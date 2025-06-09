package com.ytgld.seeking_immortals.mixin.outline;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.renderer.DistortedDefaultFramebufferSets;
import com.ytgld.seeking_immortals.renderer.MDistorted;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class Distorted implements MDistorted {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract void needsUpdate();

    @Shadow @Final private LevelTargetBundle targets;

    @Unique
    private final DistortedDefaultFramebufferSets si1_21_4$defaultFramebufferSets = new DistortedDefaultFramebufferSets();

    @Unique
    private  RenderTarget _1_21_5$entityOutlineFramebuffer;
    @Unique
    private final DistortedDefaultFramebufferSets _1_21_5$defaultFramebufferSets = new DistortedDefaultFramebufferSets();


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
            this._1_21_5$defaultFramebufferSets.entityOutlineFramebuffer =
                    frameGraphBuilder.importExternal("distorted", this._1_21_5$entityOutlineFramebuffer);
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, Matrix4f projectionMatrix, FogParameters fogParameters, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        FramePass framepass = frameGraphBuilder.addPass("main_distorted");

        this.targets.entityOutline = framepass.readsAndWrites(this.targets.entityOutline);

        ResourceHandle<RenderTarget> handle4 = this._1_21_5$defaultFramebufferSets.entityOutlineFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();

            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
                    0, rendertarget.getDepthTexture(), 1.0);

        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMains(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, Matrix4f projectionMatrix, FogParameters fogParameters, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        int i = this.minecraft.getMainRenderTarget().width;
        int j = this.minecraft.getMainRenderTarget().height;

        PostChain postchain1 = this.minecraft.getShaderManager().getPostChain(SeekingImmortalsMod.Distorted, Set.of(DistortedDefaultFramebufferSets.MAIN, DistortedDefaultFramebufferSets.ENTITY_OUTLINE));

        if (postchain1 != null) {
            postchain1.addToFrame(frameGraphBuilder, i, j, this._1_21_5$defaultFramebufferSets, (renderPass -> {
                float r = 0f;
                float g = 0f;
                float b = 0f;
                float a = 33f;
                renderPass.setUniform("ColorModulate",r,g,b,a);
            }));
        }

    }
    @Override
    public RenderTarget si1_21_4$MDistorted() {
        return _1_21_5$entityOutlineFramebuffer;
    }

}
