package gay.sylv.blight.api.item;

import gay.sylv.blight.impl.util.Constants;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public final class BlightCreativeModeTabs {
	public static CreativeModeTab ECHO;

	private BlightCreativeModeTabs() {}

	@SuppressWarnings("CodeBlock2Expr")
	@ApiStatus.Internal
	public static void init() {
		ECHO = register(
				"echo",
				BlightItems.ECHO_SPAWN_EGG,
				tab -> {
					tab.accept(BlightItems.ECHO_SPAWN_EGG);
				}
		);
	}

	private static CreativeModeTab register(
			String name,
			Item icon,
			ItemGroupEvents.ModifyEntries callback
	) {
		ResourceKey<CreativeModeTab> key = ResourceKey.create(
				Registries.CREATIVE_MODE_TAB,
				Constants.modId(name)
		);
		CreativeModeTab tab = Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB,
				Constants.modId(name),
				FabricItemGroup.builder()
						.icon(icon::getDefaultInstance)
						.title(Component.translatable("itemGroup.blight." + name))
						.build()
		);
		ItemGroupEvents.modifyEntriesEvent(key).register(callback);
		return tab;
	}
}
