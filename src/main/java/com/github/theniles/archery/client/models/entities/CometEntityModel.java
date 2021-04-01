package com.github.theniles.archery.client.models.entities;

import com.github.theniles.archery.entities.CometEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CometEntityModel extends EntityModel<CometEntity> {
    private final ModelPart bb_main;

    public CometEntityModel() {
        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, -6, -6, -6, 12, 12, 12, 0, 0, 0, false, 64, 64);
        List<ModelPart.Cuboid> cubes = new ArrayList<ModelPart.Cuboid>(1);
        cubes.add(cuboid);
        bb_main = new ModelPart(cubes, new HashMap<>());
        bb_main.setPivot(0.0F, 0.0F, 0.0F);
        //bb_main.setTextureOffset(0, 24).addCuboid(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
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