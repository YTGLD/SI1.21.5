package com.ytgld.seeking_immortals.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender extends RenderType {

    public MRender(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static final OutputStateShard OUTLINE_TARGET = new OutputStateShard("set_outline", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebuffer framebuffer){
            if (framebuffer.si1_21_4$defaultFramebufferSets()!=null) {
                return framebuffer.si1_21_4$defaultFramebufferSets();
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
            RenderType.CompositeState.builder().setOutputState(OUTLINE_TARGET).createCompositeState(false)
    );

    public static final RenderType LIGHTNING = create(
            "lightning",
            1536,
            false,
            true,
            RenderPs.LIGHTNINGBloodRenderPipeline,
            RenderType.CompositeState.builder().setOutputState(WEATHER_TARGET).createCompositeState(false)
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
