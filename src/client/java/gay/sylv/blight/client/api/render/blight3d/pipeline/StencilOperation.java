package gay.sylv.blight.client.api.render.blight3d.pipeline;

public record StencilOperation(Type stencilFailure, Type stencilPassDepthFailure, Type stencilDepthPass) {
	public enum Type {
		KEEP,
		ZERO,
		REPLACE,
		INCREMENT,
		INCREMENT_AND_WRAP,
		DECREMENT,
		DECREMENT_AND_WRAP,
		INVERT,
	}
}
