package com.ytgld.seeking_immortals.client.particle;

import com.ytgld.seeking_immortals.init.Effects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class black extends TextureSheetParticle {
    public int atTime = 30;
    public int live = 30;

    public black(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ) {
        super(level, x, y, z, movementX, movementY, movementZ);
        this.lifetime = 30;
        this.alpha = 0;
    }
    @Override
    protected int getLightColor(float p_107249_) {
        return 240;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    private final List<Vec3> trailPositions = new ArrayList<>();
    private LivingEntity living ;
    public void tick() {
        if (atTime>0) {
            trailPositions.add(new Vec3(this.x, this.y, this.z));
        }

        if (trailPositions.size() > 100 || atTime <= 0) {
            if (!trailPositions.isEmpty()) {
                trailPositions.removeFirst();
            }
        }
        if (living == null) {
            Vec3 playerPos = this.getPos();
            float range = 16;

            List<LivingEntity> entities =
                    this.level.getEntitiesOfClass(LivingEntity.class,
                            new AABB(playerPos.x - range,
                                    playerPos.y - range,
                                    playerPos.z - range,
                                    playerPos.x + range,
                                    playerPos.y + range,
                                    playerPos.z + range));
            for (LivingEntity entity : entities) {
                if (entity.hasEffect(Effects.erosion)) {
                    living = entity;
                }
            }
        }
        if (living != null) {
            setv(living);
        }
        this.alpha = 0;
        atTime--;
        if (atTime<=0){
            live--;
            if (live <= 0) {
                this.remove();
            }
        }
        super.tick();
    }
   public void setv(Entity e){
       Vec3 targetPos = e.position().add(0, 0, 0); // 将 Y 坐标增加 heightOffset

       Vec3 currentPos = this.getPos();
       Vec3 direction = targetPos.subtract(currentPos).normalize();

       Vec3 currentDirection = new Vec3(this.xd, this.yd, this.zd).normalize();

       double angle = Math.acos(currentDirection.dot(direction)) * (180.0 / Math.PI);

       if (angle > 8.5) {
           double angleLimit = Math.toRadians(8.5); // 将5度转为弧度

           Vec3 limitedDirection = currentDirection.scale(Math.cos(angleLimit)) // 计算缩放因子
                   .add(direction.normalize().scale(Math.sin(angleLimit))); // 根据目标方向进行调整

           this.setParticleSpeed(limitedDirection.x * 0.25f, limitedDirection.y * 0.25f, limitedDirection.z * 0.25f);
       } else {
           this.setParticleSpeed(direction.x * 0.25f, direction.y * 0.25f, direction.z * 0.25f);
       }
   }
    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }


        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            black particle = new black(level, x, y, z, (float) xSpeed, (float) ySpeed, (float) zSpeed);
            particle.pickSprite(this.sprite);
            return particle;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }
    }
}

