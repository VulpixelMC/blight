package gay.sylv.blight.client.api.render.blight3d.pipeline;

import java.util.List;
import java.util.Optional;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.LogicOp;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BlightRenderPipeline extends RenderPipeline {
	private final int stencilTestMask;
	private final @Nullable StencilFunction stencilTestFunction;
	private final @Nullable StencilOperation stencilTestOperation;

	protected BlightRenderPipeline(
			ResourceLocation location,
			ResourceLocation vertexShader,
			ResourceLocation fragmentShader,
			ShaderDefines shaderDefines,
			List<String> samplers,
			List<UniformDescription> uniforms,
			Optional<BlendFunction> blendFunction,
			DepthTestFunction depthTestFunction,
			PolygonMode polygonMode,
			boolean cull,
			boolean writeColor,
			boolean writeAlpha,
			boolean writeDepth,
			LogicOp colorLogic,
			VertexFormat vertexFormat,
			VertexFormat.Mode vertexFormatMode,
			float depthBiasScaleFactor,
			float depthBiasConstant,
			int stencilTestMask,
			StencilFunction stencilTestFunction,
			StencilOperation stencilTestOperation
	) {
		super(
				location,
				vertexShader,
				fragmentShader,
				shaderDefines,
				samplers,
				uniforms,
				blendFunction,
				depthTestFunction,
				polygonMode,
				cull,
				writeColor,
				writeAlpha,
				writeDepth,
				colorLogic,
				vertexFormat,
				vertexFormatMode,
				depthBiasScaleFactor,
				depthBiasConstant
		);
		this.stencilTestMask = stencilTestMask;
		this.stencilTestFunction = stencilTestFunction;
		this.stencilTestOperation = stencilTestOperation;
	}

	private BlightRenderPipeline(
			RenderPipeline pipeline,
			int stencilTestMask,
			StencilFunction stencilTestFunction,
			StencilOperation stencilTestOperation
	) {
		super(
				pipeline.getLocation(),
				pipeline.getVertexShader(),
				pipeline.getFragmentShader(),
				pipeline.getShaderDefines(),
				pipeline.getSamplers(),
				pipeline.getUniforms(),
				pipeline.getBlendFunction(),
				pipeline.getDepthTestFunction(),
				pipeline.getPolygonMode(),
				pipeline.isCull(),
				pipeline.isWriteColor(),
				pipeline.isWriteAlpha(),
				pipeline.isWriteDepth(),
				pipeline.getColorLogic(),
				pipeline.getVertexFormat(),
				pipeline.getVertexFormatMode(),
				pipeline.getDepthBiasScaleFactor(),
				pipeline.getDepthBiasConstant()
		);

		this.stencilTestMask = stencilTestMask;
		this.stencilTestFunction = stencilTestFunction;
		this.stencilTestOperation = stencilTestOperation;
	}

	public static Builder builder() {
		return new Builder();
	}

	public int getStencilTestMask() {
		return stencilTestMask;
	}

	public Optional<StencilFunction> getStencilTestFunction() {
		return Optional.ofNullable(stencilTestFunction);
	}

	public Optional<StencilOperation> getStencilTestOperation() {
		return Optional.ofNullable(stencilTestOperation);
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static class Builder extends RenderPipeline.Builder {
		private Optional<Integer> stencilTestMask = Optional.empty();
		private Optional<StencilFunction> stencilTestFunction = Optional.empty();
		private Optional<StencilOperation> stencilTestOperation = Optional.empty();

		@Override
		public @NotNull BlightRenderPipeline build() {
			return new BlightRenderPipeline(
					super.build(),
					stencilTestMask.orElse(0x00),
					stencilTestFunction.orElse(null),
					stencilTestOperation.orElse(null)
			);
		}

		private Builder() {}

		public @NotNull Builder withStencilTestMask(int stencilMask) {
			this.stencilTestMask = Optional.of(stencilMask);
			return this;
		}

		public @NotNull Builder withStencilTestFunction(StencilFunction stencilTestFunction) {
			this.stencilTestFunction = Optional.of(stencilTestFunction);
			return this;
		}

		public @NotNull Builder withStencilTestOperation(StencilOperation stencilTestOperation) {
			this.stencilTestOperation = Optional.of(stencilTestOperation);
			return this;
		}

		@Override
		public @NotNull Builder withLocation(String location) {
			return (Builder) super.withLocation(location);
		}

		@Override
		public @NotNull Builder withLocation(ResourceLocation location) {
			return (Builder) super.withLocation(location);
		}

		@Override
		public @NotNull Builder withFragmentShader(String fragmentShader) {
			return (Builder) super.withFragmentShader(fragmentShader);
		}

		@Override
		public @NotNull Builder withFragmentShader(ResourceLocation fragmentShader) {
			return (Builder) super.withFragmentShader(fragmentShader);
		}

		@Override
		public @NotNull Builder withVertexShader(String vertexShader) {
			return (Builder) super.withVertexShader(vertexShader);
		}

		@Override
		public @NotNull Builder withVertexShader(ResourceLocation vertexShader) {
			return (Builder) super.withVertexShader(vertexShader);
		}

		@Override
		public @NotNull Builder withShaderDefine(String flag) {
			return (Builder) super.withShaderDefine(flag);
		}

		@Override
		public @NotNull Builder withShaderDefine(String key, int value) {
			return (Builder) super.withShaderDefine(key, value);
		}

		@Override
		public @NotNull Builder withShaderDefine(String key, float value) {
			return (Builder) super.withShaderDefine(key, value);
		}

		@Override
		public @NotNull Builder withSampler(String sampler) {
			return (Builder) super.withSampler(sampler);
		}

		@Override
		public @NotNull Builder withUniform(
				String uniform,
				UniformType type
		) {
			return (Builder) super.withUniform(uniform, type);
		}

		@Override
		public @NotNull Builder withDepthTestFunction(DepthTestFunction depthTestFunction) {
			return (Builder) super.withDepthTestFunction(depthTestFunction);
		}

		@Override
		public @NotNull Builder withPolygonMode(PolygonMode polygonMode) {
			return (Builder) super.withPolygonMode(polygonMode);
		}

		@Override
		public @NotNull Builder withCull(boolean cull) {
			return (Builder) super.withCull(cull);
		}

		@Override
		public @NotNull Builder withBlend(BlendFunction blendFunction) {
			return (Builder) super.withBlend(blendFunction);
		}

		@Override
		public @NotNull Builder withoutBlend() {
			return (Builder) super.withoutBlend();
		}

		@Override
		public @NotNull Builder withColorWrite(boolean writeColor) {
			return (Builder) super.withColorWrite(writeColor);
		}

		@Override
		public @NotNull Builder withColorWrite(
				boolean writeColor,
				boolean writeAlpha
		) {
			return (Builder) super.withColorWrite(writeColor, writeAlpha);
		}

		@Override
		public @NotNull Builder withDepthWrite(boolean writeDepth) {
			return (Builder) super.withDepthWrite(writeDepth);
		}

		@Override
		public @NotNull Builder withColorLogic(LogicOp colorLogic) {
			return (Builder) super.withColorLogic(colorLogic);
		}

		@Override
		public @NotNull Builder withVertexFormat(
				VertexFormat vertexFormat,
				VertexFormat.Mode vertexFormatMode
		) {
			return (Builder) super.withVertexFormat(vertexFormat, vertexFormatMode);
		}

		@Override
		public @NotNull Builder withDepthBias(
				float scaleFactor,
				float constant
		) {
			return (Builder) super.withDepthBias(scaleFactor, constant);
		}
	}
}
