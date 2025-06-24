package gay.sylv.blight.client.impl;

import gay.sylv.blight.client.impl.render.Rendering;
import gay.sylv.blight.impl.BlightMod;
import org.slf4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public final class BlightClientMod implements ClientModInitializer {
	public static final Logger LOGGER = BlightMod.createLogger("Client");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Blight client started");

		ClientLifecycleEvents.CLIENT_STARTED.register((client) -> {
			Rendering.init();
		});
	}
}
