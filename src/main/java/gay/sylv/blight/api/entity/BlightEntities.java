package gay.sylv.blight.api.entity;

import gay.sylv.blight.impl.util.Constants;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.ApiStatus;

/**
 * Entities in Blight.
 */
public final class BlightEntities {
	public static EntityType<Echo> ECHO;

	private BlightEntities() {}

	@ApiStatus.Internal
	public static void init() {
		ECHO = register(
				"echo",
				EntityType.Builder.of(Echo::new, MobCategory.CREATURE),
				Echo.createMobAttributes()
		);
	}

	private static <T extends Entity> EntityType<T> register(
			String name,
			EntityType.Builder<T> builder
	) {
		ResourceLocation id = Constants.modId(name);
		return Registry.register(
				BuiltInRegistries.ENTITY_TYPE,
				id,
				builder.build(ResourceKey.create(
						Registries.ENTITY_TYPE,
						id
				))
		);
	}

	private static <T extends LivingEntity> EntityType<T> register(
			String name,
			EntityType.Builder<T> builder,
			AttributeSupplier.Builder attributes
	) {
		EntityType<T> entityType = register(name, builder);
		//noinspection DataFlowIssue // Contract is incorrect
		FabricDefaultAttributeRegistry.register(entityType, attributes);
		return entityType;
	}
}
