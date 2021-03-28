package com.github.theniles.archery.data.recipes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class SpectralTippedArrowRecipe extends SpecialCraftingRecipe {

    public SpectralTippedArrowRecipe(Identifier id) {
        super(id);
    }
//TODO fix
    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {

            Item arrowItem = craftingInventory.getStack(0).getItem();

            //only apply this recipe for vanilla spectral arrows or with our arrows
            //other mods might extend SpectralArrowItem (tho lbr its a .23238% chance )
            if(arrowItem != com.github.theniles.archery.items.Items.SPECTRAL_ARROW && arrowItem != Items.SPECTRAL_ARROW){
                return false;
            }

            for(int i = 0; i < craftingInventory.getWidth(); ++i) {
                for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                    ItemStack itemStack = craftingInventory.getStack(i + j * craftingInventory.getWidth());
                    if (itemStack.isEmpty()) {
                        return false;
                    }

                    Item item = itemStack.getItem();
                    if (i == 1 && j == 1) {
                        if (item != Items.LINGERING_POTION) {
                            return false;
                        }
                    } else if(item != Items.SPECTRAL_ARROW && item != com.github.theniles.archery.items.Items.SPECTRAL_ARROW){
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = craftingInventory.getStack(1 + craftingInventory.getWidth());
        if (itemStack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack2 =
                    new ItemStack(com.github.theniles.archery.items.Items.SPECTRAL_ARROW, 8);
            PotionUtil.setPotion(itemStack2, PotionUtil.getPotion(itemStack));
            List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(itemStack);
            //200 is the default vanilla duration
            effects.add(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0));
            PotionUtil.setCustomPotionEffects(itemStack2, effects);

            return itemStack2;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpecialRecipes.SPECTRAL_TIPPED_ARROW;
    }
}
