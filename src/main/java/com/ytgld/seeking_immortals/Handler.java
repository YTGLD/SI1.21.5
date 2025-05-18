package com.ytgld.seeking_immortals;

import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class Handler {


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
