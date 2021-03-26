package com.github.theniles.archery.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.potion.Potion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ArrowEntity.class)
public interface ArrowEntityAccessor {

    @Accessor("potion")
    public Potion getPotion();

    @Accessor("effects")
    public Set<StatusEffectInstance> getEffects();

    @Accessor("colorSet")
    public boolean getColorSet();
}
