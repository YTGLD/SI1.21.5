package com.ytgld.seeking_immortals.mixin;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("RETURN"), method = "getMaxHealth", cancellable = true)
    private void SeekingImmortalscreateAttributes(CallbackInfoReturnable<Float> cir){
        LivingEntity living = (LivingEntity) (Object) this;
        if (Handler.hascurio(living, Items.apple.get())){
            cir.setReturnValue(30f);
        }
    }
}
