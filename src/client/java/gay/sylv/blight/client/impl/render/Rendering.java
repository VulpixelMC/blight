package gay.sylv.blight.client.impl.render;

import gay.sylv.blight.api.entity.BlightEntities;
import gay.sylv.blight.client.impl.render.entity.EchoRenderer;
import gay.sylv.blight.impl.BlightMod;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

public final class Rendering {
	public static final Logger LOGGER = BlightMod.createLogger("Client", "Rendering");

	/**
	 * Change this value to {@code false} if you're implementing or using a different rendering engine.
	 */
	@ApiStatus.Experimental
	public static boolean useGl = true;

	private Rendering() {}

	public static void init() {
		EntityRendererRegistry.register(BlightEntities.ECHO, EchoRenderer::new);
		EchoRenderer.init();
	}
}
