package com.github.theniles.archery.client.models.items;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.items.weapons.CustomBowItem;
import com.github.theniles.archery.items.Items;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

/**
 * A client-side mod initializer used to register item model predicates.
 *
 * This is used so that the model of bows changes as you use them, giving the visual effect
 * of pulling the bow.
 */

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class ItemModelPredicates implements ClientModInitializer {

    private static float isBowPulling(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity){
        return  entity != null && entity.isUsingItem() && entity.getActiveItem() == itemStack ? 1 : 0;
    }

    private static float getBowPull(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity){
        if (entity == null) {
            return 0.0F;
        } else {
            return entity.getActiveItem() != itemStack ?
                    0.0F :
                    ((CustomBowItem)itemStack.getItem()).getPullProgress(entity.getItemUseTime());
        }
    }

    private static float isTipped(ItemStack stack, ClientWorld clientWorld, LivingEntity entity){
        CompoundTag tag = stack.getTag();
        //99 == int T_T
        if(tag != null && tag.contains("CustomPotionColor", 99)){
            return 1;
        } else {
            Potion potion = PotionUtil.getPotion(tag);
            if(potion != null && potion != Potions.EMPTY){
                return 1;
            }
        }

        return  0;
    }

    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register
                (Items.GOLD_BOW, NileArchery.newId("pulling"), ItemModelPredicates::isBowPulling);

        FabricModelPredicateProviderRegistry.register
                (Items.GOLD_BOW, NileArchery.newId("pull"), ItemModelPredicates::getBowPull);

        FabricModelPredicateProviderRegistry.register
                (Items.SEA_ARROW, NileArchery.newId("tipped"), ItemModelPredicates::isTipped);

        FabricModelPredicateProviderRegistry.register
                (Items.ENDER_ARROW, NileArchery.newId("tipped"), ItemModelPredicates::isTipped);
    }
}