package com.github.theniles.archery.items;

import com.github.theniles.archery.NileArchery;

import com.github.theniles.archery.entities.Entities;
import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import com.github.theniles.archery.items.weapons.BowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.List;

/**
 * A mod initializer that registers items in this mod.
 *
 * Contains field definitions for singletons of registered items.
 *
 * Also contains the code to configure the item group for items in this mod.
 *
 * "Have many items at your disposal and you will not know what to use in war.."
 * ~ Sun Tsu - The Art of War
 */
@SuppressWarnings("unused")
public class Items implements ModInitializer {

    public static final ItemGroup MOD_GROUP;

    public static final BowItem GOLD_BOW;

    public static final CustomArrowItem SEA_ARROW;

    private static void appendItemGroupStacks(List<ItemStack> displayItems){
        displayItems.add(new ItemStack(GOLD_BOW));
        displayItems.add(new ItemStack(SEA_ARROW));
    }

    static {
        GOLD_BOW = new BowItem(new Item.Settings().maxDamage(65), 1.25F);

        SEA_ARROW = new CustomArrowItem(new Item.Settings().maxCount(64), Entities.SEA_ARROW);

        MOD_GROUP = FabricItemGroupBuilder.create(
                NileArchery.newId("item_group"))
                .appendItems(Items::appendItemGroupStacks)
                .icon(() -> new ItemStack(GOLD_BOW)).build();
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, NileArchery.newId("gold_bow"), GOLD_BOW);
        Registry.register(Registry.ITEM, NileArchery.newId("sea_arrow"), SEA_ARROW);
    }
}
