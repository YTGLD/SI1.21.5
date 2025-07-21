package com.ytgld.seeking_immortals;

import com.mojang.logging.LogUtils;
import com.ytgld.seeking_immortals.config.ClientConfig;
import com.ytgld.seeking_immortals.config.Config;
import com.ytgld.seeking_immortals.item.tip.ToolTip;
import com.ytgld.seeking_immortals.test_entity.client.ErosionSoulRenderer;
import com.ytgld.seeking_immortals.test_entity.client.OrbEntityRenderer;
import com.ytgld.seeking_immortals.client.particle.ParticleRenderer;
import com.ytgld.seeking_immortals.client.particle.black;
import com.ytgld.seeking_immortals.event.now.EventHandler;
import com.ytgld.seeking_immortals.event.old.AdvancementEvt;
import com.ytgld.seeking_immortals.event.old.NewEvent;
import com.ytgld.seeking_immortals.init.*;
import com.ytgld.seeking_immortals.renderer.MRender;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.function.Function;

@Mod(SeekingImmortalsMod.MODID)
public class SeekingImmortalsMod
{
    public static final String MODID = "seeking_immortals";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ResourceLocation POST = ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
            "entity_outline");

    public static final ResourceLocation Distorted = ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
            "distorted");
    public static final ResourceLocation light = ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
            "light");

    public SeekingImmortalsMod(IEventBus eventBus, ModContainer modContainer) {

        NeoForge.EVENT_BUS.register(new NewEvent());
        NeoForge.EVENT_BUS.register(new AdvancementEvt());
        NeoForge.EVENT_BUS.register(new EventHandler());
        NeoForge.EVENT_BUS.register(ParticleRenderer.class);
        Entitys.REGISTRY.register(eventBus);
        Particles.PARTICLE_TYPES.register(eventBus);
        Effects.REGISTRY.register(eventBus);
        AttReg.REGISTRY.register(eventBus);
        LootReg.REGISTRY.register(eventBus);
        DataReg.REGISTRY.register(eventBus);
        Items.ITEMS.register(eventBus);

        Tab.TABS.register(eventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.Common);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT);
    }
    public static RenderLevelStageEvent.Stage stage_particles ;

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event){
            event.register(ToolTip.class, Function.identity());
        }

        @SubscribeEvent
        public static void RegisterStageEvent(RenderLevelStageEvent.RegisterStageEvent event) {
            RenderType renderType = MRender.black;
            stage_particles = event.register(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID, "black"),
                    renderType);
        }
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(Particles.black.get(), black.Provider::new);

        }
        @SubscribeEvent
        public static void RegisterRenderPipelinesEvent(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(Entitys.orb_entity.get(), OrbEntityRenderer::new);
            event.registerEntityRenderer(Entitys.erosion_soul.get(), ErosionSoulRenderer::new);

        }
        @SubscribeEvent
        public static void RegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
            event.registerPipeline(MRender.RenderPs.endBloodRenderPipeline);
            event.registerPipeline(MRender.RenderPs.LIGHTNINGBloodRenderPipeline);

        }
    }
}
