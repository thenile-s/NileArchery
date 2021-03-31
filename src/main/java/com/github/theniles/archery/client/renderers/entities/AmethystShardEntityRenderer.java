package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.client.models.entities.AmethystShardEntityModel;
import com.github.theniles.archery.entities.AmethystShardEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AmethystShardEntityRenderer extends EntityRenderer<AmethystShardEntity> {
    private AmethystShardEntityModel model;

    public AmethystShardEntityRenderer(EntityRendererFactory.Context dispatcher) {
        super(dispatcher);
        model = new AmethystShardEntityModel();
    }

    @Override
    public void render(AmethystShardEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity))), light, OverlayTexture.getUv(0, false), 1, 1, 1, 1);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(AmethystShardEntity entity) {
        return Textures.AMETHYST_SHARD;
    }
}
