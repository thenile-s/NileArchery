package com.github.theniles.archery.data.recipes;

import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CustomTippedArrowRecipe extends SpecialCraftingRecipe {
        public CustomTippedArrowRecipe(Identifier identifier) {
            super(identifier);
        }

        @Override
        public boolean matches(CraftingInventory craftingInventory, World world) {
            if (craftingInventory.getWidth() == 3 && craftingInventory.getHeight() == 3) {
                //TODO this code could be improved and perhaps made less hard coded
                //Although, it does work and is almost identical to vanilla implementation
                //"Vanilla. This twisted nightmare scape full of hard coded values and
                // unreal variable names. What will you make of it?"
                // ~Sun Tsu - The Art of Mod

                Item arrowItem = craftingInventory.getStack(0).getItem();

                if(!(arrowItem instanceof CustomArrowItem) || !((CustomArrowItem)arrowItem).getCraftsTipped()){
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
                        } else if (!(item == arrowItem)) {
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
                ItemStack itemStack2 = new ItemStack(craftingInventory.getStack(0).getItem(), 8);
                PotionUtil.setPotion(itemStack2, PotionUtil.getPotion(itemStack));
                PotionUtil.setCustomPotionEffects(itemStack2, PotionUtil.getCustomPotionEffects(itemStack));
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
            return SpecialRecipes.CUSTOM_TIPPED_ARROW;
        }
}
