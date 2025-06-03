package gay.sylv.blight.client.impl.render.entity;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import gay.sylv.blight.api.entity.Echo;
import gay.sylv.blight.client.api.render.GlSupport;
import gay.sylv.blight.client.api.render.Shaders;
import gay.sylv.blight.client.api.render.model.Icosphere;
import gay.sylv.blight.client.impl.render.Rendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL32C;

public class EchoRenderer extends EntityRenderer<Echo, EchoRenderer.EchoRenderState> {
	private static final Icosphere ICOSPHERE = new Icosphere(1);
	private static int vao;
	private static int vbo;
	private static int ebo;
	private static int mainFbo;

	@Override
	public void render(
			EchoRenderState renderState,
			PoseStack poseStack,
			MultiBufferSource bufferSource,
			int packedLight
	) {
		if (!Shaders.ECHO.isCompiled() || !GlSupport.Capability.allSupported(Rendering.REQUIRED_CAPABILITIES)) return;

		renderState.priorState.preserve();

		GL32C.glBindFramebuffer(GL32C.GL_DRAW_FRAMEBUFFER, mainFbo);

		poseStack.pushPose();
		poseStack.translate(0.0f, 0.5f, 0.0f);

		Matrix4f frustumMatrix = poseStack.last().pose();
		Matrix4f modelViewMatrix = new Matrix4f(RenderSystem.getModelViewStack());

		GL32C.glEnable(GL32C.GL_DEPTH_TEST);
		GL32C.glDepthMask(true);
		GL32C.glDepthFunc(GL32C.GL_LEQUAL);
		GL32C.glEnable(GL32C.GL_CULL_FACE);
		GL32C.glCullFace(GL32C.GL_BACK);
		GL32C.glEnable(GL32C.GL_BLEND);
		GL32C.glBlendFunc(GL32C.GL_SRC_COLOR, GL32C.GL_ONE_MINUS_DST_ALPHA);

		GL32C.glBindVertexArray(vao);
		Shaders.ECHO.use();
		Shaders.ECHO.setMat4("frustum_matrix", frustumMatrix);
		Shaders.ECHO.setMat4("model_view_matrix", modelViewMatrix);
		Shaders.ECHO.setMat4("projection_matrix", RenderSystem.getProjectionMatrix());
		// Inner
		draw(0.75f, 0.85f, 1.0f, 0.25f, 0.25f);
		// Middle
		draw(0.75f / 2.0f, 0.85f / 2.0f, 1.0f / 2.0f, 0.375f, 0.3f);
		// Outer
		draw(0.6f, 0.75f, 1.0f, 0.5f, 0.425f);

		super.render(renderState, poseStack, bufferSource, packedLight);

		poseStack.popPose();

		renderState.priorState.restore();
	}

	private void draw(float r, float g, float b, float a, float scale) {
		Shaders.ECHO.setVec4("color", new Vector4f(r, g, b, a));
		Shaders.ECHO.setFloat("scale", scale);
		GL32C.glDrawElements(GL32C.GL_TRIANGLES, ICOSPHERE.getIndices().length, GL32C.GL_UNSIGNED_INT, 0);
	}

	public EchoRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public static void init() {
		// Pull Main Render Target's FBO
		assert Minecraft.getInstance().getMainRenderTarget().getColorTexture() != null;
		mainFbo = ((GlTexture) Minecraft.getInstance().getMainRenderTarget().getColorTexture()).glId();

		// Upload icosphere
		vao = GL32C.glGenVertexArrays();
		vbo = GL32C.glGenBuffers();
		ebo = GL32C.glGenBuffers();
		GL32C.glBindVertexArray(vao);

		GL32C.glBindBuffer(GL32C.GL_ARRAY_BUFFER, vbo);
		GL32C.glBufferData(GL32C.GL_ARRAY_BUFFER, ICOSPHERE.getVertices(), GL32C.GL_STATIC_DRAW);

		GL32C.glBindBuffer(GL32C.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL32C.glBufferData(GL32C.GL_ELEMENT_ARRAY_BUFFER, ICOSPHERE.getIndices(), GL32C.GL_STATIC_DRAW);

		GL32C.glVertexAttribPointer(
				0,
				3,
				GL32C.GL_FLOAT,
				false,
				3 * Float.BYTES,
				0
		);
		GL32C.glEnableVertexAttribArray(0);
		Shaders.ECHO.setVertex(0, "position");

		GL32C.glBindVertexArray(0);
	}

	@Override
	public void extractRenderState(
			Echo entity,
			EchoRenderState reusedState,
			float partialTick
	) {
		super.extractRenderState(entity, reusedState, partialTick);
		reusedState.position = entity.position();
	}

	@Override
	public @NotNull EchoRenderState createRenderState() {
		return new EchoRenderState();
	}

	public static class EchoRenderState extends EntityRenderState {
		public Vec3 position;
		public PriorState priorState = new PriorState();

		/**
		 * <img src="fury.png" height="264" />
		 */
		public static class PriorState {
			public int shaderProgram;
			public boolean depthTest;
			public boolean depthMask;
			public int depthFunc;
			public boolean faceCulling;
			public boolean blend;
			public int blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha;

			public void preserve() {
				shaderProgram = GL32C.glGetInteger(GL32C.GL_CURRENT_PROGRAM);
				depthTest = GL32C.glGetBoolean(GL32C.GL_DEPTH_TEST);
				depthMask = GL32C.glGetBoolean(GL32C.GL_DEPTH_WRITEMASK);
				depthFunc = GL32C.glGetInteger(GL32C.GL_DEPTH_FUNC);
				faceCulling = GL32C.glGetBoolean(GL32C.GL_CULL_FACE);
				blend = GL32C.glGetBoolean(GL32C.GL_BLEND);
				blendSrcRgb = GL32C.glGetInteger(GL32C.GL_BLEND_SRC_RGB);
				blendDstRgb = GL32C.glGetInteger(GL32C.GL_BLEND_DST_RGB);
				blendSrcAlpha = GL32C.glGetInteger(GL32C.GL_BLEND_SRC_ALPHA);
				blendDstAlpha = GL32C.glGetInteger(GL32C.GL_BLEND_DST_ALPHA);
			}

			public void restore() {
				GL32C.glUseProgram(shaderProgram);
				setEnabled(GL32C.GL_DEPTH_TEST, depthTest);
				GL32C.glDepthMask(depthMask);
				GL32C.glDepthFunc(depthFunc);
				setEnabled(GL32C.GL_CULL_FACE, faceCulling);
				setEnabled(GL32C.GL_BLEND, blend);
				GL32C.glBlendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha);
			}

			private static void setEnabled(int target, boolean enabled) {
				if (enabled) {
					GL32C.glEnable(target);
				} else {
					GL32C.glDisable(target);
				}
			}
		}
	}
}
