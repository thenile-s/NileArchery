package com.github.theniles.archery.client.renderers.entities;

import com.github.theniles.archery.NileArchery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

/**
 * Stores static final identifiers of textures used by entity renderers
 */
@Environment(EnvType.CLIENT)
public class Textures {
    public static final Identifier SEA_ARROW = NileArchery.newId("textures/entity/sea_arrow.png");
    public static final Identifier ENDER_ARROW = NileArchery.newId("textures/entity/ender_arrow.png");
    public static final Identifier SPECTRAL_ARROW = new Identifier("minecraft","textures/entity/projectiles/spectral_arrow.png");
    public static final Identifier ASTRAL_ARROW = NileArchery.newId("textures/entity/astral_arrow.png");
    public static final Identifier COMET = NileArchery.newId("textures/entity/comet.png");
    public static final Identifier AMETHYST_ARROW = NileArchery.newId("textures/entity/amethyst_arrow.png");
    public static final Identifier AMETHYST_SHARD = NileArchery.newId("textures/entity/amethyst_shard.png");
}
