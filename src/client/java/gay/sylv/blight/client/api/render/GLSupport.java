package gay.sylv.blight.client.api.render;

import org.lwjgl.opengl.GLCapabilities;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * A utility class for querying OpenGL support.
 */
public final class GLSupport {
	private GLSupport() {}

	/**
	 * An enum of relevant OpenGL extensions. Note that only extensions used by Blight will be available.
	 */
	public enum Extension {
		GL_ARB_texture_swizzle,
		GL_ARB_draw_indirect,
		GL_ARB_shader_storage_buffer_object,
		GL_ARB_base_instance,;

		private final VarHandle handle;

		Extension() {
			handle = GLSupport.createVarHandle(this.name(), this.getClass());
		}

		/**
		 * @return This extension's raw {@link VarHandle} defined in {@link GLCapabilities} as a {@link Boolean}.
		 */
		public VarHandle getHandle() {
			return handle;
		}

		/**
		 * @return Whether this extension is supported.
		 */
		public boolean isSupported() {
			return (boolean) getHandle().get();
		}
	}

	/**
	 * An enum of minimum supported OpenGL versions. Note that this does not indicate hardware support, only software
	 * usage (i.e., the GLFW hint).
	 */
	public enum Version {
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
			handle = GLSupport.createVarHandle(this.name(), this.getClass());
		}

		/**
		 * @return This extension's raw {@link VarHandle} defined in {@link GLCapabilities} as a {@link Boolean}.
		 */
		public VarHandle getHandle() {
			return handle;
		}

		/**
		 * @return Whether this extension is supported.
		 */
		public boolean isSupported() {
			return (boolean) getHandle().get();
		}
	}

	private static VarHandle createVarHandle(String varName, Class<?> clazz) {
		final VarHandle handle;
		try {
			handle = MethodHandles.lookup().findVarHandle(GLCapabilities.class, varName, boolean.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Invalid OpenGL extension attempted registry in " + clazz.getName(), e);
		}
		return handle;
	}
}
