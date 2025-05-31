package gay.sylv.blight.client.impl.render;

import gay.sylv.blight.impl.BlightMod;
import org.slf4j.Logger;

public final class Rendering {
	public static final Logger LOGGER = BlightMod.createLogger("Client", "Rendering");

	private Rendering() {}

	public static void init() {
		LOGGER.info("Rendering initialized");
	}
}
