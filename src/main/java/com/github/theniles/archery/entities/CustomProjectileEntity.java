package com.github.theniles.archery.entities;

import com.github.theniles.archery.network.EntitySpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

/**
 * Custom projectile entity base class. These should be used when
 * arrow behaviour such as pickup and potion effects is not desirable.
 *
 * Current tick behaviour:
 * Its next position is determined by a block ray cast with its position and velocity.
 * Checks for entity collision in between its current position and its next position.
 * Will handle the first entity collision, the block collision or nothing, exclusively, in that order.
 * Updates its position by adding its velocity.
 *
 * Make another class if checking collision with multiple entities is required for a projectile.
 */
public abstract class CustomProjectileEntity extends Entity {
    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    private Entity owner;

    public CustomProjectileEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
            if(world instanceof ServerWorld){
                if(tag.containsUuid("OwnerUUID")){
                    setOwner(((ServerWorld)world).getEntity(tag.getUuid("OwnerUUID")));
                }
            }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        if(getOwner() != null && world instanceof ServerWorld){
            tag.putUuid("OwnerUUID", getOwner().getUuid());
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.newPacket(this);
    }

    @Override
    public void tick() {
        //after some consideration, the super tick should be here
        super.tick();

        Vec3d curPos = getPos();

        Vec3d nextPos = curPos.add(getVelocity());

        BlockHitResult blockHitResult = world.raycast(new RaycastContext(curPos, nextPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

        boolean hitBlock = blockHitResult.getType() == HitResult.Type.BLOCK;

        if(hitBlock){
            //this prevents our projectiles from ghosting through walls
            //probably won't happen anyway if the velocity is small enough
            //but we do it anyway, just in case. After all, trap doors exist :p
            nextPos = blockHitResult.getPos();

        }

        EntityHitResult entityHitResult = getEntityCollision(curPos, nextPos);

        if(entityHitResult != null){
            onEntityHit(entityHitResult);
        } else if (hitBlock){
            onBlockHit(blockHitResult);
        }

        nextPos = getPos().add(getVelocity());

        updatePosition(nextPos.x, nextPos.y, nextPos.z);
    }

    protected boolean entityCollisionPredicate(Entity entity) {
        //same entity check is covered in another method
        return entity instanceof LivingEntity &&
                entity != getOwner() &&
                !entity.isTeammate(this) &&
                entity.isAlive(); //a dying entity could block a comet D:< so i fix this here
    }

    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition){
        return ProjectileUtil.getEntityCollision(this.world, this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), this::entityCollisionPredicate);
    }

    protected abstract void onEntityHit(EntityHitResult hitResult);

    protected abstract void onBlockHit(BlockHitResult hitResult);
}
