package com.ytgld.seeking_immortals.init;

import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> black;

    static {
        PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SeekingImmortalsMod.MODID);

        black = PARTICLE_TYPES.register("black", ()->{
            return new SimpleParticleType(false);
        });
    }
}
