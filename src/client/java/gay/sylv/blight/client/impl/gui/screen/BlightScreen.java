package gay.sylv.blight.client.impl.gui.screen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import net.minecraft.network.chat.Component;

/**
 * A screen that is also window-like.
 * <br>
 * Also, there is no ABI or API guarantees (see {@link gay.sylv.blight.impl}).
 */
public class BlightScreen extends SpruceScreen {
	protected int windowWidth;
	protected int windowHeight;
	protected Position windowPosition;

	protected BlightScreen(Component title) {
		super(title);
	}

	protected Position centerOfScreen() {
		return Position.of(this.width / 2 - this.windowWidth / 2, this.height / 2 - this.windowHeight / 2);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
