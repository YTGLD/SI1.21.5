package com.ytgld.seeking_immortals.init;


import com.mojang.serialization.MapCodec;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.event.loot.DungeonLoot;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LootReg {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTRY =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SeekingImmortalsMod.MODID);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, ?> TD = REGISTRY.register("dungeon",()->{
        return DungeonLoot.CODEC.get();
    });
}
