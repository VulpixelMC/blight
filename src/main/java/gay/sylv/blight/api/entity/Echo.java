package gay.sylv.blight.api.entity;

import gay.sylv.blight.impl.mixin.Accessor_Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

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

	@Override
	protected void playHurtSound(DamageSource source) {
		((Accessor_Mob) this).invokeResetAmbientSoundTime();
		playHurtDeathSound(2.0f);
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		playHurtDeathSound(0.5f);
		return null;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
		return null;
	}

	private void playHurtDeathSound(float pitch) {
		this.playSound(SoundEvents.CREEPER_DEATH, this.getSoundVolume(), this.getVoicePitch() * pitch);
	}
}
