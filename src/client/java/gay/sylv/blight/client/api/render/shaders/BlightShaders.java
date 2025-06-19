package gay.sylv.blight.client.api.render.shaders;

import gay.sylv.blight.impl.util.Constants;
import net.minecraft.resources.ResourceLocation;

public final class BlightShaders {
	public static final ResourceLocation ECHO = create("echo");

	private BlightShaders() {}

	private static ResourceLocation create(String name) {
		return Constants.modId(Constants.MOD_ID + "/" + name);
	}
}
