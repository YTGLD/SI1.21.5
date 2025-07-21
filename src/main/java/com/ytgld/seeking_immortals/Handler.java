package com.ytgld.seeking_immortals;

import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public class Handler {

    public static float getDistanceToGround(Entity entity){
        Vec3 position = entity.position();
        BlockPos blockPos = new BlockPos((int) position.x, (int) position.y, (int) position.z);

        // 获取该位置下方的最近非空气方块位置
        BlockPos groundPos = blockPos.below();
        while (groundPos.getY() > -100 && entity.level().getBlockState(groundPos).isAir()) {
            groundPos = groundPos.below();
        }
        Vec3 groundCenter = new Vec3(groundPos.getX() + 0.5, groundPos.getY() + 0.5, groundPos.getZ() + 0.5);
        return (float) position.distanceTo(groundCenter);
    }

    public static boolean hascurio(LivingEntity entity, Item curio) {

        if (CuriosApi.getCuriosInventory(entity).isPresent()) {
            if (CuriosApi.getCuriosInventory(entity).get().isEquipped(Items.immortal.get())) {
                if (curio instanceof nightmare) {
                    return false;
                }
            }
        }
        if (CuriosApi.getCuriosInventory(entity).isPresent()
                && !CuriosApi.getCuriosInventory(entity).get().isEquipped(Items.nightmare_base.get())) {
            if (curio instanceof SuperNightmare) {
                return false;
            }
        }
        return CuriosApi.getCuriosInventory(entity).isPresent()
                && CuriosApi.getCuriosInventory(entity).get().isEquipped(curio);
    }

}
