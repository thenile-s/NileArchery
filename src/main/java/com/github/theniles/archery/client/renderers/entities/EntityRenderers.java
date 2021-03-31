package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.client.renderers.entities.factories.AmethystShardEntityRendererFactory;
import com.github.theniles.archery.client.renderers.entities.factories.CometEntityRendererFactory;
import com.github.theniles.archery.client.renderers.entities.factories.CustomArrowEntityRendererFactory;
import com.github.theniles.archery.entities.Entities;
import com.github.theniles.archery.entities.SeaArrowEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

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
        EntityRendererRegistry.INSTANCE.register(Entities.SEA_ARROW, new CustomArrowEntityRendererFactory(Textures.SEA_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.ENDER_ARROW, new CustomArrowEntityRendererFactory(Textures.ENDER_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.SPECTRAL_ARROW, new CustomArrowEntityRendererFactory(Textures.SPECTRAL_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.ASTRAL_ARROW, new CustomArrowEntityRendererFactory(Textures.ASTRAL_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.COMET, new CometEntityRendererFactory());
        EntityRendererRegistry.INSTANCE.register(Entities.AMETHYST_ARROW, new CustomArrowEntityRendererFactory(Textures.AMETHYST_ARROW));
        EntityRendererRegistry.INSTANCE.register(Entities.AMETHYST_SHARD, new AmethystShardEntityRendererFactory());
    }
}
