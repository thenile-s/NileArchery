package com.github.theniles.archery.items.projectiles;

import com.github.theniles.archery.entities.CustomArrowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

/**
 * Base class for all new arrow items.
 *
 * This item type allows us easily to create configurable arrows.
 *
 * Vanilla (eww) has a new Item class for each arrow type! (tipped, normal, spectral)
 */
public class CustomArrowItem extends ArrowItem {

    private EntityType<? extends CustomArrowEntity> arrowEntityType;

    public EntityType<? extends CustomArrowEntity> getArrowEntityType() {
        return arrowEntityType;
    }

    public void setArrowEntityType(EntityType<? extends CustomArrowEntity> arrowEntityType) {
        this.arrowEntityType = Validate.notNull(arrowEntityType);
    }

    private ArrowItem pickupItem;

    public ArrowItem getPickupItem(){
        return pickupItem;
    }

    public void setPickupItem(ArrowItem item){
        pickupItem = item;
    }

    private boolean persistsStatusEffects;

    public boolean getPersistsStatusEffects() {
        return persistsStatusEffects;
    }

    public void setPersistsStatusEffects(boolean persistsStatusEffects) {
        this.persistsStatusEffects = persistsStatusEffects;
    }

    public CustomArrowItem(Settings settings, EntityType<? extends CustomArrowEntity> arrowType) {
        super(settings);
        setArrowEntityType(arrowType);
        pickupItem = this;
    }

    @SuppressWarnings("unused")
    public CustomArrowItem(Settings settings, EntityType<? extends CustomArrowEntity> arrowType, ArrowItem pickupItem) {
        super(settings);
        setArrowEntityType(arrowType);
        setPickupItem(pickupItem);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        CustomArrowEntity arrowEntity = getArrowEntityType().create(world);

        arrowEntity.setOwner(shooter);

        arrowEntity.setPickupItem(getPickupItem());

        if(shooter instanceof PlayerEntity){
            arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        }

        //The strange y subtraction value is taken from a PersistentProjectileEntity constructor
        arrowEntity.updatePosition(shooter.getX(), shooter.getEyeY() - 0.10000000149011612D, shooter.getZ());

        return  arrowEntity;
    }
}
