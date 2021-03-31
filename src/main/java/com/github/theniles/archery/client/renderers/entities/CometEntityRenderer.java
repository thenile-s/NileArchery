package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.client.models.entities.CometEntityModel;
import com.github.theniles.archery.entities.CometEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CometEntityRenderer extends EntityRenderer<CometEntity> {
    protected CometEntityModel model;

    public CometEntityRenderer(EntityRendererFactory.Context dispatcher) {
        super(dispatcher);

        model = new CometEntityModel();
    }

    @Override
    public void render(CometEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        //matrices.multiply(new Quaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw), MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch), 0, true));
        model.setYaw(MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw));
        model.setPitch(MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch));
        //for some reason we need to translate by half a block t o align it properly
        matrices.translate(0, 0.5, 0);
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
