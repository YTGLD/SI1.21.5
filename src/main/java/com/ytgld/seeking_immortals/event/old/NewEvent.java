package com.ytgld.seeking_immortals.event.old;

import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.event.CurioDamageEvent;
import com.ytgld.seeking_immortals.init.AttReg;
import com.ytgld.seeking_immortals.init.Effects;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.item.nightmare.abnormal.eye.abnormal_eye;
import com.ytgld.seeking_immortals.item.nightmare.base.defend_against_runestone;
import com.ytgld.seeking_immortals.item.nightmare.base.revive_runestone;
import com.ytgld.seeking_immortals.item.nightmare.base.strengthen_runestone;
import com.ytgld.seeking_immortals.item.nightmare.immortal;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.SuperNightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.extend.nightmare;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_eye;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_heart;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_red;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.apple;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.nightmare_base_fool_bone;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.insight.nightmare_base_insight_insane;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.nightmare_base_insight;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.nightmare_base_reversal;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.nightmare_base_start;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.nightmare_base_stone;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.redemption.nightmare_base_redemption_deception;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.candle;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.nightmare_base_reversal_orb;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.start.nightmare_base_start_pod;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.start.wolf;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.stone.end_bone;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.stone.nightmare_base_stone_brain;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.stone.nightmare_base_stone_virus;
import com.ytgld.seeking_immortals.item.nightmare.the_erosion;
import com.ytgld.seeking_immortals.item.tip.AllTip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioCanEquipEvent;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class NewEvent {
    public static float time= 0;

    @SubscribeEvent
    public void tick(LevelTickEvent.Post event){
        time++;
    }


    @SubscribeEvent
    public  void LivingExperienceDropEvent(LivingExperienceDropEvent event) {
        nightmare_base_insight.exp(event);
    }
    @SubscribeEvent
    public void CurioLivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                    ICurioStacksHandler stacksHandler = entry.getValue();
                    IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                    for (int i = 0; i < stacksHandler.getSlots(); i++) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        NeoForge.EVENT_BUS.post(new CurioDamageEvent(player, stack,event));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public void LivingHealEvent(LivingHealEvent event) {
        nightmare_base_reversal_orb.LivingHealEvent(event);
        nightmare_base_black_eye_heart.heal(event);
        candle.heal(event);
        if (event.getEntity() instanceof LivingEntity living){
            if (living.getAttribute(AttReg.heal)!=null){
                float attack = (float) living.getAttribute(AttReg.heal).getValue();
                event.setAmount(event.getAmount()*(attack));
            }
        }
    }

    @SubscribeEvent
    public void LivingHealEvent(LivingDeathEvent event) {
        nightmare_base_reversal.LivingDeathEvent(event);
        immortal.livDead(event);
        wolf.kill(event);
        nightmare_base_black_eye_red.kill(event);
        nightmare_base_insight_insane.LivingDeathEvents(event);
        abnormal_eye.dieEqItem(event);
    }

    @SubscribeEvent
    public void LivingHurtEvent(LivingIncomingDamageEvent event){
        apple.damage(event);
        end_bone.hurts(event);
        defend_against_runestone.hurt(event);
        revive_runestone.hurt(event);
        strengthen_runestone.hurt(event);
        nightmare_base_stone_virus.h(event);
        nightmare_base_black_eye_eye.attLook(event);
        nightmare_base_black_eye_heart.hurt(event);
        nightmare_base_stone.LivingHurtEvent(event);
        nightmare_base_stone_brain.hurts(event);
        nightmare_base_redemption_deception.LivingIncomingDamageEvent(event);
        nightmare_base_fool_bone.attLook(event);
        nightmare_base_insight_insane.damage(event);
        nightmare_base_start.damage(event);
        nightmare_base_start_pod.damage(event);
        candle.hurt(event);
        immortal.hEvt(event);
        wolf.attack(event);
        the_erosion.hur(event);
        if (event.getEntity().hasEffect(Effects.dead) && event.getEntity().getEffect(Effects.dead)!=null){
            float lvl = event.getEntity().getEffect(Effects.dead).getAmplifier();
            lvl *= 0.2f;
            event.setAmount(event.getAmount()*(1+lvl));

        }
        if (event.getAmount()>Integer.MAX_VALUE){
            event.setAmount(Integer.MAX_VALUE);
        }
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(handler -> {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();
            for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                ICurioStacksHandler stacksHandler = entry.getValue();
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int i = 0; i < stacksHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals(SeekingImmortalsMod.MODID)){

                        float s = event.getAmount();
                        if (s>Integer.MAX_VALUE){
                            event.setAmount(Integer.MAX_VALUE);
                        }
                    }
                }
            }
        });
        if (event.getSource().getEntity() instanceof LivingEntity living){
            if (living.getAttribute(AttReg.alL_attack)!=null){
                float attack = (float) living.getAttribute(AttReg.alL_attack).getValue();
                event.setAmount(event.getAmount()*(attack));
            }
        }

    }
    @SubscribeEvent
    public  void CurioCanEquipEvent(CurioCanEquipEvent event) {
        Item item = event.getStack().getItem();
        if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(SeekingImmortalsMod.MODID)) {
            if (Handler.hascurio(event.getEntity(), item)) {
                event.setEquipResult(TriState.FALSE);
            }
        }
    }

    @SubscribeEvent
    public  void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.getTags().contains(SeekingImmortalsMod.MODID+"nightmare")) {
            player.addItem(Items.nightmare_base.get().getDefaultInstance());
            player.addTag(SeekingImmortalsMod.MODID+"nightmare");
        }
    }
    @SubscribeEvent
    public void soulbattery(CriticalHitEvent event) {
       if (event.getEntity() instanceof Player living){
            if (living.getAttribute(AttReg.cit)!=null){
                float attack = (float) living.getAttribute(AttReg.cit).getValue();
                event.setDamageMultiplier(event.getDamageMultiplier()*(attack));
            }
        }

    }
    @SubscribeEvent
    public void soulbattery(PlayerEvent.BreakSpeed event) {
        if (event.getEntity() instanceof Player living){
            if (living.getAttribute(AttReg.dig)!=null){

                float dig = (float) living.getAttribute(AttReg.dig).getValue();

                event.setNewSpeed(event.getNewSpeed()*(dig));
            }
        }
    }
    @SubscribeEvent
    public void hurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player living){
            if (living.getAttribute(AttReg.hurt)!=null){
                float hurt = (float) living.getAttribute(AttReg.hurt).getValue();
                event.setAmount(event.getAmount()*(hurt));
            }
        }

    }
    @SubscribeEvent
    public void hurt(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof AllTip){
            event.getToolTip().add(1, Component.translatable(
                    "key.keyboard.left.shift").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFCD853F))));
        }
        if (event.getEntity() instanceof Player player) {
            if (!Handler.hascurio(player, Items.nightmare_base.get())) {
                if (event.getItemStack().getItem() instanceof SuperNightmare) {
                    List<Component> toolTip = event.getToolTip();
                    Random random = new Random();
                    for (int i = 0; i < toolTip.size(); i++) {
                        int randomLength = random.nextInt(25) + 1;
                        StringBuilder randomString = new StringBuilder();
                        for (int j = 0; j < randomLength; j++) {
                            randomString.append("§ka");
                        }
                        toolTip.set(i, Component.literal(randomString.toString()).withStyle(ChatFormatting.DARK_RED));
                    }
                }
            }
        }
        {
            ItemStack stack = event.getItemStack();
            Player player = event.getEntity();
            if (stack.getItem() instanceof SuperNightmare) {
                if (!Handler.hascurio(player, Items.nightmare_base.get())) {
                    event.getToolTip().add(1, Component.literal(""));
                    event.getToolTip().add(1, Component.translatable("seeking_immortals.super_nightmare.name.1").withStyle(ChatFormatting.DARK_RED));
                    event.getToolTip().add(1, Component.translatable("seeking_immortals.super_nightmare.name").withStyle(ChatFormatting.DARK_RED));
                } else {
                    event.getToolTip().add(1, Component.literal(""));
                    event.getToolTip().add(1, Component.translatable("seeking_immortals.super_nightmare.name.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFCD853F))));
                    event.getToolTip().add(1, Component.translatable("seeking_immortals.super_nightmare.name").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFCD853F))));
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void Color(ViewportEvent.RenderFog event){
        if (event.getCamera().getEntity() instanceof Player player) {
            if (Handler.hascurio(player, Items.a_thousand_evil_eyeballs.get())) {
                event.setCanceled(true);

                event.setFarPlaneDistance(event.getFarPlaneDistance()*0.33f);
                event.setNearPlaneDistance(event.getNearPlaneDistance()*0.33f);
            }
        }
    }
}
