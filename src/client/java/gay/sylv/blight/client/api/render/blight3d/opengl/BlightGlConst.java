package gay.sylv.blight.client.api.render.blight3d.opengl;

import gay.sylv.blight.client.api.render.blight3d.pipeline.StencilFunction;
import gay.sylv.blight.client.api.render.blight3d.pipeline.StencilOperation;
import org.lwjgl.opengl.GL32C;

public final class BlightGlConst {
	private BlightGlConst() {}

	public static int toGl(StencilFunction.Type func) {
		return switch (func) {
			case NEVER -> GL32C.GL_NEVER;
			case LESS -> GL32C.GL_LESS;
			case LESS_OR_EQUAL -> GL32C.GL_LEQUAL;
			case GREATER -> GL32C.GL_GREATER;
			case GREATER_OR_EQUAL -> GL32C.GL_GEQUAL;
			case EQUAL -> GL32C.GL_EQUAL;
			case NOT_EQUAL -> GL32C.GL_NOTEQUAL;
			case ALWAYS -> GL32C.GL_ALWAYS;
		};
	}

	public static int toGl(StencilOperation.Type op) {
		return switch (op) {
			case KEEP -> GL32C.GL_KEEP;
			case ZERO -> GL32C.GL_ZERO;
			case REPLACE -> GL32C.GL_REPLACE;
			case INCREMENT -> GL32C.GL_INCR;
			case INCREMENT_AND_WRAP -> GL32C.GL_INCR_WRAP;
			case DECREMENT -> GL32C.GL_DECR;
			case DECREMENT_AND_WRAP -> GL32C.GL_DECR_WRAP;
			case INVERT -> GL32C.GL_INVERT;
		};
	}
}
