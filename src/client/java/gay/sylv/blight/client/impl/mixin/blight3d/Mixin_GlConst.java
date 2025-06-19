package gay.sylv.blight.client.impl.mixin.blight3d;

import com.mojang.blaze3d.opengl.GlConst;
import org.lwjgl.opengl.GL32C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GlConst.class)
public class Mixin_GlConst {
	@ModifyConstant(
			method = "toGlInternalId",
			constant = @Constant(
					intValue = GL32C.GL_DEPTH_COMPONENT32
			)
	)
	private static int depthStencilInternalId(int constant) {
		return GL32C.GL_DEPTH24_STENCIL8;
	}

	@ModifyConstant(
			method = "toGlExternalId",
			constant = @Constant(
					intValue = GL32C.GL_DEPTH_COMPONENT
			)
	)
	private static int depthStencilExternalId(int constant) {
		return GL32C.GL_DEPTH_STENCIL;
	}

	@ModifyConstant(
			method = "toGlType",
			constant = @Constant(
					intValue = GL32C.GL_FLOAT
			)
	)
	private static int depthStencilType(int constant) {
		return GL32C.GL_UNSIGNED_INT_24_8;
	}
}
