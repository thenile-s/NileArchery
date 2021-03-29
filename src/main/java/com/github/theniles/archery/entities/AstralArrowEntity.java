package com.github.theniles.archery.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * This arrow summons astral comets on enemy hits!
 *
 * "Calamity..."
 * ~ Sun Tsu - The Art of Mod
 */
public class AstralArrowEntity extends CustomArrowEntity{
    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world      The world which the entity should be in. However, this constructor not add it to the world.
     */
    public AstralArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if(!world.isClient && !inGround){
            if(!(target instanceof EndermanEntity)){

                if(!world.isDirectionSolid(target.getBlockPos().add(0, 7, 0), this, Direction.DOWN));
                {
                    CometEntity comet = new CometEntity(Entities.COMET, world);

                    comet.updatePosition(target.getX(), target.getY() + 7, target.getZ());
                    comet.setOwner(getOwner());
                    comet.setVelocity(0, -1, 0);

                    world.spawnEntity(comet);
                }
            }
        }
    }
}
