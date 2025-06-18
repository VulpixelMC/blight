package gay.sylv.blight.client.api.render.buffer;

import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import org.apache.commons.lang3.mutable.MutableLong;
import org.lwjgl.system.MemoryUtil;

/**
 * A grab-bag of utilities for dealing with buffers.
 */
public class BufferUtil {
	// Taken from MeshData#indexWriter
	public static IntConsumer indexWriter(long pointer, VertexFormat.IndexType type) {
		MutableLong mutableLong = new MutableLong(pointer);

		return switch (type) {
			case SHORT -> i -> MemoryUtil.memPutShort(mutableLong.getAndAdd(2L), (short)i);
			case INT -> i -> MemoryUtil.memPutInt(mutableLong.getAndAdd(4L), i);
		};
	}
}
