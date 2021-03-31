package com.github.theniles.archery.client.renderers.entities.factories;

import com.github.theniles.archery.client.renderers.entities.CometEntityRenderer;
import com.github.theniles.archery.entities.CometEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class CometEntityRendererFactory implements EntityRendererFactory<CometEntity> {
    @Override
    public EntityRenderer<CometEntity> create(Context ctx) {
        return new CometEntityRenderer(ctx);
    }
}
