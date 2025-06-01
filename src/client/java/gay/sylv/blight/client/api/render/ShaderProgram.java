package gay.sylv.blight.client.api.render;

import gay.sylv.blight.client.impl.render.Rendering;
import gay.sylv.blight.impl.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL32C;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;

/**
 * <h1>Shader Program</h1>
 * This represents an OpenGL <a href="https://www.khronos.org/opengl/wiki/Shader">Shader</a> <a href="https://www.khronos.org/opengl/wiki/GLSL_Object">Program Object</a>.
 */
public class ShaderProgram {
	private final String name;
	private final ShaderType[] shaderTypes;
	private int program = -1;
	private boolean compiled = false;

	public ShaderProgram(String name, ShaderType... shaderTypes) {
		this.name = name;
		this.shaderTypes = shaderTypes;
	}

	/**
	 * Compile the shader program or throw if failed.
	 * @param resourceProvider The game's {@link ResourceProvider}.
	 * @throws IOException If an error during shader compilation occurred.
	 */
	public void compileOrThrow(ResourceProvider resourceProvider) throws IOException {
		if (!compile(resourceProvider)) {
			throw new IOException("Failed to compile shader program: " + name);
		}
	}

	/**
	 * Compile the shader program or return {@code false} if failed.
	 * @param resourceProvider The game's {@link ResourceProvider}.
	 */
	public boolean compile(ResourceProvider resourceProvider) {
		this.program = GL32C.glCreateProgram();

		for (ShaderType shaderType : shaderTypes) {
			try {
				String shaderSource = openShader(resourceProvider, Constants.modId(name), shaderType);
				int shader = GL32C.glCreateShader(shaderType.getGlType());
				GL32C.glShaderSource(shader, shaderSource);
				GL32C.glCompileShader(shader);

				int[] success = new int[1];
				GL32C.glGetShaderiv(shader, GL32C.GL_COMPILE_STATUS, success);
				if (success[0] == 0) {
					String infoLog = GL32C.glGetShaderInfoLog(shader);
					Rendering.LOGGER.error("Failed to compile shader {} for program {}", shaderType, name);
					Rendering.LOGGER.error(infoLog);
					GL32C.glDeleteShader(shader);
					GL32C.glDeleteProgram(program);
					return false;
				}

				GL32C.glAttachShader(program, shader);
				GL32C.glDeleteShader(shader);
			} catch (IOException e) {
				Rendering.LOGGER.error("Failed to open shader {} for program {}", shaderType, name, e);
			}
		}

		GL32C.glLinkProgram(program);

		int[] success = new int[1];
		GL32C.glGetProgramiv(program, GL32C.GL_LINK_STATUS, success);
		if (success[0] == 0) {
			String infoLog = GL32C.glGetProgramInfoLog(program);
			Rendering.LOGGER.error("Failed to link shaders for program {}", name);
			Rendering.LOGGER.error(infoLog);
			return false;
		}

		compiled = true;
		return true;
	}

	public void setMat4(String name, Matrix4fc matrix4f) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		GL32C.glUniformMatrix4fv(GL32C.glGetUniformLocation(program, name), false, matrix4f.get(buffer));
	}

	public void setVec3(String name, Vector3fc vector) {
		GL32C.glUniform3f(GL32C.glGetUniformLocation(program, name), vector.x(), vector.y(), vector.z());
	}

	public void setVec4(String name, Vector4fc vector) {
		GL32C.glUniform4f(GL32C.glGetUniformLocation(program, name), vector.x(), vector.y(), vector.z(), vector.w());
	}

	public void setInt(String name, int value) {
		GL32C.glUniform1i(GL32C.glGetUniformLocation(program, name), value);
	}

	public void setFloat(String name, float value) {
		GL32C.glUniform1f(GL32C.glGetUniformLocation(program, name), value);
	}

	public void use() {
		GL32C.glUseProgram(program);
	}

	public int getProgram() {
		return program;
	}

	public boolean isCompiled() {
		return compiled;
	}

	private static String openShader(ResourceProvider resourceProvider, ResourceLocation loc, ShaderType shaderType) throws IOException {
		Resource resource = resourceProvider.getResourceOrThrow(
				loc
						.withPrefix("shaders/")
						.withSuffix(shaderType.getExtension())
		);
		try (InputStream inputStream = resource.open()) {
			return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}
}
