package com.ytgld.seeking_immortals.mixin.common;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("RETURN"), method = "canBeAffected", cancellable = true)
    private void canBeAffected(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir){
        LivingEntity living = (LivingEntity) (Object) this;
        if (Handler.hascurio(living, Items.nightmare_base_black_eye.get())) {
            if (effectInstance.is(MobEffects.BLINDNESS)||effectInstance.is(MobEffects.DARKNESS)) {
                cir.setReturnValue(false);
            }
        }
    }
}
