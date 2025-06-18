package gay.sylv.blight.client.impl.render;

import gay.sylv.blight.api.entity.BlightEntities;
import gay.sylv.blight.client.impl.render.entity.EchoRenderer;
import gay.sylv.blight.impl.BlightMod;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.slf4j.Logger;

public final class Rendering {
	public static final Logger LOGGER = BlightMod.createLogger("Client", "Rendering");

	private Rendering() {}

	public static void init() {
		EntityRendererRegistry.register(BlightEntities.ECHO, EchoRenderer::new);
		EchoRenderer.init();
	}
}
