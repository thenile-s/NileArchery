package com.github.theniles.archery.entities;

import com.github.theniles.archery.network.EntitySpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CometEntity extends Entity {

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private float damage;

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    Entity owner;

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    private int maxAge;

    public CometEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        setRotationSpeed(10);
        setMaxAge(100);
        setDamage(4);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
    setMaxAge(tag.getInt("MaxAge"));
    setDamage(tag.getFloat("Damage"));
    setOwner(world.getEntityById(tag.getInt("OwnerID")));
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putInt("MaxAge", getMaxAge());
        tag.putFloat("Damage", getDamage());
        tag.putInt("OwnerID", getOwner().getEntityId());//TODO gmm asd
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.newPacket(this);
    }

    private boolean entityCollisionPredicate(Entity entity){
        //same entity check is covered in another method
        return entity instanceof LivingEntity && entity != getOwner() && entity.collidesWith(this);
    }

    @Override
    public void tick() {
        if (this.world.isClient) {
            this.yaw += getRotationSpeed();
            this.yaw = yaw % 360;//lol idk it looks ok but maybe u should change it
        } else {
            if (age >= getMaxAge()) {
                explode(null);
                remove();
            }

            HitResult hitResult = ProjectileUtil.getCollision(this, this::entityCollisionPredicate);
            handleHitResult(hitResult);
        }

        Vec3d newPos = getVelocity().add(getPos());

        updatePosition(newPos.x, newPos.y, newPos.z);

        super.tick();
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    private float rotationSpeed;

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    protected void handleHitResult(HitResult hitResult){
        if(hitResult.getType() == HitResult.Type.BLOCK){
            onBlockHit((BlockHitResult)hitResult);

        } else if(hitResult.getType() == HitResult.Type.ENTITY){
            onEntityHit((EntityHitResult)hitResult);
        }
    }

    protected void onBlockHit(BlockHitResult hitResult) {
        explode(null);
    }

    protected void onEntityHit(EntityHitResult hitResult) {
        explode(hitResult.getEntity());
    }

    protected void explode(Entity entity){
        if(entity instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity)entity;
            entity.damage(DamageSource.mob(getOwner() instanceof LivingEntity ? (LivingEntity)(getOwner()) : null ), getDamage());
        }

        world.createExplosion(getOwner(), getX(), getY(), getZ(), getDamage(), Explosion.DestructionType.NONE);
        remove();
    }
}
