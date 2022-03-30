package com.github.theniles.archery.client.models.entities;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.entities.CometEntity;
import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class CometEntityModel extends EntityModel<CometEntity> {
    
    public static final EntityModelLayer COMET_MODEL_LAYER = new EntityModelLayer(NileArchery.newId("comet"), "main");

    private final ModelPart base;

    public CometEntityModel(ModelPart modelPart) {
        //textureWidth = 64;
        //textureHeight = 64;
        //bb_main.setPivot(0.0F, 0.0F, 0.0F);
        //bb_main.setTextureOffset(0, 24).addCuboid(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        //bb_main.setTextureOffset(0, 0).addCuboid(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false)

        //wtf has 1.18.2 done
        //probably better than the shit b4 tho
        //fuck mojang but fuck microsoft slightly less
        base = modelPart.getChild(EntityModelPartNames.CUBE);
    }

    @Override
    public void setAngles(CometEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //base.render(matrixStack, buffer, packedLight, packedOverlay);
        ImmutableList.of(this.base).forEach((modelPart) -> {
            modelPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        });
    }

    public void setRotationAngle(float pitch, float yaw, float roll) {
        base.pitch = pitch;
        base.yaw = yaw;
        base.roll = roll;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
    	ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 0F, -6F, 12F, 12F, 12F), ModelTransform.pivot(0F, 2F, 0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}