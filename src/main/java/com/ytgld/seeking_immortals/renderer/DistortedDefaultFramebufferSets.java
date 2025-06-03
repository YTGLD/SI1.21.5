package com.ytgld.seeking_immortals.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.ytgld.seeking_immortals.SeekingImmortalsMod;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DistortedDefaultFramebufferSets
        implements PostChain.TargetBundle  {

    public  ResourceHandle<RenderTarget> entityOutlineFramebuffer;
    public  ResourceHandle<RenderTarget> mainFramebuffer = ResourceHandle.invalid();
    public static final ResourceLocation MAIN =ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,"main");
    public static final ResourceLocation ENTITY_OUTLINE = ResourceLocation.fromNamespaceAndPath(SeekingImmortalsMod.MODID,
            "distorted");
    @Override
    public ResourceHandle<RenderTarget> getOrThrow(ResourceLocation id) {
        if (id .equals(ENTITY_OUTLINE) ) {
            return entityOutlineFramebuffer;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }


    @Override
    public void replace(ResourceLocation id, ResourceHandle<RenderTarget> framebuffer) {
        if (id.equals(ENTITY_OUTLINE) ) {
            entityOutlineFramebuffer = framebuffer;
        }else if (id.equals(MAIN)){
            mainFramebuffer = framebuffer;
        }else {
            System.out.println(id);
        }
    }

    @Nullable
    @Override
    public ResourceHandle<RenderTarget> get(ResourceLocation id) {
        if (id .equals(ENTITY_OUTLINE) ) {
            return entityOutlineFramebuffer;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }

}
