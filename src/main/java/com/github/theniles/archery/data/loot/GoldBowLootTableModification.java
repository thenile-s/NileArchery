package com.github.theniles.archery.data.loot;

import com.github.theniles.archery.items.Items;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

import net.minecraft.loot.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class GoldBowLootTableModification implements LootTableLoadingCallback {

    @Override
    public void onLootTableLoading(ResourceManager resourceManager, LootManager manager, Identifier id, FabricLootSupplierBuilder supplier, LootTableSetter setter) {
        //Only find it in bastion treasure chests
        if(id.equals(LootTables.BASTION_TREASURE_CHEST)){
            //Find it once per chest, with a durability between 20% and 80% of its durability
            LootPool loot = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .withEntry(ItemEntry.builder(Items.GOLD_BOW)
                            .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2F, 0.8F))).build())
                    .build();

            supplier.withPool(loot);
        }
    }
}
