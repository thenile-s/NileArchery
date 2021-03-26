package com.github.theniles.archery.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

/**
 * A new type of arrow.
 *
 * It moves normally in water.
 */
public class SeaArrowEntity extends CustomArrowEntity {
    public SeaArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getDragInWater() {
        return 1;
    }
}
