package gay.sylv.blight.client.impl;

import gay.sylv.blight.impl.BlightMod;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class BlightClientMod implements ClientModInitializer {
	public static final Logger LOGGER = BlightMod.createLogger("Client");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Blight client started");
	}
}
