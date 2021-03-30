package com.github.theniles.archery.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
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
        this.pitch = random.nextFloat();
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
    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setDamage(tag.getFloat("Damage"));
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putFloat("Damage", getDamage());
    }

    @Override
    public void tick() {
        //these values are used in the renderer to rotate the comet nicely :p
        if (this.world.isClient) {
            //decided it looks better if the pitch is constant and only the yaw changes
            this.yaw += 0.6F;
            if(isSubmergedInWater()){
                MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.BUBBLE, getX(), getY(), getZ(), 0, 0, 0);
            }
        }

        super.tick();
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
        remove();
    }
}
