package gay.sylv.blight.client.api.render.blight3d.pipeline;

public record StencilFunction(Type type, int reference, int mask) {
	public enum Type {
		NEVER,
		LESS,
		LESS_OR_EQUAL,
		GREATER,
		GREATER_OR_EQUAL,
		EQUAL,
		NOT_EQUAL,
		ALWAYS,
	}
}
