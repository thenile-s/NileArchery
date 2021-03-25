package com.github.theniles.archery.entities;

import com.github.theniles.archery.NileArchery;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

/**
 * A mod initializer used to register entity types.
 */
public class Entities implements ModInitializer {

    public static final EntityType<SeaArrowEntity> SEA_ARROW = FabricEntityTypeBuilder.<SeaArrowEntity>create(SpawnGroup.MISC, SeaArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("sea_arrow"), SEA_ARROW);
    }
}
