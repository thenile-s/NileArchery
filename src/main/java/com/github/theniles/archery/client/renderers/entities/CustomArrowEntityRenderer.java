package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.entities.CustomArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.util.Identifier;

/**
 * Lets us easily render different arrow entities with different textures
 * without making a new class for each one.
 */
@Environment(EnvType.CLIENT)
public class CustomArrowEntityRenderer extends ProjectileEntityRenderer<CustomArrowEntity> {

    private final Identifier texture;

    public CustomArrowEntityRenderer(Context context, Identifier texture) {
        super(context);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture(CustomArrowEntity entity) {
        return texture;
    }
}
