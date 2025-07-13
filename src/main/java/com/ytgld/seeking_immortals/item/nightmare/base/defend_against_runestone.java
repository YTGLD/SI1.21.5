package com.ytgld.seeking_immortals.item.nightmare.base;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.DataReg;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

/** 抵御符石
 * <P>
 * <P>
 * 受击时有概率获得抗性II
 * <P>
 * 攻击时有概率对目标造成虚弱II
 * <P>
 */
public class defend_against_runestone extends nightmare implements SuperNightmare {

    public defend_against_runestone(Properties properties) {
        super(properties);
    }

    public static void hurt (LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(Items.defend_against_runestone.get().getDefaultInstance())) {
                if (Handler.hascurio(player, Items.defend_against_runestone.get())) {
                    if (Mth.nextInt(RandomSource.create(), 0, 100) <= 25) {
                        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 100, 1));
                        player.getCooldowns().addCooldown(Items.defend_against_runestone.get().getDefaultInstance(),30);
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity living) {
                if (!player.getCooldowns().isOnCooldown(Items.defend_against_runestone.get().getDefaultInstance())) {

                    if (Handler.hascurio(player, Items.defend_against_runestone.get())) {
                        if (Mth.nextInt(RandomSource.create(), 0, 100) <= 25) {
                            living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
                            player.getCooldowns().addCooldown(Items.defend_against_runestone.get().getDefaultInstance(),30);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents, flag);
        pTooltipComponents.accept(Component.translatable("item.defend_against_runestone.tool.string.1").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.defend_against_runestone.tool.string.2").withStyle(ChatFormatting.DARK_RED));
    }
}
