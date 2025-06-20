package gay.sylv.blight.client.api.render.pipeline;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public final class BlightVertexFormats {
	public static final VertexFormat POSITION_NORMAL = VertexFormat.builder()
			.add("Position", VertexFormatElement.POSITION)
			.add("Normal", VertexFormatElement.POSITION)
			.build();

	private  BlightVertexFormats() {}
}
