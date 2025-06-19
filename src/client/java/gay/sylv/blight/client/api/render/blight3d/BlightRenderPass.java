package gay.sylv.blight.client.api.render.blight3d;

import com.mojang.blaze3d.opengl.GlRenderPass;
import com.mojang.blaze3d.systems.RenderPass;

/**
 * A series of improvements to {@link RenderPass}.
 * <br>
 * @apiNote This is implemented on {@link GlRenderPass} and {@link RenderPass}, so you can cast them both.
 */
public interface BlightRenderPass extends RenderPass {
	default void blight$enableStencil() {}

	default void blight$disableStencil() {}
}
