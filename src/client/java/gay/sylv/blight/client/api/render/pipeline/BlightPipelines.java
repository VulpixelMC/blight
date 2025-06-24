package gay.sylv.blight.client.api.render.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.VertexFormat;
import gay.sylv.blight.client.api.render.blight3d.pipeline.BlightRenderPipeline;
import gay.sylv.blight.client.api.render.shaders.BlightShaders;

/**
 * <h1>Blight Render Pipelines</h1>
 * Just as in Vulkan or even WebGPU, Blight uses <b>Render Pipelines</b> (as defined by {@link RenderPipeline}).
 * <br>
 * <h2>Beware the Pipeline</h2>
 * You have to be careful with Pipelines and Render Passes as demonstrated by the image below.
 * Clear your Depth Buffers and hold on to your belongings, else you risk
 * summoning an interdimensional rendering demon named Karl.
 * <br>
 * <img src="beware.jpg" height="291" />
 */
public final class BlightPipelines {
	public static final BlightRenderPipeline ECHO_PASS_1 = BlightRenderPipeline.builder()
			.withLocation(BlightShaders.ECHO)
			.withColorWrite(false)
			.withDepthWrite(true)
			.withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
			.withCull(true)
			.withVertexShader(BlightShaders.ECHO)
			.withFragmentShader(BlightShaders.ECHO)
			.withBlend(new BlendFunction(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_ALPHA))
			.withVertexFormat(
					BlightVertexFormats.POSITION_NORMAL,
					VertexFormat.Mode.TRIANGLES
			)
			.withUniform("LocalMat", UniformType.MATRIX4X4)
			.withUniform("ModelViewMat", UniformType.MATRIX4X4)
			.withUniform("ProjMat",  UniformType.MATRIX4X4)
			.withUniform("Color", UniformType.VEC4)
			.withUniform("Scale", UniformType.FLOAT)
			.withUniform("CameraPos", UniformType.VEC3)
			.build();

	public static final BlightRenderPipeline ECHO_PASS_2 = BlightRenderPipeline.builder()
			.withLocation(BlightShaders.ECHO)
			.withColorWrite(true)
			.withDepthWrite(false)
			.withDepthTestFunction(DepthTestFunction.GREATER_DEPTH_TEST)
			.withCull(true)
			.withVertexShader(BlightShaders.ECHO)
			.withFragmentShader(BlightShaders.ECHO)
			.withBlend(new BlendFunction(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_ALPHA))
			.withVertexFormat(
					BlightVertexFormats.POSITION_NORMAL,
					VertexFormat.Mode.TRIANGLES
			)
			.withUniform("LocalMat", UniformType.MATRIX4X4)
			.withUniform("ModelViewMat", UniformType.MATRIX4X4)
			.withUniform("ProjMat",  UniformType.MATRIX4X4)
			.withUniform("Color", UniformType.VEC4)
			.withUniform("Scale", UniformType.FLOAT)
			.withUniform("CameraPos", UniformType.VEC3)
			.build();

	private BlightPipelines() {}
}
