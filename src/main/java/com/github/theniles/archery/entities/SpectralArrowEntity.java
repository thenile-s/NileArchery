package com.github.theniles.archery.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

/**
 * Rather than mixing in to vanilla, we simply add a new custom arrow to act as a spectral arrow.
 * This is preferred to mixin hell. This is unnoticeable to players as it will be obtained in the
 * same way as other tipped arrows; a crafting recipe.
 */
public class SpectralArrowEntity extends CustomArrowEntity{
    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world      The world which the entity should be in. However, this constructor not add it to the world.
     */
    public SpectralArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(world.isClient && !inGround){
            this.world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
