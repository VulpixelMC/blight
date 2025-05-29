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

	/**
	 * Set this Object as a number.
	 * @param number The number.
	 */
	public void setNumber(float number) {
		this.number = number;
	}

	/**
	 * @return This Object as a number or {@code -1} if this is an Entity.
	 */
	public float getNumber() {
		return entity != null ? -1 : number;
	}

	/**
	 * Set this Object as an Entity.
	 * @param entity The Entity.
	 */
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	/**
	 * Get this Object as an Entity or fail if this is not an Entity.
	 * @return The Entity wrapped in a {@link BlightResult}.
	 */
	public BlightResult<LivingEntity> getEntity() {
		return BlightResult.fromNullable(entity, BlightError.NO_ENTITY);
	}

	/**
	 * The underlying type of this {@link BlightObject}.
	 */
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
