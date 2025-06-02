package gay.sylv.blight.client.api.render;

import gay.sylv.blight.client.impl.render.Rendering;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import static gay.sylv.blight.impl.BlightMod.logSeparator;

/**
 * A utility class for querying OpenGL support.
 */
public final class GlSupport {
	private GlSupport() {}

	/**
	 * Send a message to the log if the capability is unsupported.
	 * @param capability The capabilities.
	 * @param logLevel The log level.
	 * @param message The message to be logged.
	 */
	public static void logIfUnsupported(Capability capability, Level logLevel, String message) {
		if (!capability.isSupported()) {
			LoggingEventBuilder logger = Rendering.LOGGER.atLevel(logLevel);
			logSeparator(logger);
			logger.log(message);
			logSeparator(logger);
		}
	}

	/**
	 * Send a message to the log if any of the capabilities are unsupported.
	 * @param logLevel The log level.
	 * @param message The message to be logged.
	 * @param capabilities The capabilities.
	 */
	public static void logIfAnyUnsupported(Level logLevel, String message, Capability... capabilities) {
		boolean supported = true;
		for (Capability capability : capabilities) {
			if (!capability.isSupported()) {
				supported = false;
				break;
			}
		}

		if (!supported) {
			LoggingEventBuilder logger = Rendering.LOGGER.atLevel(logLevel);
			logSeparator(logger);
			logger.log(message);
			logSeparator(logger);
		}
	}

	/**
	 * A base interface enabling the query of capability support.
	 */
	public interface Capability {
		/**
		 * This capability's raw {@link VarHandle} referencing a field in the defining class for this type of
		 * capabilities (e.g. {@link GLCapabilities}).
		 * @return This capability's raw {@link VarHandle}.
		 */
		VarHandle getHandle();

		/**
		 * @return Whether this capability is supported.
		 */
		default boolean isSupported() {
			return (boolean) getHandle().get(GL.getCapabilities());
		}

		static boolean allSupported(Capability... capabilities) {
			for (Capability capability : capabilities) {
				if (!capability.isSupported()) {
					return false;
				}
			}

			return true;
		}
	}

	/**
	 * An enum of relevant OpenGL extensions. Note that only extensions used by Blight will be available.
	 */
	public enum Extension implements Capability {
		GL_ARB_texture_swizzle,
		GL_ARB_draw_indirect,
		GL_ARB_shader_storage_buffer_object,
		GL_ARB_base_instance,
		GL_ARB_separate_shader_objects,;

		private final VarHandle handle;

		Extension() {
			handle = GlSupport.createVarHandle(GLCapabilities.class, this.name(), boolean.class, this.getClass());
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
			handle = GlSupport.createVarHandle(GLCapabilities.class, this.name(), boolean.class, this.getClass());
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
