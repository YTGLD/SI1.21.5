package com.ytgld.seeking_immortals.mixin.common;

import com.ytgld.seeking_immortals.init.AttReg;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(at = @At("RETURN"), method = "createAttributes", cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir){
        cir.setReturnValue(cir.getReturnValue()
                .add(AttReg.alL_attack,1)
                .add(AttReg.cit,1)
                .add(AttReg.heal,1)
                .add(AttReg.hurt,1)
                .add(AttReg.dig,1)
        );
    }
}
