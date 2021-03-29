package com.github.theniles.archery.network;

import com.github.theniles.archery.NileArchery;
import net.minecraft.util.Identifier;

/**
 * Used to initialise and access the identifiers for various network play channels.
 */
public class PacketChannelIdentifiers {
    public  static  final Identifier ENTITY_SPAWN = NileArchery.newId("entity_spawn");
}
