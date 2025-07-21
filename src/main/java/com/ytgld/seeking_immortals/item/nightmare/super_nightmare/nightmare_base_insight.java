package com.ytgld.seeking_immortals.item.nightmare.super_nightmare;

import com.google.common.collect.Multimap;
import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import com.ytgld.seeking_immortals.item.tip.AllTip;
import com.ytgld.seeking_immortals.item.tip.ToolTip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class nightmare_base_insight extends nightmare implements SuperNightmare, AllTip {
    public nightmare_base_insight(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new ToolTip(this,stack));
    }
    public static void exp(LivingExperienceDropEvent event){
        if (event.getAttackingPlayer() instanceof Player player){
            if (Handler.hascurio(player,Items.nightmare_base_insight.get())){
                event.setDroppedExperience(event.getDroppedExperience()*4);
            }
        }
    }
    @Override
    public Map<Integer, String> tooltip() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"你感受到了另类的气息");
        map.put(2,"增加百分之三百经验掉落");
        return map;
    }

    @Override
    public Map<Integer, String> element(ItemStack stack) {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"你感受到了另类的气息");
        map.put(2,"增加百分之三百经验掉落");
        return map;
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player){
            if (CuriosApi.getCuriosInventory(player).isPresent()
                    && CuriosApi.getCuriosInventory(player).get().isEquipped(Items.immortal.get())){
                return true;
            }
            if (player.isCreative()){
                return true;
            }
        }
        return false;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_insight.tool.string").withStyle(ChatFormatting.DARK_RED));

        pTooltipComponents.accept(Component.translatable("item.nightmare_base_black_eye.tool.string.1").withStyle(ChatFormatting.RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_insight_collapse").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_insight_insane").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_insight_drug").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.nightmareeye.tool.string.2").withStyle(ChatFormatting.DARK_RED));

    }
    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers
                .builder().addSlotModifier("nightmare",
                        new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()+"nightmare_base_insight"),
                                3, AttributeModifier.Operation.ADD_VALUE)).build();

    }
}
