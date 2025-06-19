package gay.sylv.blight.client.impl.mixin.blight3d;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.opengl.GlRenderPass;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import gay.sylv.blight.client.api.render.blight3d.opengl.BlightGlConst;
import gay.sylv.blight.client.api.render.blight3d.pipeline.BlightRenderPipeline;
import gay.sylv.blight.client.impl.render.blight3d.Duck_BlightRenderPass;
import org.lwjgl.opengl.GL32C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlCommandEncoder.class)
public class Mixin_GlCommandEncoder {
	@Inject(
			method = "trySetup",
			at = @At("TAIL")
	)
	private void afterTrySetup(GlRenderPass renderPass, CallbackInfoReturnable<Boolean> cir) {
		if (renderPass instanceof Duck_BlightRenderPass blightRenderPass) {
			if (blightRenderPass.blight$isStencilEnabled()) {
				GL32C.glEnable(GL32C.GL_STENCIL_TEST);
			} else {
				GL32C.glDisable(GL32C.GL_STENCIL_TEST);
			}
		}
	}

	@Inject(
			method = "applyPipelineState",
			at = @At("TAIL")
	)
	private void afterApplyPipelineState(RenderPipeline pipeline, CallbackInfo ci) {
		if (pipeline instanceof BlightRenderPipeline blightPipeline) {
			blightPipeline.getStencilTestFunction().ifPresent(stencilTestFunction -> GL32C.glStencilFunc(
					BlightGlConst.toGl(stencilTestFunction.type()),
					stencilTestFunction.reference(),
					stencilTestFunction.mask()
			));
			blightPipeline.getStencilTestOperation().ifPresent(stencilTestOperation -> GL32C.glStencilOp(
					BlightGlConst.toGl(stencilTestOperation.stencilFailure()),
					BlightGlConst.toGl(stencilTestOperation.stencilPassDepthFailure()),
					BlightGlConst.toGl(stencilTestOperation.stencilDepthPass())
			));
			GL32C.glStencilMask(blightPipeline.getStencilTestMask());
		}
	}
}
