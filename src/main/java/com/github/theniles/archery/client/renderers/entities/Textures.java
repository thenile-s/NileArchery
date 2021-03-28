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
}
