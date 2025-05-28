package gay.sylv.blight.api.vm.error;

import gay.sylv.blight.api.vm.BlightVM;
import net.minecraft.network.chat.Component;

/**
 * An error that occurred during the operation of the {@link BlightVM}.
 */
public enum BlightError {
	NO_ENTITY(Component.translatable("blight.vm.error.no_entity"));

	private final Component message;

	BlightError(Component message) {
		this.message = message;
	}

	private Component getMessage() {
		return message;
	}
}
