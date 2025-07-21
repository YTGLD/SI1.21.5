package com.ytgld.seeking_immortals.init;

import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.effect.dead;
import com.ytgld.seeking_immortals.effect.erosion;
import com.ytgld.seeking_immortals.effect.invulnerable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Effects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SeekingImmortalsMod.MODID);
    public static final DeferredHolder<MobEffect,?> dead  =REGISTRY.register("dead",()->new dead());
    public static final DeferredHolder<MobEffect,?> erosion  =REGISTRY.register("erosion",()->new erosion());
    public static final DeferredHolder<MobEffect,?> invulnerable  =REGISTRY.register("invulnerable",()->new invulnerable());

}
