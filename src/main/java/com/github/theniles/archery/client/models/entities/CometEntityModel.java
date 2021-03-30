package com.github.theniles.archery.client.models.entities;

import com.github.theniles.archery.entities.CometEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CometEntityModel extends EntityModel<CometEntity> {
    private final ModelPart bb_main;

    public CometEntityModel() {
        textureWidth = 64;
        textureHeight = 64;
        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 0.0F, 0.0F);
        //bb_main.setTextureOffset(0, 24).addCuboid(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);
    }

    public void setYaw(float yaw){
        bb_main.yaw = yaw;
    }

    public void setPitch(float pitch){
        bb_main.pitch = pitch;
    }

    @Override
    public void setAngles(CometEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

}