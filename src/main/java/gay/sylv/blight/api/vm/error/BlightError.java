package gay.sylv.blight.api.vm.error;

import gay.sylv.blight.api.vm.BlightVM;
import gay.sylv.blight.impl.util.Constants;

import net.minecraft.network.chat.Component;

/**
 * An error that occurred during the operation of the {@link BlightVM}.
 */
public enum BlightError {
	NO_ENTITY("no_entity"),
	NO_VARIABLE("no_variable"),
	HEX_UNIMPLEMENTED("hex_unimplemented"),
	NATURE_UNIMPLEMENTED("nature_unimplemented"),;

	private final String transKey;

	BlightError(String transKey) {
		this.transKey = Constants.MOD_ID + ".vm.error." + transKey;
	}

	/**
	 * @return This error's translation key.
	 */
	public String getTransKey() {
		return transKey;
	}

	/**
	 * Creates a {@link Component} from the translation key.
	 * @return The {@link Component}.
	 */
	public Component createMessage() {
		return Component.translatable(transKey);
	}

	/**
	 * Creates a {@link Component} from the translation key.
	 * @param args The arguments to this {@link Component}.
	 * @return The {@link Component} with arguments.
	 */
	public Component createMessage(Object... args) {
		return Component.translatable(transKey, args);
	}
}
