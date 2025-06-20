package gay.sylv.blight.client.impl.render.entity;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import gay.sylv.blight.api.entity.Echo;
import gay.sylv.blight.client.api.render.model.Icosphere;
import gay.sylv.blight.client.api.render.blight3d.BlightRenderPass;
import gay.sylv.blight.client.api.render.pipeline.BlightPipelines;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL32C;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class EchoRenderer extends EntityRenderer<Echo, EchoRenderer.EchoRenderState> {
	private static final Icosphere ICOSPHERE = new Icosphere(2);
	private static GpuBuffer vertexBuffer;
	private static GpuBuffer indexBuffer;

	public EchoRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(
			EchoRenderState renderState,
			PoseStack poseStack,
			MultiBufferSource bufferSource,
			int packedLight
	) {
		Minecraft mc = Minecraft.getInstance();
		GpuDevice device = RenderSystem.getDevice();
		CommandEncoder encoder = device.createCommandEncoder();

		poseStack.pushPose();
		poseStack.translate(0.0f, 0.5f, 0.0f);

		try (BlightRenderPass pass = (BlightRenderPass) encoder.createRenderPass(
				mc.getMainRenderTarget().getColorTexture(),
				OptionalInt.empty(),
				mc.getMainRenderTarget().getDepthTexture(),
				OptionalDouble.empty()
		)) {
			renderWithPipeline(renderState, poseStack, pass, BlightPipelines.ECHO_PASS_1);
		}

		try (BlightRenderPass pass = (BlightRenderPass) encoder.createRenderPass(
				mc.getMainRenderTarget().getColorTexture(),
				OptionalInt.empty(),
				mc.getMainRenderTarget().getDepthTexture(),
				OptionalDouble.empty()
		)) {
			renderWithPipeline(renderState, poseStack, pass, BlightPipelines.ECHO_PASS_2);
		}

		super.render(renderState, poseStack, bufferSource, packedLight);

		poseStack.popPose();
	}

	private void renderWithPipeline(
			EchoRenderState renderState,
			PoseStack poseStack,
			BlightRenderPass pass,
			RenderPipeline pipeline
	) {
		pass.setPipeline(pipeline);

		// A "local matrix" is a trick to perform transformations on the entity.
		// It's preferable to just a "scale" variable because it also allows for
		// translation and scaling in local space. It's also generally preferable
		// that the GPU do matrix multiplication.
		Matrix4f localMatrix = poseStack.last().pose();
		// This is the actual model view matrix in entity rendering.
		Matrix4f modelViewMatrix = RenderSystem.getModelViewMatrix();

		pass.setVertexBuffer(0, vertexBuffer);
		pass.setIndexBuffer(indexBuffer, VertexFormat.IndexType.INT);

		Camera camera = this.entityRenderDispatcher.camera;

		pass.setUniform("LocalMat",  localMatrix);
		pass.setUniform("ModelViewMat", modelViewMatrix);
		pass.setUniform("ProjMat", RenderSystem.getProjectionMatrix());
		pass.setUniform("CameraPos", camera.getPosition());
		// Inner
		draw(pass, 0.75f, 0.85f, 1.0f, 0.25f, 0.25f);
		// Middle
		draw(pass, 0.75f / 2.0f, 0.85f / 2.0f, 1.0f / 2.0f, 0.375f, 0.3f);
		// Outer
		draw(pass, 0.6f, 0.75f, 1.0f, 0.625f, 0.425f);
		draw(pass, 0.6f, 0.75f, 1.0f, 0.9f, 0.426f);
	}

	private void draw(RenderPass pass, float r, float g, float b, float a, float scale) {
		pass.setUniform("Color", r, g, b, a);
		pass.setUniform("Scale", scale);
		pass.drawIndexed(0, indexBuffer.size());
	}

	public static void init() {
		WorldRenderEvents.AFTER_SETUP.register(context -> {
			GL32C.glClear(GL32C.GL_STENCIL_BUFFER_BIT);
		});
		GpuDevice device = RenderSystem.getDevice();

		// Upload icosphere
		vertexBuffer = device.createBuffer(
				() -> "Echo Vertex Buffer",
				BufferType.VERTICES,
				BufferUsage.STATIC_WRITE,
				ICOSPHERE.getMeshData().vertexBuffer()
		);
		indexBuffer = device.createBuffer(
				() -> "Echo Index Buffer",
				BufferType.INDICES,
				BufferUsage.STATIC_WRITE,
				ICOSPHERE.getMeshData().indexBuffer()
		);
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
	}
}
