package com.ytgld.seeking_immortals.item.nightmare.super_nightmare;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.AttReg;
import com.ytgld.seeking_immortals.init.Entitys;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import com.ytgld.seeking_immortals.test_entity.erosion_soul;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

/**
 *  受伤后根据单次伤害释放一定数量的黑气，黑气击中造成大量伤害并减速目标
 * <p>
 * 增加20%受到伤害和生命值
 * <p>
 * <p>
 * 你的弹射物也能触发黑气的产生
 *
 */

public class the_erosion extends nightmare implements SuperNightmare {
    public the_erosion(Properties properties) {
        super(properties);
    }

    public static void hur(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity living) {


            if (event.getEntity() instanceof Player player) {
                if (!player.getCooldowns().isOnCooldown(Items.the_erosion.get().getDefaultInstance())) {
                    if (Handler.hascurio(player, Items.the_erosion.get())) {

                        float damage = event.getAmount();
                        int size = (int) (damage / 10);
                        if (size > 10) {
                            size = 10;
                        }
                        for (int i = 0; i < size + 1; i++) {
                            erosion_soul erosion_soul = new erosion_soul(Entitys.erosion_soul.get(), player.level());
                            erosion_soul.setOwner(player);
                            erosion_soul.living = living;
                            erosion_soul.setPos(player.position());
                            erosion_soul.setDeltaMovement(new Vec3(Mth.nextFloat(RandomSource.create(), -0.21F, 0.25F), 0.25f, Mth.nextFloat(RandomSource.create(), -0.311F, 0.25F)));

                            player.level().addFreshEntity(erosion_soul);

                            player.getCooldowns().addCooldown(Items.the_erosion.get().getDefaultInstance(), size * 10);
                        }
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(Items.the_erosion.get().getDefaultInstance())) {
                if (Handler.hascurio(player, Items.the_erosion.get())) {
                    for (int i = 0; i < 3; i++) {
                        erosion_soul erosion_soul = new erosion_soul(Entitys.erosion_soul.get(), player.level());
                        erosion_soul.setOwner(player);
                        erosion_soul.living = event.getEntity();
                        erosion_soul.setPos(player.position());
                        erosion_soul.setDeltaMovement(new Vec3(Mth.nextFloat(RandomSource.create(), -0.21F, 0.25F), 0.25f, Mth.nextFloat(RandomSource.create(), -0.311F, 0.25F)));

                        player.level().addFreshEntity(erosion_soul);

                        player.getCooldowns().addCooldown(Items.the_erosion.get().getDefaultInstance(), 3 * 10);
                    }
                }
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().getAttributes().addTransientAttributeModifiers(getAttributeModifiers());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().getAttributes().removeAttributeModifiers(getAttributeModifiers());

    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers() {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap  = HashMultimap.create();
        modifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()), 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifierMultimap.put(AttReg.hurt, new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()), 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifierMultimap;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip,flag);
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.translatable("item.the_erosion.tool.string").withStyle(ChatFormatting.DARK_RED));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.translatable("item.the_erosion.tool.string.2").withStyle(ChatFormatting.DARK_RED));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.translatable("item.the_erosion.tool.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFff4789))));
        tooltip.accept(Component.translatable("item.the_erosion.tool.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFff4789))));
    }
}
