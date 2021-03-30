package com.github.theniles.archery.mixin;

import com.github.theniles.archery.NileArchery;
import com.github.theniles.archery.items.weapons.CustomBowItem;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//TODO PERMANENT TODO IN EVERY VERSION, CHECK THE BYTE CODE FOR THIS MIXIN
//3 is bow, i checked the byte code
@Mixin(targets = "net.minecraft.enchantment.EnchantmentTarget$3")
public class BowEnchantmentTargetMixin {
    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/enchantment/EnchantmentTarget$3;isAcceptableItem(Lnet/minecraft/item/Item;)Z", cancellable = true)
    private void checkItemType(Item item, CallbackInfoReturnable<Boolean> cbi){
        if(item instanceof CustomBowItem){
            cbi.setReturnValue(true);
        }
    }
}
