package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.client.models.entities.CometEntityModel;
import com.github.theniles.archery.entities.CometEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class CometEntityRenderer extends EntityRenderer<CometEntity> {
    protected CometEntityModel model;

    protected CometEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);

        model = new CometEntityModel();
    }

    @Override
    public void render(CometEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        //TODO maybe better comet rendering?
        matrices.push();
        matrices.multiply(new Quaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw), MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch), 0, true));
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity))), light, OverlayTexture.getUv(0, false), 1, 1, 1, 1);
        matrices.pop();
        //label (name tag)
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(CometEntity entity) {
        return Textures.COMET;
    }
}
