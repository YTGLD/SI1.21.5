package com.ytgld.seeking_immortals.test_entity;

import com.ytgld.seeking_immortals.init.Effects;
import com.ytgld.seeking_immortals.init.Particles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class erosion_soul extends ThrowableItemProjectile {
    public erosion_soul(EntityType<? extends erosion_soul> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setNoGravity(true);
        this.noPhysics = true;
    }

    public boolean canSee = true;
    public int live = 50;
    public LivingEntity living;
    @Override
    protected void checkSupportingBlock(boolean onGround, @Nullable Vec3 movement) {

    }


    private final List<Vec3> trailPositions = new ArrayList<>();

    @Override
    public void tick() {
        if (canSee) {
            trailPositions.add(new Vec3(this.getX(), this.getY(), this.getZ()));
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 33||!canSee) {
                trailPositions.removeFirst();
            }
        }
        if (canSee) {
            if (living != null) {
                if (this.tickCount > 6) {
                    Vec3 targetPos = living.position().add(0, 0, 0); // 将 Y 坐标增加 heightOffset

                    Vec3 currentPos = this.position();
                    Vec3 direction = targetPos.subtract(currentPos).normalize();

                    Vec3 currentDirection = this.getDeltaMovement().normalize();

                    double angle = Math.acos(currentDirection.dot(direction)) * (180.0 / Math.PI);

                    if (angle > 15) {
                        double angleLimit = Math.toRadians(15); // 将5度转为弧度

                        Vec3 limitedDirection = currentDirection.scale(Math.cos(angleLimit)) // 计算缩放因子
                                .add(direction.normalize().scale(Math.sin(angleLimit))); // 根据目标方向进行调整

                        this.setDeltaMovement(limitedDirection.x * 0.25, limitedDirection.y * 0.25, limitedDirection.z * 0.25);
                    } else {
                        this.setDeltaMovement(direction.x * 0.25, direction.y * 0.25, direction.z * 0.25);
                    }
                }
            }
        }else {
            this.setDeltaMovement(0,0,0);
        }
        if (this.tickCount > 120) {
            canSee = false;
            live--;
        }
        if (live<=0) {
            this.discard();
        }
        if (canSee) {
            Vec3 playerPos = this.position();
            float range = 0.5f;
            List<LivingEntity> entities =
                    this.level().getEntitiesOfClass(LivingEntity.class,
                            new AABB(playerPos.x - range,
                                    playerPos.y - range,
                                    playerPos.z - range,
                                    playerPos.x + range,
                                    playerPos.y + range,
                                    playerPos.z + range));
            for (LivingEntity living : entities) {
                if (this.getOwner() != null && !living.is(this.getOwner())&&this.getOwner() instanceof Player player) {
                    if (this.tickCount > 30) {
                        living.invulnerableTime = 0;

                        living.hurt(living.damageSources().magic(), (float) (player.getAttributeValue(Attributes.MAX_HEALTH)+player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2F));



                        living.addEffect(new MobEffectInstance(Effects.erosion, 600, 1));
                        MobEffectInstance effect = living.getEffect(Effects.dead);
                        if (effect!=null){
                            if (effect.getAmplifier()<5) {
                                living.addEffect(new MobEffectInstance(Effects.dead, 600, effect.getAmplifier() + 1));
                            }else {
                                living.addEffect(new MobEffectInstance(Effects.dead, 600, 5));
                            }
                        }
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.AMBIENT, 1, 1);
                        canSee = false;
                    }
                }
            }
            super.tick();
        }
    }


    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.IRON_SWORD;
    }
}

