package com.ytgld.seeking_immortals.init;

import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import com.ytgld.seeking_immortals.test_entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Entitys {
    public static final DeferredRegister.Entities REGISTRY =
            DeferredRegister.createEntities(SeekingImmortalsMod.MODID);
    public static final DeferredHolder<EntityType<?>,EntityType<orb_entity>> orb_entity = REGISTRY.register("orb_entity", ()->
            EntityType.Builder.of(orb_entity::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50) .build(ResourceKey.create(Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID, "orb_entity"))));
}
