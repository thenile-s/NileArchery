package com.github.theniles.archery.client.models.items;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.items.BowItem;
import com.github.theniles.archery.items.Items;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * A client-side mod initializer used to register item model predicates.
 *
 * This is used so that the model of bows changes as you use them, giving the visual effect
 * of pulling the bow.
 */

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class ModelPredicates implements ClientModInitializer {

    private static float isBowPulling(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity){
        return  entity != null && entity.isUsingItem() && entity.getActiveItem() == itemStack ? 1 : 0;
    }

    private static float getBowPull(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity){
        if (entity == null) {
            return 0.0F;
        } else {
            return entity.getActiveItem() != itemStack ?
                    0.0F :
                    ((BowItem)itemStack.getItem()).getPullProgress(entity.getItemUseTime());
        }
    }

    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register
                (Items.GOLD_BOW, NileArchery.newId("pulling"), ModelPredicates::isBowPulling);

        FabricModelPredicateProviderRegistry.register
                (Items.GOLD_BOW, NileArchery.newId("pull"), ModelPredicates::getBowPull);
    }
}