package com.github.theniles.archery.entities;

import com.github.theniles.archery.PacketChannelIdentifiers;
import com.github.theniles.archery.items.projectiles.CustomArrowItem;
import com.github.theniles.archery.mixin.ArrowEntityAccessor;
import io.netty.buffer.Unpooled;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * Base class for all new arrows.
 *
 * Contains common code they all need.
 *
 * Right now, its just the spawn packet creation.
 *
 * "When brain is toast, the mind has no wisdom to boast."
 * ~ Sun Tsu - The Art of Mod
 */
public abstract class CustomArrowEntity extends ArrowEntity {

    /**
     * This constructor is the factory default, all entities must have this.
     *
     * @param entityType The type of the entity
     * @param world The world which the entity should be in. However, this constructor not add it to the world.
     */
    public CustomArrowEntity(EntityType<? extends CustomArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    protected ArrowItem pickupItem;

    public ArrowItem getPickupItem() {
        return pickupItem;
    }

    public void setPickupItem(ArrowItem pickupItem) {
        this.pickupItem = pickupItem;
    }

    @Override
    protected ItemStack asItemStack() {
        //We can do this because an ItemStack with a null Item will be set to Items.AIR :)
        ItemStack itemStack = new ItemStack(getPickupItem());

        //Set potion effects only if there is an actual item
        //If there is an actual item, only save potion effects if we made the item to do it so
        //Or if it we did not make the item in teh first place
        if(itemStack.getItem() != null &&
                (!(itemStack.getItem() instanceof  CustomArrowItem) ||
                ((CustomArrowItem)itemStack.getItem()).getPersistsStatusEffects())){
            ArrowEntityAccessor accessor = ((ArrowEntityAccessor)(ArrowEntity)this);
            PotionUtil.setPotion(itemStack, accessor.getPotion());
            PotionUtil.setCustomPotionEffects(itemStack, accessor.getEffects());
            if(accessor.getColorSet()){
                itemStack.getOrCreateTag().putInt("CustomPotionColor", getColor());
            }
        }
        return itemStack;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return ServerPlayNetworking.createS2CPacket(PacketChannelIdentifiers.ENTITY_SPAWN, writeSpawnData(new PacketByteBuf(Unpooled.buffer())));
    }

    private PacketByteBuf writeSpawnData(PacketByteBuf packetByteBuf){
        packetByteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(getType()));
        packetByteBuf.writeUuid(getUuid());
        packetByteBuf.writeVarInt(getEntityId());
//TODO improved packet api
        packetByteBuf.writeDouble(getX());
        packetByteBuf.writeDouble(getY());
        packetByteBuf.writeDouble(getZ());

        Vec3d velocity = getVelocity();

        //Why clamp the velocity? Idk, but vanilla does it too
        //Guess its worth it to reduce packet size...?
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.x, -3.9D, 3.9D) * 8000.0D));
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.y, -3.9D, 3.9D) * 8000.0D));
        packetByteBuf.writeShort((int)(MathHelper.clamp(velocity.z, -3.9D, 3.9D) * 8000.0D));

        packetByteBuf.writeByte(MathHelper.floor(pitch * 256.0F / 360.0F));
        packetByteBuf.writeByte(MathHelper.floor(yaw * 256.0F / 360.0F));

        return  packetByteBuf;
    }
}
