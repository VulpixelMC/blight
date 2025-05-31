package gay.sylv.blight.client.api.render;

import gay.sylv.blight.client.impl.render.Rendering;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import static gay.sylv.blight.impl.BlightMod.logSeparator;

/**
 * A utility class for querying OpenGL support.
 */
public final class GLSupport {
	private GLSupport() {}

	/**
	 * Send a message to the log if the capability is unsupported.
	 * @param capability The capability.
	 * @param logLevel The log level.
	 * @param message The message to be logged.
	 * @param capabilityArgs The arguments querying capability support.
	 */
	public static void logIfUnsupported(Capability capability, Level logLevel, String message, Object... capabilityArgs) {
		if (!capability.isSupported(capabilityArgs)) {
			LoggingEventBuilder logger = Rendering.LOGGER.atLevel(logLevel);
			logSeparator(logger);
			logger.log(message);
			logSeparator(logger);
		}
	}

	/**
	 * Send a message to the log if the capability is unsupported.
	 * @param capability The capability.
	 * @param logLevel The log level.
	 * @param message The message to be logged.
	 */
	public static void logIfUnsupported(Capability capability, Level logLevel, String message) {
		logIfUnsupported(capability, logLevel, message, new Object[0]);
	}

	/**
	 * A base interface enabling the query of capability support.
	 */
	public interface Capability {
		/**
		 * This capability's raw {@link VarHandle} referencing a field in the defining class for this type of
		 * capability (e.g. {@link GLCapabilities}).
		 * @return This capability's raw {@link VarHandle}.
		 */
		VarHandle getHandle();

		/**
		 * @param args Any arguments as needed by this capability. This is intended for capabilities that are not
		 * booleans.
		 * @return Whether this capability is supported.
		 */
		default boolean isSupported(@SuppressWarnings("unused") Object... args) {
			return (boolean) getHandle().get();
		}
	}

	/**
	 * An enum of relevant OpenGL extensions. Note that only extensions used by Blight will be available.
	 */
	public enum Extension implements Capability {
		GL_ARB_texture_swizzle,
		GL_ARB_draw_indirect,
		GL_ARB_shader_storage_buffer_object,
		GL_ARB_base_instance,;

		private final VarHandle handle;

		Extension() {
			handle = GLSupport.createVarHandle(GLCapabilities.class, this.name(), boolean.class, this.getClass());
		}

		@Override
		public VarHandle getHandle() {
			return handle;
		}
	}

	/**
	 * An enum of minimum supported OpenGL versions. Note that this does not indicate hardware support, only software
	 * usage (i.e., the GLFW hint).
	 */
	public enum Version implements Capability {
		OpenGL11,
		OpenGL12,
		OpenGL13,
		OpenGL14,
		OpenGL15,
		OpenGL20,
		OpenGL21,
		OpenGL30,
		OpenGL31,
		OpenGL32,
		OpenGL33,
		OpenGL40,
		OpenGL41,
		OpenGL42,
		OpenGL43,
		OpenGL44,
		OpenGL45,
		OpenGL46,;

		private final VarHandle handle;

		Version() {
			handle = GLSupport.createVarHandle(GLCapabilities.class, this.name(), boolean.class, this.getClass());
		}

		@Override
		public VarHandle getHandle() {
			return handle;
		}
	}

	private static VarHandle createVarHandle(
			Class<?> definingClazz,
			String fieldName,
			Class<?> fieldType,
			Class<?> callerClazz
	) {
		final VarHandle handle;
		try {
			handle = MethodHandles.lookup().findVarHandle(definingClazz, fieldName, fieldType);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Invalid OpenGL extension attempted registry in " + callerClazz.getName(), e);
		}
		return handle;
	}
}
