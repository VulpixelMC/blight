package gay.sylv.blight.api.vm;

import gay.sylv.blight.api.vm.error.BlightError;
import gay.sylv.blight.api.vm.error.BlightResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

/**
 * <h1>Blight Object</h1>
 * An <b>Object</b> in the {@link BlightVM} is a decimal or integer number, or a {@link LivingEntity}.
 */
public class BlightObject {
	private float number;
	private LivingEntity entity;

	public void setNumber(float number) {
		this.number = number;
	}

	public float getNumber() {
		return entity != null ? -1 : number;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	public BlightResult<LivingEntity> getEntity() {
		return BlightResult.fromNullable(entity, BlightError.NO_ENTITY);
	}
	
	public enum Type {
		NUMBER(Component.translatable("blight.vm.object.number")),
		ENTITY(Component.translatable("blight.vm.object.entity"));
		
		private final Component name;
		
		Type(Component name) {
			this.name = name;
		}
		
		public Component getName() {
			return name;
		}
	}
}
