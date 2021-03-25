package com.github.theniles.archery.items.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This arrow acts like a normal arrow, but is not affected by water drag like vanilla arrows.
 */
public class SeaArrow extends ArrowItem {
    public SeaArrow(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        ArrowEntity arrowEntity = new ArrowEntity(world, shooter);
        arrowEntity.initFromStack(stack);
        return arrowEntity;
    }
}
