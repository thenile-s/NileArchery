package com.github.theniles.archery.entities;

import com.github.theniles.archery.network.EntitySpawnPacket;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.ReusableStream;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Stream;

public class AmethystShardEntity extends Entity{
    public final static int MAX_GROUND_TICKS = 200;

    private int inGroundTicks;

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    private Entity owner;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private float damage;

    public AmethystShardEntity(EntityType<?> type, World world) {
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
        setDamage(tag.getFloat("Damage"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        if(getOwner() != null && world instanceof ServerWorld){
            tag.putUuid("OwnerUUID", getOwner().getUuid());
        }
        tag.putFloat("Damage", getDamage());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.newPacket(this);
    }

    protected boolean entityCollisionPredicate(Entity entity) {
        //same entity check is covered in another method
        return entity instanceof LivingEntity &&
                entity != getOwner() &&
                !entity.isTeammate(this) &&
                entity.isAlive(); //a dying entity could block a comet D:< so i fix this here
    }

    @Override
    public void tick() {
        //TODO amethyst shard tick
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

        List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().stretch(nextPos.subtract(curPos)), this::entityCollisionPredicate);

        Entity hitEntity = null;
        double distanceFromEntity = Double.MAX_VALUE;

        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).squaredDistanceTo(this) <= distanceFromEntity){
                hitEntity = entities.get(i);
            }
        }

        if(hitEntity != null){
                LivingEntity livingEntity = (LivingEntity)hitEntity;
                //only remove this shard if it damaged something
                //don't let entities soak up multiple shards
                //with their immunity frames :D
                if(livingEntity.damage(DamageSource.mob(getOwner() instanceof LivingEntity ? (LivingEntity)getOwner() : null), getDamage())){
                    world.playSound(getX(), getY(), getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, 100F, 1F, true);
                    remove(RemovalReason.DISCARDED);
                }
        } else if(hitBlock){
            if(inGroundTicks++ == 0){
                float hardness = world.getBlockState(blockHitResult.getBlockPos()).getHardness(world, blockHitResult.getBlockPos());
                float volume;
                if(hardness > 2){
                    //bigger than stone
                    remove(RemovalReason.DISCARDED);
                    volume = 100;
                } else {
                    volume = 50;
                }
                world.playSound(getX(), getY(), getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, volume, 1F, true);
            } else if(++inGroundTicks > 200){
                remove(RemovalReason.DISCARDED);
            }
        } else{
            inGroundTicks = 0;
            if(getVelocity().y > -0.5F){
                setVelocity(getVelocity().add(0, -0.025F, 0));
            }
        }

        updatePosition(nextPos.x, nextPos.y, nextPos.z);
    }
}
