package gay.sylv.blight.client.impl.mixin;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MeshData.class)
public interface Accessor_MeshData {
	@Accessor
	void setIndexBuffer(ByteBufferBuilder.Result buffer);
}
