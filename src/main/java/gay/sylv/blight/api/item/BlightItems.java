package gay.sylv.blight.api.item;

import gay.sylv.blight.api.entity.BlightEntities;
import gay.sylv.blight.impl.util.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

public final class BlightItems {
	public static Item ECHO_SPAWN_EGG;

	private BlightItems() {}

	@ApiStatus.Internal
	public static void init() {
		ECHO_SPAWN_EGG = register(
				"echo_spawn_egg",
				itemProps(),
				props -> new SpawnEggItem(
						BlightEntities.ECHO,
						props
				)
		);
	}

	private static Item register(String name, Item.Properties defaultProps, Function<Item.Properties, Item> itemFactory) {
		ResourceKey<Item> key = ResourceKey.create(
				Registries.ITEM,
				Constants.modId(name)
		);
		return Registry.register(
				BuiltInRegistries.ITEM,
				Constants.modId(name),
				itemFactory.apply(defaultProps.setId(key))
		);
	}

	private static Item.Properties itemProps() {
		return new Item.Properties()
				.useItemDescriptionPrefix();
	}
}
