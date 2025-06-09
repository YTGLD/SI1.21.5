package com.ytgld.seeking_immortals.mixin.light;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


    @Shadow @Final private Camera mainCamera;
    @Shadow @Final
    private Minecraft minecraft;

    @Shadow @Final private CrossFrameResourcePool resourcePool;

    @Inject(at = @At("RETURN"), method = "render")
    public void init(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(true);

        if (minecraft!=null&&minecraft.player!=null&&minecraft.player.tickCount>1) {
            PostChain postchain = this.minecraft.getShaderManager().
                    getPostChain(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID, "blur_si"),
                            LevelTargetBundle.MAIN_TARGETS);

            float f = minecraft.level.getSkyDarken(1.0F);
            float f1;
            if (minecraft.level.getSkyFlashTime() > 0) {
                f1 = 1.0F;
            } else {
                f1 = f * 0.95F + 0.05F;
            }

            Vec3 vec3 =this.minecraft.player.position();

            float f2 = this.minecraft.options.darknessEffectScale().get().floatValue();
            float f3 = this.minecraft.player.getEffectBlendFactor(MobEffects.DARKNESS, partialTicks) * f2;
            float f4 = 1;
            float f6 = this.minecraft.player.getWaterVision();
            float f5;
            if (this.minecraft.player.hasEffect(MobEffects.NIGHT_VISION)) {
                f5 = GameRenderer.getNightVisionScale(this.minecraft.player, partialTicks);
            } else if (f6 > 0.0F && this.minecraft.player.hasEffect(MobEffects.CONDUIT_POWER)) {
                f5 = f6;
            } else {
                f5 = 0.0F;
            }

            Vector3f vector3f = new Vector3f(f, f, 1.0F).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float f7 =  1.5F;
            float f8 = 0;
            float f9 = this.minecraft.options.gamma().get().floatValue();

            if (postchain != null) {
                postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool, (p_409027_) -> {

                    p_409027_.setUniform("AmbientLightFactor", new float[]{f8});
                    p_409027_.setUniform("SkyFactor", new float[]{f1});
                    p_409027_.setUniform("BlockFactor", new float[]{f7});
                    p_409027_.setUniform("UseBrightLightmap", new int[]{1});
                    p_409027_.setUniform("SkyLightColor", new float[]{vector3f.x, vector3f.y, vector3f.z});
                    p_409027_.setUniform("NightVisionFactor", new float[]{f5});
                    p_409027_.setUniform("DarknessScale", new float[]{f4});
                    p_409027_.setUniform("DarkenWorldFactor", 1f);
                    p_409027_.setUniform("BrightnessFactor", new float[]{Math.max(0.0F, f9 - f3)});

                    p_409027_.setUniform("fragPos", new float[]{(float) 0, (float)0, (float) 0});
                    p_409027_.setUniform("positionL", new float[]{(float) vec3.x, (float) vec3.y, (float) vec3.z});
                    p_409027_.setUniform("colorL", new float[]{(float) 1, (float) 0, (float) 1});
                    p_409027_.setUniform("radiusL", new float[]{(float)10});
                    p_409027_.setUniform("Time", new float[]{(float) minecraft.getFrameTimeNs()});
                });
            }

        }
    }
}
