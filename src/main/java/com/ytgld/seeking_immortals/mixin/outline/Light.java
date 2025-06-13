package com.ytgld.seeking_immortals.mixin.outline;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.renderer.LightFramebufferSets;
import com.ytgld.seeking_immortals.renderer.MDistorted;
import com.ytgld.seeking_immortals.renderer.MLight;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class Light implements MLight {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract void needsUpdate();

    @Shadow @Final private LevelTargetBundle targets;

    @Unique
    private  RenderTarget _1_21_5$entityOutlineFramebuffer;
    @Unique
    private final LightFramebufferSets si1_21_4$lightFramebufferSets = new LightFramebufferSets();


    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (_1_21_5$entityOutlineFramebuffer != null) {
            _1_21_5$entityOutlineFramebuffer.destroyBuffers();
        }
    }
    @Inject(method = "initOutline", at = @At(value = "RETURN"))
    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {
        this._1_21_5$entityOutlineFramebuffer = new TextureTarget(
                "Entity Outline For Distorted", this.minecraft.getWindow().getWidth(),
                this.minecraft.getWindow().getHeight(), true
        );
    }
    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        this._1_21_5$entityOutlineFramebuffer.blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTexture());
    }

    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        this.needsUpdate();
        if (this._1_21_5$entityOutlineFramebuffer != null) {
            this._1_21_5$entityOutlineFramebuffer.resize(width, height);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, Matrix4f projectionMatrix, FogParameters fogParameters, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        if (this._1_21_5$entityOutlineFramebuffer != null) {
            this.si1_21_4$lightFramebufferSets.entityOutlineFramebuffer =
                    frameGraphBuilder.importExternal("light", this._1_21_5$entityOutlineFramebuffer);
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, Matrix4f projectionMatrix, FogParameters fogParameters, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        FramePass framepass = frameGraphBuilder.addPass("main_light");

        this.targets.entityOutline = framepass.readsAndWrites(this.targets.entityOutline);

        ResourceHandle<RenderTarget> handle4 = this.si1_21_4$lightFramebufferSets.entityOutlineFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();

            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
                    0, rendertarget.getDepthTexture(), 1.0);

        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMains(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, Matrix4f matrix4f, FogParameters fogParameters, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {

        PostChain postchain1 = this.minecraft.getShaderManager().getPostChain(SeekingImmortalsMod.light, Set.of(LightFramebufferSets.MAIN, LightFramebufferSets.ENTITY_OUTLINE));

        Player player = this.minecraft.player;
        int width = this.minecraft.getMainRenderTarget().width;
        int height = this.minecraft.getMainRenderTarget().height;
        if (player  != null) {
            if (postchain1 != null) {
                postchain1.addToFrame(frameGraphBuilder, width, height, this.si1_21_4$lightFramebufferSets, (renderPass -> {
                    renderPass.setUniform("Radius", 5.0f);
                }));
            }
        }


    }
    @Override
    public RenderTarget si1_21_4$MLight() {
        return _1_21_5$entityOutlineFramebuffer;
    }

}
