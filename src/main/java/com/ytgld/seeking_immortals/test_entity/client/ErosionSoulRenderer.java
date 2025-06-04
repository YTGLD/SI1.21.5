package com.ytgld.seeking_immortals.test_entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.seeking_immortals.config.ClientConfig;
import com.ytgld.seeking_immortals.renderer.MRender;
import com.ytgld.seeking_immortals.test_entity.erosion_soul;
import com.ytgld.seeking_immortals.test_entity.state.ErosionSoulState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ErosionSoulRenderer <T extends erosion_soul> extends net.minecraft.client.renderer.entity.EntityRenderer<T, ErosionSoulState> {
    public ErosionSoulRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ErosionSoulState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        float partialTick = renderState.partialTick;

        double x = Mth.lerp(partialTick, renderState.entity.xOld, renderState.entity.getX());
        double y = Mth.lerp(partialTick, renderState.entity.yOld, renderState.entity.getY());
        double z = Mth.lerp(partialTick, renderState.entity.zOld, renderState.entity.getZ());
        poseStack.pushPose();
        poseStack.translate(renderState.entity.getX()-x, renderState.entity.getY()-y,renderState.entity.getZ() -z);
        if (ClientConfig.CLIENT_CONFIG.itemDurabilityMultiplier.get()) {
            setT(poseStack, renderState.entity, bufferSource.getBuffer(MRender.black));
        }
        poseStack.popPose();

    }

    private  static void setT(PoseStack matrices,
                              erosion_soul entity,
                              VertexConsumer vertexConsumers)
    {
        matrices.pushPose();

        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.position().x, prevPos.y - entity.position().y, prevPos.z - entity.position().z);
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.position().x, currPos.y - entity.position().y, currPos.z - entity.position().z);

            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());

            renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, RenderType.lightning(),0.1f);
        }
        matrices.popPose();
    }

    public static void renderBlood(PoseStack poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, float a, RenderType renderType, float r) {
        int segmentCount = 16; // 圆柱横向细分数

        for (int i = 0; i < segmentCount; i++) {
            double angle1 = (2 * Math.PI * i) / segmentCount;
            double angle2 = (2 * Math.PI * (i + 1)) / segmentCount;

            double x1 = Math.cos(angle1) * r;
            double z1 = Math.sin(angle1) * r;
            double x2 = Math.cos(angle2) * r;
            double z2 = Math.sin(angle2) * r;

            Vec3 up1 = start.add(x1, 0, z1);
            Vec3 up2 = start.add(x2, 0, z2);
            Vec3 down1 = end.add(x1, 0, z1);
            Vec3 down2 = end.add(x2, 0, z2);


            addSquare(vertexConsumer, poseStack, up1, up2, down1, down2, a);
        }
    }
    private static void addSquare(VertexConsumer vertexConsumer, PoseStack poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, float alpha) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack.last().pose(), (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(0 ,0 ,0, (int) (alpha * 255))
                .setUv2(0,0)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(0 ,0 ,0, (int) (alpha * 255))
                .setUv2(0,0)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(0 ,0 ,0, (int) (alpha * 255))
                .setUv2(0,0)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(	0 ,0 ,0, (int) (alpha * 255))
                .setUv2(0,0)
                .setNormal(0, 0, 1);
    }

    @Override
    public @NotNull ErosionSoulState createRenderState() {
        return new ErosionSoulState();
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(T p_entity, ErosionSoulState reusedState, float partialTick) {
        reusedState.entity = p_entity;
        reusedState.partialTick = partialTick;
    }
}


