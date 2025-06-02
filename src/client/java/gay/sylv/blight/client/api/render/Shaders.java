package gay.sylv.blight.client.api.render;

import gay.sylv.blight.api.entity.Echo;
import gay.sylv.blight.impl.util.Constants;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.jetbrains.annotations.ApiStatus;

public final class Shaders {
	/**
	 * The shader for {@link Echo}.
	 */
	public static final ShaderProgram ECHO = create(
			"echo",
			ShaderType.VERTEX,
			ShaderType.FRAGMENT
	);

	private Shaders() {}

	@ApiStatus.Internal
	public static void init(ResourceProvider resourceProvider) {
		ECHO.compileOrThrow(resourceProvider);
	}

	private static ShaderProgram create(String name, ShaderType... shaderTypes) {
		return new ShaderProgram(
				Constants.modId(name),
				Constants.MOD_ID,
				shaderTypes
		);
	}
}
