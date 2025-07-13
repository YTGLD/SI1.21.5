package com.ytgld.seeking_immortals.item.nightmare.base;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

public class revive_runestone  extends nightmare implements SuperNightmare {


    public revive_runestone(Properties properties) {
        super(properties);
    }

    public static void hurt (LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(Items.revive_runestone.get().getDefaultInstance())) {
                if (Handler.hascurio(player, Items.revive_runestone.get())) {
                    if (Mth.nextInt(RandomSource.create(), 0, 100) <= 25) {
                        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                        player.getCooldowns().addCooldown(Items.revive_runestone.get().getDefaultInstance(), 10);
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity living) {
                if (!player.getCooldowns().isOnCooldown(Items.revive_runestone.get().getDefaultInstance())) {
                    if (Handler.hascurio(player, Items.revive_runestone.get())) {
                        if (Mth.nextInt(RandomSource.create(), 0, 100) <= 25) {
                            player.heal(6);
                            player.getCooldowns().addCooldown(Items.revive_runestone.get().getDefaultInstance(), 10);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents, flag);
        pTooltipComponents.accept(Component.translatable("item.revive_runestone.tool.string.1").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.revive_runestone.tool.string.2").withStyle(ChatFormatting.DARK_RED));
    }

}
