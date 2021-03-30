package com.github.theniles.archery.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AmethystArrowEntity extends CustomArrowEntity{
    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world      The world which the entity should be in. However, this constructor not add it to the world.
     */
    public AmethystArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }
}
