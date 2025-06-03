package com.ytgld.seeking_immortals.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender extends RenderType {

    public MRender(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static final OutputStateShard OUTLINE_TARGET = new OutputStateShard("set_outline", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebuffer framebuffer){
            if (framebuffer.si1_21_4$defaultFramebufferSets()!=null) {
                framebuffer.si1_21_4$defaultFramebufferSets().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.si1_21_4$defaultFramebufferSets();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static final OutputStateShard CAN_LOOK_BLOCK = new OutputStateShard("set_outline_can", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebuffer framebuffer){
            if (framebuffer.si1_21_4$defaultFramebufferSets()!=null) {
                return framebuffer.si1_21_4$defaultFramebufferSets();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });

    public static final OutputStateShard Distorted = new OutputStateShard("distorted", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MDistorted framebuffer){
            if (framebuffer.si1_21_4$MDistorted()!=null) {
                framebuffer.si1_21_4$MDistorted().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.si1_21_4$MDistorted();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static final RenderType Bluer = create(
            "light_si",
            1536,
            false,
            true,
            RenderPs.LIGHTNINGBloodRenderPipeline,
            RenderType.CompositeState.builder().setOutputState(CAN_LOOK_BLOCK).createCompositeState(false)
    );

    public static final RenderType endBlood = create(
            "end_si",
            1536,
            false,
            true,
            RenderPs.endBloodRenderPipeline,
            RenderType.CompositeState.builder()
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
                                            "textures/ging.png"), false, false)
                                    .build()
                    )
                    .createCompositeState(false)
    );
    public static final RenderType endBloodOutline = create(
            "end_si_outline",
            1536,
            false,
            true,
            RenderPs.endBloodRenderPipeline,
            RenderType.CompositeState.builder().setOutputState(OUTLINE_TARGET)
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
                                            "textures/ging.png"), false, false)
                                    .build()
                    )
                    .createCompositeState(false)
    );

    public static final RenderType lightning_color_outline = create(
            "lightning_color_outline",
            1536,
            false,
            true,
            RenderPs.LIGHTNINGBloodRenderPipeline,
            RenderType.CompositeState.builder().setOutputState(OUTLINE_TARGET).createCompositeState(false)
    );

    public static final Function<ResourceLocation, RenderType> Distorted_ENTITY_SOLID = Util.memoize(
            p_404030_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(p_404030_, TriState.FALSE, false))
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(true);
                return create("distorted_entity",
                        1536,
                        true,
                        false,
                        RenderPipelines.ENTITY_SOLID, rendertype$compositestate);
            }
    );
    public static class RenderPs{
        public static final RenderPipeline endBloodRenderPipeline=
                (RenderPipeline
                .builder(RenderPipeline.builder(MATRICES_SNIPPET, FOG_SNIPPET)
                        .withVertexShader(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,"core/rendertype_end_portal"))
                        .withFragmentShader(ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,"core/rendertype_end_portal"))
                        .withSampler("Sampler0")
                        .withUniform("GameTime",UniformType.FLOAT)
                        .withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation("pipeline/end_si")
                .withShaderDefine("PORTAL_LAYERS", 16).build());

        public static final RenderPipeline LIGHTNINGBloodRenderPipeline =
                (RenderPipeline.builder(MATRICES_COLOR_FOG_SNIPPET)
                        .withLocation("pipeline/light_si")
                        .withVertexShader("core/rendertype_lightning")
                        .withFragmentShader("core/rendertype_lightning")
                        .withCull(false)
                        .withDepthWrite(true)
                        .withBlend(BlendFunction.LIGHTNING)
                        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS).build());



    }
}
