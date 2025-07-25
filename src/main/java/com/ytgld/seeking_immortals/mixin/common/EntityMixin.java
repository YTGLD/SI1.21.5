package com.ytgld.seeking_immortals.mixin.common;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Effects;
import com.ytgld.seeking_immortals.init.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Vec3 position();

    @Inject(at = @At("RETURN"), method = "isInvulnerableToBase", cancellable = true)
    public void mhead(DamageSource p_20122_, CallbackInfoReturnable<Boolean> cir) {
        if ((Entity) (Object) this instanceof Player player) {
            if (player.hasEffect(Effects.invulnerable)){
                cir.setReturnValue(true);
            }
            if (Handler.hascurio(player, Items.nightmare_base_redemption_degenerate.get())) {
                if (p_20122_.is(DamageTypes.MAGIC) ||
                        p_20122_.is(DamageTypes.FALL) ||
                        p_20122_.is(DamageTypes.ON_FIRE) ||
                        p_20122_.is(DamageTypes.LAVA) ||
                        p_20122_.is(DamageTypes.IN_FIRE)) {
                    cir.setReturnValue(true);
                }
            }
            if (Handler.hascurio(player, Items.disintegrating_stone.get())) {
                if (p_20122_.is(DamageTypes.EXPLOSION)||
                        p_20122_.is(DamageTypes.PLAYER_EXPLOSION)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
