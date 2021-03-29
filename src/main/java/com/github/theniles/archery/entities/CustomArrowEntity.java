package com.github.theniles.archery.entities;

import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import com.github.theniles.archery.mixin.ArrowEntityMixin;

import com.github.theniles.archery.network.EntitySpawnPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Iterator;

/**
 * Base class for all new arrows.
 *
 * Contains common code they all need, such as spawning and tipped arrow handling.
 *
 * "When the heart is toast, the mind has no wisdom to boast."
 * ~ Sun Tsu - The Art of Mod
 */
public class CustomArrowEntity extends ArrowEntity {

    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world The world which the entity should be in. However, this constructor not add it to the world.
     */
    public CustomArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    protected ArrowItem pickupItem;

    public ArrowItem getPickupItem() {
        return pickupItem;
    }

    public void setPickupItem(ArrowItem pickupItem) {
        this.pickupItem = pickupItem;
    }

    @Override
    protected ItemStack asItemStack() {
        //We can do this because an ItemStack with a null Item will be set to Items.AIR :)
        ItemStack itemStack = new ItemStack(getPickupItem());

        //Set potion effects only if there is an actual item
        //If there is an actual item, only save potion effects if we made the item to do it so
        //Or if it we did not make the item in teh first place
        if(itemStack.getItem() != null &&
                (!(itemStack.getItem() instanceof  CustomArrowItem) ||
                ((CustomArrowItem)itemStack.getItem()).getPersistsStatusEffects())){
            ArrowEntityMixin accessor = ((ArrowEntityMixin)this);
            PotionUtil.setPotion(itemStack, accessor.getPotion());
            PotionUtil.setCustomPotionEffects(itemStack, accessor.getEffects());
            if(accessor.getColorSet()){
                itemStack.getOrCreateTag().putInt("CustomPotionColor", getColor());
            }
        }
        return itemStack;
    }

    /**
     * This method seems to be used for NBT initialisation, such as potion effects.
     *
     * The same vanilla effect has been replicated here to allow for custom tipped arrows.
     * @param stack The item stack containing the NBT data with which to initialize the arrow
     */
    @Override
    public void initFromStack(ItemStack stack) {

        ArrowEntityMixin accessor = (ArrowEntityMixin)this;

        boolean hasPotionData =
                stack.hasTag() && (
                        stack.getTag().contains("Potion") ||
                        stack.getTag().contains("CustomPotionEffects")  ||
                        stack.getTag().contains("CustomPotionColor"));

        if (hasPotionData) {
            accessor.setPotion(PotionUtil.getPotion(stack));
            Collection<StatusEffectInstance> collection = PotionUtil.getCustomPotionEffects(stack);
            if (!collection.isEmpty()) {
                Iterator var3 = collection.iterator();

                while(var3.hasNext()) {
                    StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                    accessor.getEffects().add(new StatusEffectInstance(statusEffectInstance));
                }
            }

            int i = getCustomPotionColor(stack);
            if (i == -1) {
                    accessor.invokeInitColor();
            } else {
                accessor.invokeSetColor(i);
            }
        } else {
            accessor.setPotion(Potions.EMPTY);
            accessor.getEffects().clear();
            this.dataTracker.set(accessor.getCOLOR(), -1);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.newPacket(this);
    }
}
