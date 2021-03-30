package com.github.theniles.archery.items.projectiles;

import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class CustomArrowDispenserBehaviour extends ProjectileDispenserBehavior {
    public CustomArrowItem getArrowItem() {
        return arrowItem;
    }

    public void setArrowItem(CustomArrowItem arrowItem) {
        Validate.notNull(arrowItem);
        this.arrowItem = arrowItem;
    }

    private CustomArrowItem arrowItem;

    public CustomArrowDispenserBehaviour(CustomArrowItem arrowItem) {
        setArrowItem(arrowItem);
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
        return arrowItem.createDispensedEntity(world, stack, position);
    }
}
