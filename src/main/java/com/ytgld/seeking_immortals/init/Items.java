package com.ytgld.seeking_immortals.init;

import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.item.nightmare.disintegrating_stone;
import com.ytgld.seeking_immortals.item.nightmare.eye;
import com.ytgld.seeking_immortals.item.nightmare.immortal;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_eye;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_heart;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.nightmare_base_black_eye_red;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.eye.tricky_puppets;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.apple;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.nightmare_base_fool_betray;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.nightmare_base_fool_bone;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.fool.nightmare_base_fool_soul;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.insight.nightmare_base_insight_collapse;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.insight.nightmare_base_insight_drug;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.insight.nightmare_base_insight_insane;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.insight.ring;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.*;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.redemption.hypocritical_self_esteem;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.redemption.nightmare_base_redemption_deception;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.redemption.nightmare_base_redemption_degenerate;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.redemption.nightmare_base_redemption_down_and_out;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.candle;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.nightmare_base_reversal_card;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.nightmare_base_reversal_mysterious;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.reversal.nightmare_base_reversal_orb;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.start.*;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.start.nightmare_base_start_pod;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.start.nightmare_base_start_power;
import com.ytgld.seeking_immortals.item.nightmare.super_nightmare.stone.*;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SeekingImmortalsMod.MODID);

    public static final DeferredHolder<Item,?> nightmare_base_black_eye =ITEMS.registerItem("nightmare_base_black_eye", nightmare_base_black_eye::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base =ITEMS.registerItem("nightmare_base", nightmare_base::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_black_eye_eye =ITEMS.registerItem("nightmare_base_black_eye_eye", nightmare_base_black_eye_eye::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_black_eye_heart =ITEMS.registerItem("nightmare_base_black_eye_heart", nightmare_base_black_eye_heart::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_black_eye_red =ITEMS.registerItem("nightmare_base_black_eye_red", nightmare_base_black_eye_red::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_stone =ITEMS.registerItem("nightmare_base_stone", nightmare_base_stone::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_stone_meet =ITEMS.registerItem("nightmare_base_stone_meet", nightmare_base_stone_meet::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_stone_virus =ITEMS.registerItem("nightmare_base_stone_virus", nightmare_base_stone_virus::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_stone_brain =ITEMS.registerItem("nightmare_base_stone_brain", nightmare_base_stone_brain::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_virus =ITEMS.registerItem("nightmare_virus", nightmare_virus::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_reversal =ITEMS.registerItem("nightmare_base_reversal", nightmare_base_reversal::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_reversal_orb =ITEMS.registerItem("nightmare_base_reversal_orb", nightmare_base_reversal_orb::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_reversal_card =ITEMS.registerItem("nightmare_base_reversal_card", nightmare_base_reversal_card::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_reversal_mysterious =ITEMS.registerItem("nightmare_base_reversal_mysterious", nightmare_base_reversal_mysterious::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_redemption =ITEMS.registerItem("nightmare_base_redemption", nightmare_base_redemption::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_redemption_deception =ITEMS.registerItem("nightmare_base_redemption_deception", nightmare_base_redemption_deception::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_redemption_degenerate =ITEMS.registerItem("nightmare_base_redemption_degenerate", nightmare_base_redemption_degenerate::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_redemption_down_and_out =ITEMS.registerItem("nightmare_base_redemption_down_and_out", nightmare_base_redemption_down_and_out::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_fool =ITEMS.registerItem("nightmare_base_fool", nightmare_base_fool::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_fool_soul =ITEMS.registerItem("nightmare_base_fool_soul", nightmare_base_fool_soul::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_fool_bone =ITEMS.registerItem("nightmare_base_fool_bone", nightmare_base_fool_bone::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_fool_betray =ITEMS.registerItem("nightmare_base_fool_betray", nightmare_base_fool_betray::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_insight =ITEMS.registerItem("nightmare_base_insight", nightmare_base_insight::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_insight_drug =ITEMS.registerItem("nightmare_base_insight_drug", nightmare_base_insight_drug::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_insight_insane =ITEMS.registerItem("nightmare_base_insight_insane", nightmare_base_insight_insane::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_insight_collapse =ITEMS.registerItem("nightmare_base_insight_collapse", nightmare_base_insight_collapse::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_start =ITEMS.registerItem("nightmare_base_start", nightmare_base_start::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_start_pod =ITEMS.registerItem("nightmare_base_start_pod", nightmare_base_start_pod::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> nightmare_base_start_egg =ITEMS.registerItem("nightmare_base_start_egg", nightmare_base_start_egg::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> end_bone =ITEMS.registerItem("end_bone", end_bone::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> candle =ITEMS.registerItem("candle", candle::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> apple =ITEMS.registerItem("apple", apple::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> ring =ITEMS.registerItem("ring", ring::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> immortal =ITEMS.registerItem("immortal", immortal::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> hypocritical_self_esteem =ITEMS.registerItem("hypocritical_self_esteem", hypocritical_self_esteem::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> disintegrating_stone =ITEMS.registerItem("disintegrating_stone", disintegrating_stone::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> eye =ITEMS.registerItem("eye", eye::new,new Item.Properties().stacksTo(1));

    public static final DeferredHolder<Item,?> nightmare_base_start_power =ITEMS.registerItem("nightmare_base_start_power", nightmare_base_start_power::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> tricky_puppets =ITEMS.registerItem("tricky_puppets", tricky_puppets::new,new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item,?> wolf =ITEMS.registerItem("wolf", wolf::new,new Item.Properties().stacksTo(1));

}
