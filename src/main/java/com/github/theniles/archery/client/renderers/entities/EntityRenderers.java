package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.client.models.entities.CometEntityModel;
import com.github.theniles.archery.entities.Entities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

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
        //factory can be done as lambda here
        EntityRendererRegistry.register(Entities.SEA_ARROW, (context) -> new CustomArrowEntityRenderer(context, Textures.SEA_ARROW));
        EntityRendererRegistry.register(Entities.ENDER_ARROW, (context) -> new CustomArrowEntityRenderer(context, Textures.ENDER_ARROW));
        EntityRendererRegistry.register(Entities.SPECTRAL_ARROW, (context) -> new CustomArrowEntityRenderer(context, Textures.SPECTRAL_ARROW));
        EntityRendererRegistry.register(Entities.ASTRAL_ARROW, (context) -> new CustomArrowEntityRenderer(context, Textures.ASTRAL_ARROW));
        EntityRendererRegistry.register(Entities.COMET, (context) -> new CometEntityRenderer(context));
        EntityRendererRegistry.register(Entities.AMETHYST_ARROW, (context) -> new CustomArrowEntityRenderer(context, Textures.AMETHYST_ARROW));
    
        //since 1.18.2
        EntityModelLayerRegistry.registerModelLayer(CometEntityModel.COMET_MODEL_LAYER, CometEntityModel::getTexturedModelData);
    }
}
