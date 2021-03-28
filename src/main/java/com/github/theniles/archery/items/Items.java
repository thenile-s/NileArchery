package com.github.theniles.archery.items;

import com.github.theniles.archery.NileArchery;

import com.github.theniles.archery.entities.Entities;
import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import com.github.theniles.archery.items.weapons.CustomBowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

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

    /* Adding a new arrow checklist:
        Create new EntityType<CustomArrowEntity> if needed
            Create the entity
            Register the type
            Register a renderer
        Create a new instance of CustomArrowItem or any subclass and register it
        Don't forget the tipped arrow model if needed
        Don't forget the lang file
        Don't forget the arrow tag (data/tags/items/arrows)
        Add any recipes if needed
        Register item model predicate for tipped
        Register color provider for tipped
     */

    /* Adding a new bow checklist:
        Create and register a CustomBowItem
        Add models for it
        Don't forget the lang file
        Add any recipes if needed
     */

    public static final ItemGroup MOD_GROUP;

    public static final CustomBowItem GOLD_BOW;

    public static final CustomArrowItem SEA_ARROW;

    public static final CustomArrowItem ENDER_ARROW;

    static {
        MOD_GROUP = FabricItemGroupBuilder.create(NileArchery.newId("item_group")).icon(()->new ItemStack(Registry.ITEM.get(NileArchery.newId("gold_bow")))).build();

        GOLD_BOW = new CustomBowItem(new Item.Settings().maxDamage(65).group(MOD_GROUP), 1.25F);

        SEA_ARROW = CustomArrowItem.newDefault(Entities.SEA_ARROW);

        ENDER_ARROW = CustomArrowItem.newDefault(Entities.ENDER_ARROW);
    }

    @Override
    public void onInitialize() {

        //TODO maybe auto registering items? Or maybe not...

        Registry.register(Registry.ITEM, NileArchery.newId("gold_bow"), GOLD_BOW);
        Registry.register(Registry.ITEM, NileArchery.newId("sea_arrow"), SEA_ARROW);
        Registry.register(Registry.ITEM, NileArchery.newId("ender_arrow"), ENDER_ARROW);
    }
}
