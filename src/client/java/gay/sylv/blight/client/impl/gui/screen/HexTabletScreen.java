package gay.sylv.blight.client.impl.gui.screen;

import dev.lambdaurora.spruceui.border.Border;
import dev.lambdaurora.spruceui.widget.SpruceWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextAreaWidget;
import gay.sylv.blight.impl.util.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class HexTabletScreen extends BlightScreen {
	private static final ResourceLocation BG_SPRITE_DARK = Constants.modId("hex_tablet_dark");
	private static final int NINE_SLICE_OFFSET = 16;
	private SpruceTextAreaWidget textArea;

	protected HexTabletScreen(Component title) {
		super(title);
	}

	public HexTabletScreen() {
		this(Component.translatable("blight.gui.hex_tablet.title"));
	}

	@Override
	protected void init() {
		this.windowWidth = this.width / 4 * 2;
		this.windowHeight = this.width / 4;
		this.windowPosition = this.centerOfScreen();
		this.textArea = new SpruceTextAreaWidget(
				this.windowPosition
						.copy()
						.move(
								this.windowPosition.getX() + NINE_SLICE_OFFSET,
								this.windowPosition.getY() + NINE_SLICE_OFFSET
						),
				this.windowWidth - NINE_SLICE_OFFSET * 2,
				this.windowHeight - NINE_SLICE_OFFSET * 2,
				this.title
		);
		this.textArea.setBorder(new Border() {
			@Override
			public void render(
					GuiGraphics graphics,
					SpruceWidget widget,
					int mouseX,
					int mouseY,
					float delta
			) {
			}

			@Override
			public int getThickness() {
				return 0;
			}
		});
		this.textArea.setBackground((graphics, widget, vOffset, mouseX, mouseY, delta) -> {});
		this.addRenderableWidget(this.textArea);
	}

	@Override
	public void renderBackground(
			GuiGraphics guiGraphics,
			int mouseX,
			int mouseY,
			float partialTick
	) {
		this.renderBlurredBackground();

		guiGraphics.blitSprite(
				RenderType::guiTextured,
				BG_SPRITE_DARK,
				this.windowPosition.getX(),
				this.windowPosition.getY(),
				this.windowWidth,
				this.windowHeight
		);
	}
}
