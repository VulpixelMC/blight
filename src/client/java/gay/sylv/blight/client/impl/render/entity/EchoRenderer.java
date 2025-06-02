package gay.sylv.blight.client.impl.render.entity;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import gay.sylv.blight.api.entity.Echo;
import gay.sylv.blight.client.api.render.GlSupport;
import gay.sylv.blight.client.api.render.Shaders;
import gay.sylv.blight.client.api.render.model.Icosphere;
import gay.sylv.blight.client.impl.render.Rendering;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
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

		// https://shaders.properties/_astro/sampler2darrayshadow.B_BOj-ZN_tLJ18.webp
		renderState.priorState.preserve();

		GL32C.glBindFramebuffer(GL32C.GL_DRAW_FRAMEBUFFER, mainFbo);

		poseStack.pushPose();
		poseStack.scale(0.25f, 0.25f, 0.25f);

		Matrix4f frustumMatrix = poseStack.last().pose();
		Matrix4f modelViewMatrix = new Matrix4f(RenderSystem.getModelViewStack());

		GL32C.glEnable(GL32C.GL_DEPTH_TEST);
		GL32C.glDepthMask(true);
		GL32C.glDepthFunc(GL32C.GL_LEQUAL);
		GL32C.glEnable(GL32C.GL_CULL_FACE);
		GL32C.glCullFace(GL32C.GL_BACK);

		GL32C.glBindVertexArray(vao);
		Shaders.ECHO.use();
		Shaders.ECHO.setMat4("frustum_matrix", frustumMatrix);
		Shaders.ECHO.setMat4("model_view_matrix", modelViewMatrix);
		Shaders.ECHO.setMat4("projection_matrix", RenderSystem.getProjectionMatrix());
		GL32C.glDrawElements(GL32C.GL_TRIANGLES, ICOSPHERE.getIndices().length, GL32C.GL_UNSIGNED_INT, 0);

		GL32C.glDisable(GL32C.GL_CULL_FACE);

		super.render(renderState, poseStack, bufferSource, packedLight);

		poseStack.popPose();

		// https://shaders.properties/_astro/sampler2darrayshadow.B_BOj-ZN_tLJ18.webp
		renderState.priorState.restore();
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

		public static class PriorState {
			public int shaderProgram;

			public void preserve() {
				shaderProgram = GL32C.glGetInteger(GL32C.GL_CURRENT_PROGRAM);
			}

			public void restore() {
				GL32C.glUseProgram(shaderProgram);
			}
		}
	}
}
