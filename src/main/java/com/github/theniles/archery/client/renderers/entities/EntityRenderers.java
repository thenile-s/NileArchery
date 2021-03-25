package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.entities.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;

/**
 * This client-side mod initializer is used to register entity renderers.
 *
 * Renderers must be registered for every entity type, or else the game will crash.
 */
public class EntityRenderers implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(Entities.SEA_ARROW, (dispatcher, context) -> new ArrowEntityRenderer(dispatcher));

    }
}
