package com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

public class candle  extends nightmare implements SuperNightmare {

    public candle(Properties properties) {
        super(properties);
    }

    public static void hurt(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.candle.get())){
                if (!player.getCooldowns().isOnCooldown(Items.candle.get().getDefaultInstance())) {

                    if (player.getHealth() >= player.getMaxHealth()) {

                        event.setAmount(event.getAmount() * 11);
                        int s = (int) (event.getAmount() / 10);

                        if (event.getAmount() > player.getHealth()) {
                            event.setAmount(0);
                            player.setHealth(1);
                        }
                        if (s > 5 * 20) {
                            s = 5 * 20;
                        }
                        player.invulnerableTime += s;
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.RAVAGER_ROAR, SoundSource.NEUTRAL, 0.5f, 0.5f);

                        player.getCooldowns().addCooldown(Items.candle.get().getDefaultInstance(), 150);
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.candle.get())){
                if (player.invulnerableTime>0){
                    event.setAmount(event.getAmount()*2);
                }
            }
        }
    }
    public static void heal(LivingHealEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.candle.get())){
                if (player.invulnerableTime>0){
                    event.setAmount(event.getAmount()*2);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.1").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.2").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.3").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.4").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.5").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.candle.tool.string.6").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
}
