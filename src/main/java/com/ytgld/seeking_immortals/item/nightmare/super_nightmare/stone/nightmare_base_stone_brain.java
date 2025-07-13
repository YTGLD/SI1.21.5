package com.ytgld.seeking_immortals.item.nightmare.super_nightmare.stone;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

public class nightmare_base_stone_brain extends nightmare implements SuperNightmare {

    public nightmare_base_stone_brain(Properties properties) {
        super(properties);
    }

    public static void hurts(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.nightmare_base_stone_brain.get())) {
                if (player.getHealth() >= player.getMaxHealth()) {
                    event.setAmount(0);
                } else {
                    event.setAmount(event.getAmount() * 1.25f);
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.nightmare_base_stone_brain.get())) {
                if (!(player.getHealth() >= player.getMaxHealth())) {
                    event.setAmount(event.getAmount() *0.75f);
                }
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_stone_brain.tool.string").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_stone_brain.tool.string.1").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_stone_brain.tool.string.2").withStyle(ChatFormatting.DARK_RED));
    }
}

