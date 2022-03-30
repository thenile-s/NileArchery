package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.client.models.entities.CometEntityModel;
import com.github.theniles.archery.entities.CometEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CometEntityRenderer extends EntityRenderer<CometEntity> {

    protected CometEntityModel model;

    protected CometEntityRenderer(Context context) {
        super(context);

        model = new CometEntityModel(context.getPart(CometEntityModel.COMET_MODEL_LAYER));
    }

    @Override
    public void render(CometEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        //matrices.multiply(new Quaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw), MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch), 0, true));
        //model.setYaw(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()));
        //model.setPitch(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()));
        model.setRotationAngle(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()), MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()), entity.getRoll());
        //for some reason we need to translate by half a block t o align it properly
        //1.18.2 we dont anymroe :/
        //matrices.translate(0, -0.5, 0);
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
