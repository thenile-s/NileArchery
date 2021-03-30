package com.github.theniles.archery.entities;

import com.github.theniles.archery.NileArchery;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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

    public static final int MAX_COMET_OFFSET = 5;

    public static final int COMET_Y_OFFSET = 12;

    protected Vec3d getCometPosition(Vec3d destination){
        return new Vec3d(
                //we can also offset negatively on the axis
                random.nextDouble() * 2 * MAX_COMET_OFFSET + destination.getX() - MAX_COMET_OFFSET,
                COMET_Y_OFFSET + destination.getY(),
                random.nextDouble() * 2 * MAX_COMET_OFFSET + destination.getZ() - MAX_COMET_OFFSET);
    }

    protected Vec3d getCometVelocity(Vec3d pos, Vec3d destination, double speed){
        return pos.reverseSubtract(destination).normalize().multiply(speed);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if(!world.isClient && !inGround){
            if(!(target instanceof EndermanEntity)){

                boolean shouldSpawnComet = true;

                BlockPos pos = new BlockPos(target.getX() - MAX_COMET_OFFSET, target.getEyeY() + COMET_Y_OFFSET ,target.getZ() - MAX_COMET_OFFSET);

                for (int i = 0; i < 2 * MAX_COMET_OFFSET; i++) {
                    for (int j = 0; j < 2 * MAX_COMET_OFFSET; j++) {
                        if(world.getBlockState(pos).isFullCube(world, pos)){
                            shouldSpawnComet = false;
                            break;
                        }
                        pos.add(0,0,1);
                    }
                    pos.add(1,0,-2 * MAX_COMET_OFFSET);
                }

                if(shouldSpawnComet)
                {
                    CometEntity comet = new CometEntity(Entities.COMET, world);

                    Vec3d position = getCometPosition(target.getPos().add(0, target.getStandingEyeHeight(), 0));
                    Vec3d velocity = getCometVelocity(position, target.getPos(), 1);

                    comet.setOwner(getOwner());
                    //critical hits would be too op :( have to nerf it
                    //"nerf"
                    comet.setDamage((float) getDamage() * (isCritical() ? 1.5F : 1.0F));

                    comet.updatePosition(position.x, position.y, position.z);
                    comet.setVelocity(velocity.x, velocity.y, velocity.z);

                    world.spawnEntity(comet);
                }
            }
        }
    }
}
