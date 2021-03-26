package com.github.theniles.archery.client.models.items;

import com.github.theniles.archery.items.Items;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

/**
 * This client-side mod initializer registers color providers for certain items.
 *
 * This is used to make certain items, such as custom tipped arrows show up with
 * a color representing their state, such as their potion effects.
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class ModelColors implements ClientModInitializer {

    public static int getCustomTippedArrowTint(ItemStack itemStack, int tintIndex){

        if(tintIndex == 1){
            CompoundTag tag = itemStack.getTag();
            //99 == int T_T
            if(tag != null && tag.contains("CustomPotionColor", 99)){
                return tag.getInt("CustomPotionColor");
            } else {
                return PotionUtil.getColor(PotionUtil.getPotion(tag));
            }
        }

            //(the alpha component is not applied in the tint)
            //had to register a model predicate instead of returning alpha in the tint D:

        return 0xFFFFFFFF;
    }

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(ModelColors::getCustomTippedArrowTint, new ArrowItem[]{Items.SEA_ARROW});
    }
}
