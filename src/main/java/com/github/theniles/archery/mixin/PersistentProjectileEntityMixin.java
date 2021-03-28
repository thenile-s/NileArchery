package com.github.theniles.archery.mixin;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PersistentProjectileEntity.class)
public interface PersistentProjectileEntityMixin {

    @Accessor("punch")
    public int getPunch();

    @Accessor("piercedEntities")
    public IntOpenHashSet getPiercedEntities();

    @Accessor("piercedEntities")
    public void setPiercedEntities(IntOpenHashSet set);

    @Accessor("piercingKilledEntities")
    public List<Entity> getPiercedKilledEntities();

    @Accessor("piercingKilledEntities")
    public void setPiercedKilledEntities(List<Entity> list);

}
