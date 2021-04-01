package com.github.theniles.archery.client.renderers.entities.factories;

import com.github.theniles.archery.client.renderers.entities.AmethystShardEntityRenderer;
import com.github.theniles.archery.entities.AmethystShardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class AmethystShardEntityRendererFactory implements EntityRendererFactory<AmethystShardEntity> {
    @Override
    public EntityRenderer<AmethystShardEntity> create(Context ctx) {
        return new AmethystShardEntityRenderer(ctx);
    }
}
