package com.github.theniles.archery;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

/**
 * The heart of this mod.
 *
 * Well, not really. Just some helper functions or poorly designed configurations in this file :)
 */
public class NileArchery implements ModInitializer {
	public static final String MOD_ID = "nile_archery";

	/**
	 * Returns a new identifier with the mod's id as the namespace and the path as the path.
	 * @param path The path of the identifier.
	 * @return The identifier with the specified path and the mod's id as the namespace.
	 */
	public static Identifier newId(String path){
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		//TODO a way to set custom colors to arrows?
	}
}
