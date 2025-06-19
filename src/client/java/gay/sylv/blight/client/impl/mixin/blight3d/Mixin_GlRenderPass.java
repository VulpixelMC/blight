package gay.sylv.blight.client.impl.mixin.blight3d;

import com.mojang.blaze3d.opengl.GlRenderPass;
import gay.sylv.blight.client.api.render.blight3d.BlightRenderPass;
import gay.sylv.blight.client.impl.render.blight3d.Duck_BlightRenderPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GlRenderPass.class)
public abstract class Mixin_GlRenderPass implements BlightRenderPass, Duck_BlightRenderPass {
	@Unique
	private boolean blight$stencil;

	@Override
	public void blight$enableStencil() {
		blight$stencil = true;
	}

	@Override
	public void blight$disableStencil() {
		blight$stencil = false;
	}

	@Override
	public boolean blight$isStencilEnabled() {
		return blight$stencil;
	}
}
