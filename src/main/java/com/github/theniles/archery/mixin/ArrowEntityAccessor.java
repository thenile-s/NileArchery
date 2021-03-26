package com.github.theniles.archery.mixin;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.potion.Potion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(ArrowEntity.class)
public interface ArrowEntityAccessor {

    @Accessor("potion")
    public Potion getPotion();

    @Accessor("potion")
    public void setPotion(Potion potion);

    @Accessor("effects")
    public Set<StatusEffectInstance> getEffects();

    @Accessor("colorSet")
    public boolean getColorSet();

    @Invoker("initColor")
    public void invokeInitColor();

    @Invoker("setColor")
    public void invokeSetColor(int color);

    @Accessor("COLOR")
    public TrackedData<Integer> getCOLOR();

    @Accessor("colorSet")
    public void setColorSet(boolean b);
}
