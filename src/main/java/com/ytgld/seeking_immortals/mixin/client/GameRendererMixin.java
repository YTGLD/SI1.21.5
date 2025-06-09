package com.ytgld.seeking_immortals.mixin.client;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.config.Config;
import com.ytgld.seeking_immortals.init.Items;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


    @Shadow @Final private Camera mainCamera;
    @Shadow @Final
    private Minecraft minecraft;
    @Shadow @Final private static ResourceLocation BLUR_POST_CHAIN_ID;

    @Shadow @Final private CrossFrameResourcePool resourcePool;

    @Inject(at = @At("RETURN"), method = "render")
    public void init(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {

        if (Config.SERVER.nightmare_base_black_eye.get()) {
            if (mainCamera.getEntity() instanceof Player player) {
                if (Handler.hascurio(player, Items.nightmare_base_black_eye.get())) {
                    if (player.getPersistentData().getFloat("blurEffectOFNightmare_base_black_eye").isPresent()) {
                        float f = player.getPersistentData().getFloat("blurEffectOFNightmare_base_black_eye").get();
                        if (moonstone1_21_1$getPlayerLookTarget(player.level(), player) != null && moonstone1_21_1$getPlayerLookTarget(player.level(), player) instanceof LivingEntity) {

                            float s = 5;
                            if (Handler.hascurio(player,Items.abnormal_eye.get())){
                                s*=0.2f;
                            }
                            if (f < s) {
                                player.getPersistentData().putFloat("blurEffectOFNightmare_base_black_eye", f + 0.1f);
                            }
                        } else {
                            if (f > 0) {
                                player.getPersistentData().putFloat("blurEffectOFNightmare_base_black_eye", f - 0.05f);
                            }
                        }
                        if (!(f < 1.0F)) {
                            PostChain postchain = this.minecraft.getShaderManager().getPostChain(BLUR_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
                            if (postchain != null) {
                                postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool, (p_409027_) -> {
                                    p_409027_.setUniform("Radius", f);
                                });
                            }
                        }
                    } else {
                        player.getPersistentData().putFloat("blurEffectOFNightmare_base_black_eye", 0f);
                    }
                }
            }
        }
    }
    @Unique
    public Entity moonstone1_21_1$getPlayerLookTarget(Level level, LivingEntity living) {
        Entity pointedEntity = null;
        double range = 20.0D;
        Vec3 srcVec = living.getEyePosition();
        Vec3 lookVec = living.getViewVector(1.0F);
        Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
        float var9 = 1.0F;
        List<Entity> possibleList = level.getEntities(living, living.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate(var9, var9, var9));
        double hitDist = 0;

        for (Entity possibleEntity : possibleList) {

            if (possibleEntity.isPickable()) {
                float borderSize = possibleEntity.getPickRadius();
                AABB collisionBB = possibleEntity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
                Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);

                if (collisionBB.contains(srcVec)) {
                    if (0.0D < hitDist || hitDist == 0.0D) {
                        pointedEntity = possibleEntity;
                        hitDist = 0.0D;
                    }
                } else if (interceptPos.isPresent()) {
                    double possibleDist = srcVec.distanceTo(interceptPos.get());

                    if (possibleDist < hitDist || hitDist == 0.0D) {
                        pointedEntity = possibleEntity;
                        hitDist = possibleDist;
                    }
                }
            }
        }
        return pointedEntity;
    }

}
