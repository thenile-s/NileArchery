package com.github.theniles.archery.client.renderers.entities.factories;

import com.github.theniles.archery.client.renderers.entities.CustomArrowEntityRenderer;
import com.github.theniles.archery.entities.CustomArrowEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class CustomArrowEntityRendererFactory implements EntityRendererFactory<CustomArrowEntity> {

    Identifier texture;

    public CustomArrowEntityRendererFactory(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public EntityRenderer<CustomArrowEntity> create(Context ctx) {
        return new CustomArrowEntityRenderer(ctx, texture);
    }
}
