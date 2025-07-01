package com.ytgld.seeking_immortals.test_entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.config.ClientConfig;
import com.ytgld.seeking_immortals.renderer.MRender;
import com.ytgld.seeking_immortals.test_entity.orb_entity;
import com.ytgld.seeking_immortals.test_entity.state.OrbEntityRenderState;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class OrbEntityRenderer<T extends orb_entity> extends net.minecraft.client.renderer.entity.EntityRenderer<T, OrbEntityRenderState> {
    public OrbEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(OrbEntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        float partialTick = renderState.partialTick;

        double x = Mth.lerp(partialTick, renderState.entity.xOld, renderState.entity.getX());
        double y = Mth.lerp(partialTick, renderState.entity.yOld, renderState.entity.getY());
        double z = Mth.lerp(partialTick, renderState.entity.zOld, renderState.entity.getZ());
        poseStack.pushPose();
        poseStack.translate(renderState.entity.getX()-x, renderState.entity.getY()-y,renderState.entity.getZ() -z);

        float f = 1.5f;
        double d2 = renderState.distanceToCameraSq;
        float f1 = (float)((1.0 - d2 / 256.0) * 1);
        if (f1 > 0.0F) {
            renderShadow(poseStack, bufferSource, renderState, f1, renderState.entity.level(), f);
        }

        if (ClientConfig.CLIENT_CONFIG.itemDurabilityMultiplier.get()) {
            if (renderState.entity.canSee) {
                renderSphere1(poseStack, bufferSource.getBuffer(MRender.endBloodOutline), 240, 0.15f);
            }
            setT(poseStack, renderState.entity, bufferSource.getBuffer(MRender.lightning_color_outline));
        }else {

            setT(poseStack, renderState.entity, bufferSource.getBuffer(MRender.endBlood));
        }
        if (renderState.entity.canSee) {
            renderSphere1(poseStack, bufferSource.getBuffer(MRender.endBlood), 240, 0.15f);
        }

        poseStack.popPose();

    }
    private static void renderShadow(
            PoseStack poseStack, MultiBufferSource bufferSource, OrbEntityRenderState renderState, float strength, LevelReader level, float baseSize
    ) {
        float yDistanceFromGround = (float) (renderState.entity.getY() - Mth.floor(renderState.entity.getY())); // 计算实体与地面的高度差
        float size = baseSize + (1 - yDistanceFromGround) * baseSize; // 靠近地面时半径变大
        float f = Math.min(strength / 0.5F, size);
        int i = Mth.floor(renderState.entity.getX() - size);
        int j = Mth.floor(renderState.entity.getX() + size);
        int k = Mth.floor(renderState.entity.getY() - f * 2);
        int l = Mth.floor(renderState.entity.getY());
        int i1 = Mth.floor(renderState.entity.getZ() - size);
        int j1 = Mth.floor(renderState.entity.getZ() + size);
        PoseStack.Pose posestack$pose = poseStack.last();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityShadow(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,"textures/gui/shadow.png")));
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = i1; k1 <= j1; k1++) {
            for (int l1 = i; l1 <= j; l1++) {
                blockpos$mutableblockpos.set(l1, 0, k1);
                ChunkAccess chunkaccess = level.getChunk(blockpos$mutableblockpos);

                for (int i2 = k; i2 <= l; i2++) {
                    blockpos$mutableblockpos.setY(i2);
                    float f1 = strength - (float)(renderState.entity.getY() - blockpos$mutableblockpos.getY()) * 0.1F;
                    renderBlockShadow(renderState,
                            posestack$pose, vertexconsumer, chunkaccess, level, blockpos$mutableblockpos, renderState.entity.getX(), renderState.entity.getY(), renderState.entity.getZ(), size, f1
                    );
                }
            }
        }
    }

    private static void renderBlockShadow(
            OrbEntityRenderState renderState,
            PoseStack.Pose pose,
            VertexConsumer consumer,
            ChunkAccess chunk,
            LevelReader level,
            BlockPos pos,
            double x,
            double y,
            double z,
            float size,
            float weight
    ) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = chunk.getBlockState(blockpos);
        if (blockstate.getRenderShape() != RenderShape.INVISIBLE && level.getMaxLocalRawBrightness(pos) > 3) {
            if (blockstate.isCollisionShapeFullBlock(chunk, blockpos)) {
                VoxelShape voxelshape = blockstate.getShape(chunk, blockpos);
                if (!voxelshape.isEmpty()) {
                    float f1 = weight * 0.5F;
                    if (f1 >= 0.0F) {

                        float yDistanceFromGround = (float) (renderState.entity.getY() - Mth.floor(renderState.entity.getY())); // 计算实体与地面的高度差
                        float alpha = 255.0F * (1.0F - yDistanceFromGround); // 根据高度差计算alpha值
                        int i = ARGB.color(Mth.floor(alpha), 255, 0, 255); // 使用计算得到的alpha值

                        AABB aabb = voxelshape.bounds();
                        double d0 = pos.getX() + aabb.minX;
                        double d1 = pos.getX() + aabb.maxX;
                        double d2 = pos.getY() + aabb.minY;
                        double d3 = pos.getZ() + aabb.minZ;
                        double d4 = pos.getZ() + aabb.maxZ;
                        float f2 = (float)(d0 - x);
                        float f3 = (float)(d1 - x);
                        float f4 = (float)(d2 - y);
                        float f5 = (float)(d3 - z);
                        float f6 = (float)(d4 - z);
                        float f7 = -f2 / 2.0F / size + 0.5F;
                        float f8 = -f3 / 2.0F / size + 0.5F;
                        float f9 = -f5 / 2.0F / size + 0.5F;
                        float f10 = -f6 / 2.0F / size + 0.5F;
                        shadowVertex(pose, consumer, i, f2, f4, f5, f7, f9);
                        shadowVertex(pose, consumer, i, f2, f4, f6, f7, f10);
                        shadowVertex(pose, consumer, i, f3, f4, f6, f8, f10);
                        shadowVertex(pose, consumer, i, f3, f4, f5, f8, f9);
                    }
                }
            }
        }
    }

    private static void shadowVertex(
            PoseStack.Pose pose, VertexConsumer consumer, int color, float offsetX, float offsetY, float offsetZ, float u, float v
    ) {
        Vector3f vector3f = pose.pose().transformPosition(offsetX, offsetY, offsetZ, new Vector3f());
        consumer.addVertex(vector3f.x(), vector3f.y(), vector3f.z(), color, u, v, OverlayTexture.NO_OVERLAY, 0Xff, 0.0F, 1.0F, 0.0F);
    }
    private  static void setT(PoseStack matrices,
                              orb_entity entity,
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
                .setColor(255 ,0 ,255, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(255 ,0 ,0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(255 ,0 ,0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(	255 ,0 ,255, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);
    }

    @Override
    public @NotNull OrbEntityRenderState createRenderState() {
        return new OrbEntityRenderState();
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(T p_entity, OrbEntityRenderState reusedState, float partialTick) {
        reusedState.entity = p_entity;
        reusedState.partialTick = partialTick;
    }
    public void renderSphere1(@NotNull PoseStack matrices, @NotNull VertexConsumer vertexConsumer, int light, float s) {
        int stacks = 20;
        int slices = 20;
        for (int i = 0; i < stacks; ++i) {
            float phi0 = (float) Math.PI * ((i + 0) / (float) stacks);
            float phi1 = (float) Math.PI * ((i + 1) / (float) stacks);

            for (int j = 0; j < slices; ++j) {
                float theta0 = (float) (2 * Math.PI) * ((j + 0) / (float) slices);
                float theta1 = (float) (2 * Math.PI) * ((j + 1) / (float) slices);

                float x0 = s * (float) Math.sin(phi0) * (float) Math.cos(theta0);
                float y0 = s * (float) Math.cos(phi0);
                float z0 = s * (float) Math.sin(phi0) * (float) Math.sin(theta0);

                float x1 = s * (float) Math.sin(phi0) * (float) Math.cos(theta1);
                float y1 = s * (float) Math.cos(phi0);
                float z1 = s * (float) Math.sin(phi0) * (float) Math.sin(theta1);

                float x2 = s * (float) Math.sin(phi1) * (float) Math.cos(theta1);
                float y2 = s * (float) Math.cos(phi1);
                float z2 = s * (float) Math.sin(phi1) * (float) Math.sin(theta1);

                float x3 = s * (float) Math.sin(phi1) * (float) Math.cos(theta0);
                float y3 = s * (float) Math.cos(phi1);
                float z3 = s * (float) Math.sin(phi1) * (float) Math.sin(theta0);

                vertexConsumer.addVertex(matrices.last().pose(), x0, y0, z0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x1, y1, z1).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x2, y2, z2).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x3, y3, z3).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
            }
        }
    }
}

