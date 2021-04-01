package com.github.theniles.archery.entities;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AmethystArrowEntity extends CustomArrowEntity{
    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world      The world which the entity should be in. However, this constructor not add it to the world.
     */
    public AmethystArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        spawnAmethystShardsAndRemove();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        spawnAmethystShardsAndRemove();
    }

    private void spawnAmethystShardsAndRemove(){
        Vec3d frontDir = getVelocity().normalize().negate();
        float shardSpeed = (float) getVelocity().lengthSquared() / 30F;

        spawnAmethystShards(getPos(), frontDir, shardSpeed, (float) getDamage());

        world.playSound(getX(), getY(), getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.AMBIENT, 75F, 1F, true);

        remove(RemovalReason.DISCARDED);
    }

    private void spawnAmethystShards(Vec3d center, Vec3d frontDir, float shardSpeed, float shardDamage){

        //spawn to a side
        AmethystShardEntity shard = new AmethystShardEntity(Entities.AMETHYST_SHARD, world);
        if(isCritical()){
            shard.setDamage((float)(getDamage() * 1.5F));
        } else{
            shard.setDamage((float) (shardDamage * MathHelper.clamp(random.nextFloat(), 0.4F, 0.6F)));
        }
        Vec3d spawnDir = frontDir.rotateY(90); //between 10 and 80 degs
        shard.updatePosition(center.x, center.y, center.z);
        shard.setVelocity(spawnDir.multiply(shardSpeed));
        shard.setOwner(getOwner());
        world.spawnEntity(shard);


        //spawn to the other side
        shard = new AmethystShardEntity(Entities.AMETHYST_SHARD, world);
        if(isCritical()){
            shard.setDamage((float)(getDamage() * 1.5F));
        } else{
            shard.setDamage((float) (shardDamage * MathHelper.clamp(random.nextFloat(), 0.4F, 0.6F)));
        }
        spawnDir = frontDir.rotateY(-90); //between 10 and 80 degs
        shard.updatePosition(center.x, center.y, center.z);
        shard.setVelocity(spawnDir.multiply(shardSpeed));
        shard.setOwner(getOwner());
        world.spawnEntity(shard);

        //chance to spawn another shard
        if(random.nextFloat() > 0.25F){
            shard = new AmethystShardEntity(Entities.AMETHYST_SHARD, world);
            if(isCritical()){
                shard.setDamage((float)(getDamage() * 1.5F));
            } else{
                shard.setDamage((float) (shardDamage * MathHelper.clamp(random.nextFloat(), 0.4F, 0.6F)));
            }
            spawnDir = frontDir; //between 30 and -30 degs
            shard.updatePosition(center.x, center.y, center.z);
            shard.setVelocity(spawnDir.multiply(shardSpeed));
            shard.setOwner(getOwner());
            world.spawnEntity(shard);
        }
    }
}
