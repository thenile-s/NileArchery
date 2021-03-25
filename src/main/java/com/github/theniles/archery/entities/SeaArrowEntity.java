package com.github.theniles.archery.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class SeaArrowEntity extends ArrowEntity {
    public SeaArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }
}
