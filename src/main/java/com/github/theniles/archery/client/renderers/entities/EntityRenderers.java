package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.entities.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;

/**
 * This client-side mod initializer is used to register entity renderers.
 *
 * Renderers must be registered for every entity type, or else the game will crash.
 */

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class EntityRenderers implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Entities.SEA_ARROW, (dispatcher, context) -> new CustomArrowEntityRenderer(dispatcher, Textures.SEA_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.ENDER_ARROW, (dispatcher, context) -> new CustomArrowEntityRenderer(dispatcher, Textures.ENDER_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.SPECTRAL_ARROW, (dispatcher, context) -> new CustomArrowEntityRenderer(dispatcher, Textures.SPECTRAL_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.ASTRAL_ARROW, (dispatcher, context) -> new CustomArrowEntityRenderer(dispatcher, Textures.ASTRAL_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.COMET, (dispatcher, context) -> new CometEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.AMETHYST_ARROW, (dispatcher, context) -> new CustomArrowEntityRenderer(dispatcher, Textures.AMETHYST_ARROW));
    }
}
