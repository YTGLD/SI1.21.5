package com.ytgld.seeking_immortals.init;

import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AttReg {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, SeekingImmortalsMod.MODID);
    public static final DeferredHolder<Attribute,?> alL_attack = REGISTRY.register("allattack",()->{
        return new RangedAttribute("attribute.name.seeking_immortals.allattack", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> heal = REGISTRY.register("heal",()->{
        return new RangedAttribute("attribute.name.seeking_immortals.heal", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> cit = REGISTRY.register("cit",()->{
        return new RangedAttribute("attribute.name.seeking_immortals.cit", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> dig = REGISTRY.register("dig",()->{
        return new RangedAttribute("attribute.name.seeking_immortals.dig", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> hurt = REGISTRY.register("hurt",()->{
        return new RangedAttribute("attribute.name.seeking_immortals.res", 1, -1024, 1024).setSyncable(true);
    });
}
