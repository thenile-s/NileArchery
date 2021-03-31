package com.github.theniles.archery.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class AmethystShardEntity extends CustomProjectileEntity{

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
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setDamage(tag.getFloat("Damage"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putFloat("Damage", getDamage());
    }

    @Override
    public void tick() {
        //TODO amethyst shard tick

        //slow them down and affect them by gravity
        //after some experimenting, these are nice values

        if(getVelocity().y > -0.5F){
            setVelocity(getVelocity().add(0, -0.025F, 0));
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if(entity instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity)entity;
            //only remove this shard if it damaged something
            //don't let entities soak up multiple shards
            //with their immunity frames :D
            if(livingEntity.damage(DamageSource.mob(getOwner() instanceof LivingEntity ? (LivingEntity)getOwner() : null), getDamage())){
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult hitResult) {
        //TODO todo
    }
}
