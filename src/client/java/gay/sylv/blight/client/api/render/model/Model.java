package gay.sylv.blight.client.api.render.model;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import gay.sylv.blight.client.api.render.buffers.BufferUtil;
import gay.sylv.blight.client.impl.mixin.Accessor_MeshData;
import it.unimi.dsi.fastutil.ints.IntConsumer;

import java.util.Objects;

public abstract class Model implements AutoCloseable {
	private MeshData meshData;
	private ByteBufferBuilder indexByteBufferBuilder;
	private ByteBufferBuilder vertexByteBufferBuilder;

	public MeshData getMeshData() {
		return meshData;
	}

	protected void setVertexBuffer(float[] vertices, RenderPipeline pipeline) {
		vertexByteBufferBuilder = new ByteBufferBuilder(Float.BYTES * vertices.length);
		BufferBuilder vertexBufferBuilder = new BufferBuilder(
				vertexByteBufferBuilder,
				pipeline.getVertexFormatMode(),
				pipeline.getVertexFormat()
		);
		for (int i = 0; i < vertices.length; i += 3) {
			vertexBufferBuilder.addVertex(vertices[i], vertices[i + 1], vertices[i + 2]);
		}

		this.meshData = vertexBufferBuilder.buildOrThrow();
	}

	protected void setIndexBuffer(int[] indices) {
		indexByteBufferBuilder = new ByteBufferBuilder(Integer.BYTES * indices.length);
		long pointer = indexByteBufferBuilder.reserve(Integer.BYTES * indices.length);
		IntConsumer intConsumer = BufferUtil.indexWriter(pointer, VertexFormat.IndexType.INT);
		for (int index : indices) {
			intConsumer.accept(index);
		}

		ByteBufferBuilder.Result result = indexByteBufferBuilder.build();
		Objects.requireNonNull(result, "Failed to build pointer buffer");
		((Accessor_MeshData) this.meshData).setIndexBuffer(result);
	}

	@Override
	public void close() {
		meshData.close();
		indexByteBufferBuilder.close();
		vertexByteBufferBuilder.close();
	}
}
