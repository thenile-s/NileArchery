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

    public static final EntityType<EnderArrowEntity> ENDER_ARROW = FabricEntityTypeBuilder.<EnderArrowEntity>create(SpawnGroup.MISC, EnderArrowEntity::new).dimensions(EntityDimensions.fixed(.5F, .5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    public static final EntityType<SpectralArrowEntity> SPECTRAL_ARROW = FabricEntityTypeBuilder.<SpectralArrowEntity>create(SpawnGroup.MISC, SpectralArrowEntity::new).dimensions(EntityDimensions.fixed(.5F, .5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    public static final EntityType<AstralArrowEntity> ASTRAL_ARROW = FabricEntityTypeBuilder.<AstralArrowEntity>create(SpawnGroup.MISC, AstralArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    public static final EntityType<CometEntity> COMET = FabricEntityTypeBuilder.<CometEntity>create(SpawnGroup.MISC, CometEntity::new).dimensions(EntityDimensions.fixed(1.0f, 1.0F)).trackedUpdateRate(20).trackRangeBlocks(4).build();

    public static final EntityType<AmethystArrowEntity> AMETHYST_ARROW = FabricEntityTypeBuilder.<AmethystArrowEntity>create(SpawnGroup.MISC, AmethystArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    public static final EntityType<AmethystShardEntity> AMETHYST_SHARD = FabricEntityTypeBuilder.<AmethystShardEntity>create(SpawnGroup.MISC, AmethystShardEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(4).trackedUpdateRate(20).build();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("sea_arrow"), SEA_ARROW);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("ender_arrow"), ENDER_ARROW);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("spectral_arrow"), SPECTRAL_ARROW);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("astral_arrow"), ASTRAL_ARROW);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("comet"), COMET);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("amethyst_arrow"), AMETHYST_ARROW);
        Registry.register(Registry.ENTITY_TYPE, NileArchery.newId("amethyst_shard"), AMETHYST_SHARD);
    }
}
