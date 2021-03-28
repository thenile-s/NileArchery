package com.github.theniles.archery.entities;

import com.github.theniles.archery.mixin.PersistentProjectileEntityMixin;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * This arrow can hit endermen
 *
 * (Don't tell anyone but it does true damage to ender men :D)
 *
 * TODO some additional effect related to homing properties
 */
public class EnderArrowEntity extends CustomArrowEntity {
    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world      The world which the entity should be in. However, this constructor not add it to the world.
     */
    public EnderArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        //TODO make coo larrow thing
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {

        //hell copied from the super implementation
        //changed it to magic damage for endermen
        //easiest fix tbh
        //fuck mojang fuck vanilla fuck microsoft delete planet earth save no backups

        PersistentProjectileEntityMixin ppAccessor = (PersistentProjectileEntityMixin)this;
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.getDamage(), 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (ppAccessor.getPiercedEntities() == null) {
                ppAccessor.setPiercedEntities(new IntOpenHashSet(5));
            }

            if (ppAccessor.getPiercedKilledEntities() == null) {
                ppAccessor.setPiercedKilledEntities(Lists.newArrayListWithCapacity(5));
            }

            if (ppAccessor.getPiercedEntities().size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            ppAccessor.getPiercedEntities().add(entity.getEntityId());
        }

        if (this.isCritical()) {
            long l = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + (long)i, 2147483647L);
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) {
            damageSource2 = DamageSource.arrow(this, this);
        } else {
            damageSource2 = DamageSource.arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire()) {
            entity.setOnFireFor(5);
        }

        if(bl){
            //magic is also apparently a projectile damage source LMFAO
            //vanilal doesnt have our null check but whatevs idk now
            damageSource2 = DamageSource.mob(entity2 instanceof LivingEntity ?(LivingEntity)entity2 : null);
        }

        if (entity.damage(damageSource2, (float)i)) {
            //Don't think i didn't see u little rodent
            //For some reason u make arrows bounce off of endermen after hitting them haha
            //if (bl) {
            //    return;
            //}

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }

                if (ppAccessor.getPunch() > 0) {
                    Vec3d vec3d = this.getVelocity().multiply(1.0D, 0.0D, 1.0D).normalize().multiply((double)ppAccessor.getPunch() * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingEntity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }

                if (!this.world.isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
                }

                this.onHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && ppAccessor.getPiercedKilledEntities() != null) {
                    ppAccessor.getPiercedKilledEntities().add(livingEntity);
                }

                if (!this.world.isClient && entity2 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                    if (ppAccessor.getPiercedKilledEntities() != null && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, ppAccessor.getPiercedKilledEntities());
                    } else if (!entity.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1D));
            this.yaw += 180.0F;
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.remove();
            }
        }
    }
}
