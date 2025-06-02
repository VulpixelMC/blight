package gay.sylv.blight.api.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Echo</h1>
 * An <b>Echo</b> is a creature residing in the Blighted Forests and more rarely in the Pale Garden.
 * Echoes give the player knowledge about the time of Blight's past and how to utilize Natures.
 */
public class Echo extends Mob {
	protected Echo(
			EntityType<? extends Mob> entityType,
			Level level
	) {
		super(entityType, level);
	}

	@Override
	public @NotNull HumanoidArm getMainArm() {
		return HumanoidArm.LEFT; // Echoes are canonically left-handed
	}
}
