package com.ytgld.seeking_immortals.item.nightmare.super_nightmare;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.seeking_immortals.event.old.AdvancementEvt;
import com.ytgld.seeking_immortals.init.DataReg;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import com.ytgld.seeking_immortals.item.tip.AllTip;
import com.ytgld.seeking_immortals.item.tip.ToolTip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class nightmare_base_redemption extends nightmare implements SuperNightmare, AllTip {
    public nightmare_base_redemption(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new ToolTip(this,stack));
    }

    @Override
    public Map<Integer, String> tooltip() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"主啊 请赐予我力量");
        map.put(2,"增加十的伤害 生命 护甲");
        return map;
    }

    @Override
    public Map<Integer, String> element(ItemStack stack) {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"主啊 请赐予我力量");
        map.put(2,"增加十的伤害 生命 护甲");
        return map;
    }
    public Multimap<Holder<Attribute>, AttributeModifier> gets(SlotContext slotContext) {
        Multimap<Holder<Attribute>, AttributeModifier> linkedHashMultimap = HashMultimap.create();
        linkedHashMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage" + this.getDescriptionId()), 10, AttributeModifier.Operation.ADD_VALUE));
        linkedHashMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage" + this.getDescriptionId()), 10, AttributeModifier.Operation.ADD_VALUE));
        linkedHashMultimap.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage" + this.getDescriptionId()), 10, AttributeModifier.Operation.ADD_VALUE));
        return linkedHashMultimap;
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
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (!player.level().isClientSide) {
                slotContext.entity().getAttributes().removeAttributeModifiers(gets(slotContext));
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity() instanceof Player player) {
            if (!player.level().isClientSide) {
                slotContext.entity().getAttributes().addTransientAttributeModifiers(gets(slotContext));
            }
            if (player.level() instanceof ServerLevel serverLevel) {
                if (serverLevel.getRaidAt(player.blockPosition()) != null && serverLevel.getRaidAt(player.blockPosition()).isLoss()) {
                    if (stack.get(DataReg.tag) != null && !stack.get(DataReg.tag).getBoolean(AdvancementEvt.nightmare_base_redemption_down_and_out).isPresent()) {
                        player.addItem(new ItemStack(Items.nightmare_base_redemption_down_and_out.get()));
                        stack.get(DataReg.tag).putBoolean(AdvancementEvt.nightmare_base_redemption_down_and_out, true);
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_redemption.tool.string").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_black_eye.tool.string.1").withStyle(ChatFormatting.RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_redemption_deception").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_redemption_degenerate").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_redemption_down_and_out").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.hypocritical_self_esteem").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.nightmareeye.tool.string.2").withStyle(ChatFormatting.DARK_RED));

    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers
                .builder().addSlotModifier("nightmare",
                        new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()+"nightmare_base_redemption"),
                                3, AttributeModifier.Operation.ADD_VALUE)).build();

    }

}
