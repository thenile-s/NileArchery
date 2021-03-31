package com.github.theniles.archery.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

/**
 * Nether comet entity spawned by the astral arrows
 */
public class CometEntity extends CustomProjectileEntity {

    private float damage;

    public CometEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        //used in client rendering
        //clamping looks better on average imo
        //this.pitch = MathHelper.clamp(random.nextFloat(), 0.3F, 0.6F);
        //this.yaw = MathHelper.clamp(random.nextFloat(), 0.3F, 0.6F);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void initDataTracker() {

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
        super.tick();

        //these values are used in the renderer to rotate the comet nicely :p
        if (this.world.isClient) {
            //decided it looks better like this
            this.pitch += 0.35F;
            this.yaw += 0.35F;
            if(isSubmergedInWater()){
                MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.BUBBLE, getX(), getY(), getZ(), 0, 0, 0);
            } else{
                MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult hitResult) {
        explode(null);
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        explode(hitResult.getEntity());
    }

    protected void explode(Entity entity) {
        if (entity != null) {
            //bypass armor on a direct comet hit :)
            entity.damage(DamageSource.magic(this, getOwner() instanceof LivingEntity ? getOwner() : null), getDamage());
        }
        world.createExplosion(getOwner(), getX(), getY(), getZ(), getDamage(), Explosion.DestructionType.NONE);
        remove(RemovalReason.DISCARDED);
    }
}
