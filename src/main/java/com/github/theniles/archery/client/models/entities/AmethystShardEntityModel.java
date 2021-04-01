package com.github.theniles.archery.client.models.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AmethystShardEntityModel extends EntityModel<Entity> {
    private ModelPart bb_main;

    public AmethystShardEntityModel() {
        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, -1, 0, -1, 2, 2, 2, 0, 0, 0, false, 8, 4);
        List<ModelPart.Cuboid> cubes = new ArrayList<ModelPart.Cuboid>(1);
        cubes.add(cuboid);
        bb_main = new ModelPart(cubes ,new HashMap<>());
        bb_main.setPivot(0.0F, 1.0F, 0.0F);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

}