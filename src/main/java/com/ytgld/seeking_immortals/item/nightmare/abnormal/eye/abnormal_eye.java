package com.ytgld.seeking_immortals.item.nightmare.abnormal.eye;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.init.DataReg;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 提供夜视效果
 * <p>
 * <p>
 * 强制解锁下列罪孽
 * <p>
 * 千邪眼球：
 * <p>
 *      杀死凋零强制解锁
 */
public class abnormal_eye extends nightmare {

    public abnormal_eye(Properties properties) {
        super(properties);
    }

    public static void dieEqItem(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof WitherBoss) {
                if (Handler.hascurio(player, Items.abnormal_eye.get())) {
                    CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                        Map<String, ICurioStacksHandler> curios = handler.getCurios();
                        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                            ICurioStacksHandler stacksHandler = entry.getValue();
                            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                            for (int i = 0; i < stacksHandler.getSlots(); i++) {
                                ItemStack stack = stackHandler.getStackInSlot(i);
                                if (stack.isEmpty()&&!Handler.hascurio(player, Items.a_thousand_evil_eyeballs.get())){
                                    if (stacksHandler.getIdentifier().equals("nightmare")) {
                                        stackHandler.setStackInSlot(i, Items.a_thousand_evil_eyeballs.get().getDefaultInstance());
                                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.NEUTRAL, 1, 1);
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers
                .builder().addSlotModifier("nightmare",
                        new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()+"abnormal_eye"),
                                1, AttributeModifier.Operation.ADD_VALUE)).build();

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> pTooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, pTooltipComponents,flag);
        pTooltipComponents.accept(Component.translatable("item.abnormal_eye.tool.string").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal(""));
        pTooltipComponents.accept(Component.translatable("item.abnormal.tool.string").withStyle(ChatFormatting.RED));
        pTooltipComponents.accept(Component.translatable("item.seeking_immortals.a_thousand_evil_eyeballs").withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.accept(Component.literal("  ")
                .append(Component.translatable("item.seeking_immortals.give.a_thousand_evil_eyeballs")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFF8B658B))));

    }
}
