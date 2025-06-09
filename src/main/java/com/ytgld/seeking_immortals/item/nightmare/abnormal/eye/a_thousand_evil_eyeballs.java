package com.ytgld.seeking_immortals.item.nightmare.abnormal.eye;

import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class a_thousand_evil_eyeballs extends nightmare {

    public a_thousand_evil_eyeballs(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents, flag);
        if (Screen.hasShiftDown()) {
            pTooltipComponents.accept(Component.translatable("item.a_thousand_evil_eyeballs.tool.string").withStyle(ChatFormatting.DARK_RED));
        }else {
            pTooltipComponents.accept(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.RED));
            pTooltipComponents.accept(Component.literal(""));
            pTooltipComponents.accept(Component.translatable("item.a_thousand_evil_eyeballs.tool.string.1").withStyle(ChatFormatting.DARK_RED));
        }
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player){
            if (player.isCreative()){
                return true;
            }
        }
        return false;
    }
}
