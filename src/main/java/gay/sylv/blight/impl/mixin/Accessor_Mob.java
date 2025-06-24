package gay.sylv.blight.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.Mob;

@Mixin(Mob.class)
public interface Accessor_Mob {
	@Invoker
	void invokeResetAmbientSoundTime();
}
