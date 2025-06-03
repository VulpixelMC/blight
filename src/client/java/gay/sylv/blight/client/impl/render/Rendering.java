package gay.sylv.blight.client.impl.render;

import gay.sylv.blight.api.entity.BlightEntities;
import gay.sylv.blight.client.api.render.GlSupport;
import gay.sylv.blight.client.impl.render.entity.EchoRenderer;
import gay.sylv.blight.impl.BlightMod;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import static gay.sylv.blight.client.api.render.GlSupport.Extension.*;

public final class Rendering {
	public static final Logger LOGGER = BlightMod.createLogger("Client", "Rendering");
	public static final GlSupport.Capability[] REQUIRED_CAPABILITIES = new GlSupport.Capability[]{
			GL_ARB_separate_shader_objects,
	};

	private Rendering() {}

	public static void init() {
		GlSupport.logIfAnyUnsupported(
				Level.ERROR,
				"Your device is far too ancient to run Blight!",
				REQUIRED_CAPABILITIES
		);
		EntityRendererRegistry.register(BlightEntities.ECHO, EchoRenderer::new);
		EchoRenderer.init();
	}
}
