package gay.sylv.blight.client.impl;

import com.mojang.blaze3d.platform.InputConstants;
import gay.sylv.blight.client.impl.render.Rendering;
import gay.sylv.blight.client.impl.gui.screen.HexTabletScreen;
import gay.sylv.blight.impl.BlightMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public final class BlightClientMod implements ClientModInitializer {
	public static final Logger LOGGER = BlightMod.createLogger("Client");
	public static KeyMapping HEX_TABLET_KEY;

	@Override
	public void onInitializeClient() {
		LOGGER.info("Blight client started");

		ClientLifecycleEvents.CLIENT_STARTED.register((client) -> Rendering.init());
		HEX_TABLET_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.blight.tablet",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_H,
				KeyMapping.CATEGORY_GAMEPLAY
		));
		ClientTickEvents.START_CLIENT_TICK.register((client) -> {
			if (HEX_TABLET_KEY.isDown()) {
				client.setScreen(new HexTabletScreen());
			}
		});
	}
}
