package com.github.theniles.archery.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

/**
 * Utility class to create new spawn packets for entities.
 */
public class EntitySpawnPacket {
    public static Packet<?> newPacket(Entity entity){
        return ServerPlayNetworking.createS2CPacket(PacketChannelIdentifiers.ENTITY_SPAWN,
                writeSpawnData(new PacketByteBuf(Unpooled.buffer()), entity));
    }

    public static PacketByteBuf writeSpawnData(PacketByteBuf packetByteBuf, Entity entity){
        packetByteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
        packetByteBuf.writeUuid(entity.getUuid());
        packetByteBuf.writeVarInt(entity.getId());

        packetByteBuf.writeDouble(entity.getX());
        packetByteBuf.writeDouble(entity.getY());
        packetByteBuf.writeDouble(entity.getZ());

        Vec3d velocity = entity.getVelocity();

        //Why clamp the velocity? Idk, but vanilla does it too
        //Guess its worth it to reduce packet size...?
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.x, -3.9D, 3.9D) * 8000.0D));
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.y, -3.9D, 3.9D) * 8000.0D));
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.z, -3.9D, 3.9D) * 8000.0D));

        packetByteBuf.writeByte(MathHelper.floor(entity.getPitch() * 256.0F / 360.0F));
        packetByteBuf.writeByte(MathHelper.floor(entity.getYaw() * 256.0F / 360.0F));

        return  packetByteBuf;
    }
}
