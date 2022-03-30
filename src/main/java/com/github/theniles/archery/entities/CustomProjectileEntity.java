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
import net.minecraft.world.World;

/**
 * Custom projectile entity base class. These should be used when
 * arrow behaviour such as pickup and potion effects is not desirable.
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
        //vanilla projectile things tick collision on the client too :/
        HitResult hitResult = ProjectileUtil.getCollision(this, this::entityCollisionPredicate);
        handleHitResult(hitResult);

        Vec3d newPos = getVelocity().add(getPos());

        updatePosition(newPos.x, newPos.y, newPos.z);

        super.tick();
    }

    protected boolean entityCollisionPredicate(Entity entity) {
        //same entity check is covered in another method
        return entity instanceof LivingEntity &&
                entity != getOwner() &&
                !entity.isTeammate(this) &&
                entity.isAlive(); //a dying entity could block a comet D:< so i fix this here
    }

    private void handleHitResult(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            onBlockHit((BlockHitResult) hitResult);

        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
            onEntityHit((EntityHitResult) hitResult);
        }
    }

    protected abstract void onEntityHit(EntityHitResult hitResult);

    protected abstract void onBlockHit(BlockHitResult hitResult);
}
