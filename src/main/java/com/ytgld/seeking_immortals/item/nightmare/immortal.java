package com.ytgld.seeking_immortals.item.nightmare;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.config.Config;
import com.ytgld.seeking_immortals.init.Effects;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.INightmare;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.Consumer;

/**
 * 不朽轮回之印章
 * <p>
 * <p>
 * 受到攻击有80%的概率规避并反弹50%的伤害给攻击者
 * <p>
 * 如果攻击者生命值大于70%，则反弹150%的伤害并破除无敌帧
 * <p>
 * <p>
 * 每隔5秒对周围生物施加一层枯朽Buff，最多达到5级
 * <p>
 * 被施加的生物每级减少20%抗性,护甲,治疗和5%的伤害,移速,攻速
 * <p>
 * <p>
 * 若你被杀死，则对攻击者造成20%当前生命值的穿透伤害并附加10级枯朽（10秒）
 * <p>
 * <p>
 * 深渊和噩梦物品无效化
 */
public class immortal extends Item implements ICurioItem , INightmare {


    public immortal(Properties properties) {
        super(properties);
    }

    public static void hEvt(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof LivingEntity living){
            if (event.getEntity() instanceof Player player){
                int lvl = Mth.nextInt(RandomSource.create(),1,100);
                if (Handler.hascurio(player, Items.immortal.get())){
                    if (lvl<= Config.SERVER.immortal.getAsInt()){
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.NEUTRAL, 1F, 1F);
                        if (living.getHealth()<=living.getMaxHealth()*0.7f){
                            living.hurt(living.damageSources().dryOut(),event.getAmount()*0.5f);
                            event.setAmount(0);
                        }else {
                            living.invulnerableTime = 0;
                            living.hurt(living.damageSources().dryOut(),event.getAmount()*1.5f);
                            event.setAmount(0);
                        }
                    }
                }
            }
        }
    }
    public static void livDead(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof LivingEntity living){
            if (event.getEntity() instanceof Player player){
                if (Handler.hascurio(player, Items.immortal.get())){
                    living.hurt(living.damageSources().dryOut(),living.getHealth()*0.2f);
                    living.addEffect(new MobEffectInstance(Effects.dead,200,9));
                }
            }
        }
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player){
            Vec3 playerPos = player.position().add(0, 0.75, 0);
            int range = 8;
            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));

            for (LivingEntity living : entities){
                if (!living.is(player)){
                    if (player.tickCount%100==1){
                        living.addEffect(new MobEffectInstance(Effects.dead,600,0));

                        if (living.getEffect(Effects.dead)!=null){
                            if (living.getEffect(Effects.dead).getAmplifier()<5) {
                                living.addEffect(new MobEffectInstance(Effects.dead, 600, living.getEffect(Effects.dead).getAmplifier() + 1));
                            }else {
                                living.addEffect(new MobEffectInstance(Effects.dead, 600, 5));
                            }
                        }
                    }
                }
            }
        }
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip,flag);
        if (Screen.hasShiftDown()) {
           tooltip.accept(Component.literal(""));
           tooltip.accept(Component.translatable("item.immortal.tool.string").withStyle(ChatFormatting.RED));
           tooltip.accept(Component.translatable("item.immortal.tool.string.1").withStyle(ChatFormatting.RED));
           tooltip.accept(Component.literal(""));
           tooltip.accept(Component.translatable("item.immortal.tool.string.2").withStyle(ChatFormatting.RED));
           tooltip.accept(Component.translatable("item.immortal.tool.string.3").withStyle(ChatFormatting.RED));
           tooltip.accept(Component.literal(""));
           tooltip.accept(Component.translatable("item.immortal.tool.string.4").withStyle(ChatFormatting.RED));
           tooltip.accept(Component.literal(""));
           tooltip.accept(Component.translatable("item.immortal.tool.string.5").withStyle(ChatFormatting.RED));
       }else {
           tooltip.accept(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.DARK_RED));
           tooltip.accept(Component.literal(""));
           tooltip.accept(Component.translatable("item.immortal.tool.string.6").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
           tooltip.accept(Component.translatable("item.immortal.tool.string.7").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
           tooltip.accept(Component.translatable("item.immortal.tool.string.8").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
       }
    }
}
