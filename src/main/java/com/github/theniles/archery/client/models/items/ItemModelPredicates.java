package com.github.theniles.archery.client.models.items;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import com.github.theniles.archery.items.weapons.CustomBowItem;
import com.github.theniles.archery.items.Items;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;

/**
 * A client-side mod initializer used to register item model predicates.
 *
 * This is used so that the model of bows changes as you use them, giving the visual effect
 * of pulling the bow.
 * 
 * Who tf knows what int i in the predicates does :/ meh whatevre it appeared in 1.18.2
 */

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class ItemModelPredicates implements ClientModInitializer {

    private static float isBowPulling(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity, int i){
        return  entity != null && entity.isUsingItem() && entity.getActiveItem() == itemStack ? 1 : 0;
    }

    private static float getBowPull(ItemStack itemStack, ClientWorld clientWorld, LivingEntity entity, int i){
        if (entity == null) {
            return 0.0F;
        } else {
            return entity.getActiveItem() != itemStack ?
                    0.0F :
                    ((CustomBowItem)itemStack.getItem()).getPullProgress(entity.getItemUseTime());
        }
    }

    private static float isTipped(ItemStack stack, ClientWorld clientWorld, LivingEntity entity, int i){
        NbtCompound tag = stack.getNbt();
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

    public static final Identifier TIPPED = NileArchery.newId("tipped");

    public static final Identifier PULLING = NileArchery.newId("pulling");

    public static final Identifier PULL = NileArchery.newId("pull");

    private void registerTipped(CustomArrowItem item){
        FabricModelPredicateProviderRegistry.register(item, TIPPED, ItemModelPredicates::isTipped);
    }

    private void registerBow(CustomBowItem item){
        FabricModelPredicateProviderRegistry.register(item, PULLING, ItemModelPredicates::isBowPulling);
        FabricModelPredicateProviderRegistry.register(item, PULL, ItemModelPredicates::getBowPull);
    }

    @Override
    public void onInitializeClient() {
        registerBow(Items.GOLD_BOW);

        registerTipped(Items.SPECTRAL_ARROW);
        registerTipped(Items.ENDER_ARROW);
        registerTipped(Items.SEA_ARROW);
        registerTipped(Items.ASTRAL_ARROW);
        registerTipped(Items.AMETHYST_ARROW);
    }
}