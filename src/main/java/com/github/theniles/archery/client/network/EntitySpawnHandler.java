package com.github.theniles.archery.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class EntitySpawnHandler implements ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(buf.readVarInt());
        UUID uuid = buf.readUuid();
        int id = buf.readVarInt();

        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        double velocityX = buf.readShort() / 8000.0D;
        double velocityY = buf.readShort() / 8000.0D;
        double velocityZ = buf.readShort() / 8000.0D;

        float pitch = buf.readByte() * 360 / 256.0F;

        float yaw = buf.readByte() * 360 / 256.0F;

        client.execute(()->{
            Entity entity = entityType.create(client.world);

            entity.setEntityId(id);
            entity.setUuid(uuid);

            entity.updateTrackedPosition(x, y, z);
            entity.setPos(x, y, z);
            entity.setVelocityClient(velocityX, velocityY, velocityZ    );
            entity.pitch = pitch;
            entity.yaw = yaw;
            client.world.addEntity(id, entity);
        });
    }
}
