package com.ytgld.seeking_immortals.init;


import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class Tab {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SeekingImmortalsMod.MODID);
    public static final DeferredHolder<CreativeModeTab,?> ITEM = TABS.register("seeking_immortals",()-> CreativeModeTab.builder()
            .icon(()->new ItemStack(Items.nightmare_base.get()))
            .title(Component.translatable("itemGroup.seeking_immortals"))
            .displayItems((a,b)->{
                b.accept(new ItemStack(Items.immortal.get()));

                b.accept(new ItemStack(Items.nightmare_base.get()));
                b.accept(new ItemStack(Items.nightmare_base_black_eye.get()));
                b.accept(new ItemStack(Items.nightmare_base_black_eye_eye.get()));
                b.accept(new ItemStack(Items.nightmare_base_black_eye_heart.get()));
                b.accept(new ItemStack(Items.nightmare_base_black_eye_red.get()));
                b.accept(new ItemStack(Items.tricky_puppets.get()));


                b.accept(new ItemStack(Items.nightmare_base_stone.get()));
                b.accept(new ItemStack(Items.nightmare_base_stone_meet.get()));
                b.accept(new ItemStack(Items.nightmare_base_stone_virus.get()));
                b.accept(new ItemStack(Items.nightmare_base_stone_brain.get()));
                b.accept(new ItemStack(Items.end_bone.get()));


                b.accept(new ItemStack(Items.nightmare_base_reversal.get()));
                b.accept(new ItemStack(Items.nightmare_base_reversal_orb.get()));
                b.accept(new ItemStack(Items.nightmare_base_reversal_card.get()));
                b.accept(new ItemStack(Items.nightmare_base_reversal_mysterious.get()));
                b.accept(new ItemStack(Items.candle.get()));


                b.accept(new ItemStack(Items.nightmare_base_redemption.get()));
                b.accept(new ItemStack(Items.nightmare_base_redemption_deception.get()));
                b.accept(new ItemStack(Items.nightmare_base_redemption_degenerate.get()));
                b.accept(new ItemStack(Items.nightmare_base_redemption_down_and_out.get()));
                b.accept(new ItemStack(Items.hypocritical_self_esteem.get()));


                b.accept(new ItemStack(Items.nightmare_base_fool.get()));
                b.accept(new ItemStack(Items.nightmare_base_fool_soul.get()));
                b.accept(new ItemStack(Items.nightmare_base_fool_betray.get()));
                b.accept(new ItemStack(Items.nightmare_base_fool_bone.get()));
                b.accept(new ItemStack(Items.apple.get()));


                b.accept(new ItemStack(Items.nightmare_base_insight.get()));
                b.accept(new ItemStack(Items.nightmare_base_insight_drug.get()));
                b.accept(new ItemStack(Items.nightmare_base_insight_insane.get()));
                b.accept(new ItemStack(Items.nightmare_base_insight_collapse.get()));
                b.accept(new ItemStack(Items.ring.get()));


                b.accept(new ItemStack(Items.nightmare_base_start.get()));
                b.accept(new ItemStack(Items.nightmare_base_start_pod.get()));
                b.accept(new ItemStack(Items.nightmare_base_start_egg.get()));
                b.accept(new ItemStack(Items.nightmare_base_start_power.get()));

            })
            .build());
}


