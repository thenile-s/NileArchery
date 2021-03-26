package com.github.theniles.archery.data.loot;

import com.github.theniles.archery.data.loot.GoldBowLootTableModification;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

/**
 * A mod initializer responsible for adding modifications to vanilla loot tables.
 */
@SuppressWarnings("unused")
public class LootTableModifications implements ModInitializer {

    public static final GoldBowLootTableModification GOLD_BOW = new GoldBowLootTableModification();

    @Override
    public void onInitialize() {
        LootTableLoadingCallback.EVENT.register(GOLD_BOW);
    }
}
