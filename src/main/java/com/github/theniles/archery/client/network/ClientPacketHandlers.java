package com.github.theniles.archery.client.network;

import com.github.theniles.archery.PacketChannelIdentifiers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class ClientPacketHandlers implements ClientModInitializer {

    public static EntitySpawnHandler ENTITY_SPAWN_HANDLER = new EntitySpawnHandler();

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(PacketChannelIdentifiers.ENTITY_SPAWN, ENTITY_SPAWN_HANDLER);

    }
}
