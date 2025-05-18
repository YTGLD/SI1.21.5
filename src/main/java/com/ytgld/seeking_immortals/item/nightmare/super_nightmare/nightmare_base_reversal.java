package com.ytgld.seeking_immortals.item.nightmare.super_nightmare;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.event.old.AdvancementEvt;
import com.ytgld.seeking_immortals.init.DataReg;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.function.Consumer;

public class nightmare_base_reversal extends nightmare implements SuperNightmare {


    public static final String att = "Attrib";

    public nightmare_base_reversal(Properties properties) {
        super(properties);
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


    public static void LivingDeathEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.nightmare_base_reversal.get())) {
                CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                    Map<String, ICurioStacksHandler> curios = handler.getCurios();
                    for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                        ICurioStacksHandler stacksHandler = entry.getValue();
                        IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                        for (int i = 0; i < stacksHandler.getSlots(); i++) {
                            ItemStack stack = stackHandler.getStackInSlot(i);
                            if (stack.is(Items.nightmare_base_reversal.get())) {
                                if (stack.get(DataReg.tag) != null) {
                                    stack.get(DataReg.tag).putInt(att, 96);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide) {
            if (slotContext.entity().tickCount>=20) {
                slotContext.entity().getAttributes().addTransientAttributeModifiers(geta(stack));
            }
        }
        if (slotContext.entity().hasEffect(MobEffects.POISON)) {

            if (slotContext.entity().getHealth() <= 1) {
                if (stack.get(DataReg.tag) != null) {
                    if (stack.get(DataReg.tag).getInt(att).isPresent()) {
                        if (stack.get(DataReg.tag).getBoolean(AdvancementEvt.nightmare_base_reversal_orb).isPresent()) {
                            if (!stack.get(DataReg.tag).getBoolean(AdvancementEvt.nightmare_base_reversal_orb).get()) {
                                if (slotContext.entity() instanceof Player player) {
                                    player.addItem(new ItemStack(Items.nightmare_base_reversal_orb.get()));
                                }
                                stack.get(DataReg.tag).putBoolean(AdvancementEvt.nightmare_base_reversal_orb, true);
                            }
                        }
                    }
                }
                if (slotContext.entity().level() instanceof ServerLevel serverLevel) {
                    slotContext.entity().kill(serverLevel);
                }
            }
        }
        if (stack.get(DataReg.tag) != null) {
            if (!Handler.hascurio(slotContext.entity(), Items.nightmare_base_reversal_card.get())) {
                if (stack.get(DataReg.tag).getInt(att).isPresent()) {
                    if (stack.get(DataReg.tag).getInt(att).get() >= 4) {
                        if (slotContext.entity() instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem().getDefaultInstance())) {
                            stack.get(DataReg.tag).putInt(att, stack.get(DataReg.tag).getInt(att).get() - 4);
                            player.getCooldowns().addCooldown(stack.getItem().getDefaultInstance(), 20);
                        }
                    }
                }else {
                    stack.get(DataReg.tag).putInt(att,0);
                }
            } else {
                if (stack.get(DataReg.tag).getInt(att).isPresent()) {
                    if (stack.get(DataReg.tag).getInt(att).get() >= -46) {
                        if (slotContext.entity() instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem().getDefaultInstance())) {
                            stack.get(DataReg.tag).putInt(att, stack.get(DataReg.tag).getInt(att).get() - 2);
                            player.getCooldowns().addCooldown(stack.getItem().getDefaultInstance(), 20);
                        }
                    }
                }else {
                    stack.get(DataReg.tag).putInt(att,0);
                }
            }
        } else {
            stack.set(DataReg.tag, new CompoundTag());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().getAttributes().removeAttributeModifiers(geta(stack));
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers
                .builder().addSlotModifier("nightmare",
                        new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()+"nightmare_base_reversal"),
                                3, AttributeModifier.Operation.ADD_VALUE)).build();

    }

    public Multimap<Holder<Attribute>, AttributeModifier> geta(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> get = HashMultimap.create();

        if (stack.get(DataReg.tag) != null) {
            if (stack.get(DataReg.tag).getInt(att).isPresent()) {
                double as = -stack.get(DataReg.tag).getInt(att).get();
                as /= 100;
                for (Holder<Attribute> attribute : BuiltInRegistries.ATTRIBUTE.asHolderIdMap()) {
                    if (attribute != (Attributes.MAX_HEALTH)) {
                        get.put(attribute, new AttributeModifier(ResourceLocation.withDefaultNamespace("base_attack_damage" + this.getDescriptionId()), as, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    }
                }
            }
        }
        return get;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_reversal.tool.string").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_reversal.tool.string.2").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_reversal.tool.string.3").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.nightmare_base_black_eye.tool.string.1").withStyle(ChatFormatting.RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_reversal_orb").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_reversal_card").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.nightmare_base_reversal_mysterious").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.candle").withStyle(ChatFormatting.DARK_RED));

        pTooltipComponents.accept(Component.literal(""));

        pTooltipComponents.accept(Component.translatable("item.nightmareeye.tool.string.2").withStyle(ChatFormatting.DARK_RED));

    }
}

