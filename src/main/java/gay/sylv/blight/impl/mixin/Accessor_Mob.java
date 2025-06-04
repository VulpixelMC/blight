package gay.sylv.blight.impl.mixin;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mob.class)
public interface Accessor_Mob {
	@Invoker
	void invokeResetAmbientSoundTime();
}
