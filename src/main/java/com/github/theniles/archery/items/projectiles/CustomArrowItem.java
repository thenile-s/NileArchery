package com.github.theniles.archery.items.projectiles;

import com.github.theniles.archery.entities.CustomArrowEntity;
import com.github.theniles.archery.items.Items;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Base class for all new arrow items.
 *
 * This item type allows us easily to create configurable arrows.
 *
 * Vanilla (eww) has a new Item class for each arrow type! (tipped, normal, spectral)
 */
public class CustomArrowItem extends ArrowItem {

    private EntityType<? extends CustomArrowEntity> arrowEntityType;

    public EntityType<? extends CustomArrowEntity> getArrowEntityType() {
        return arrowEntityType;
    }

    public void setArrowEntityType(@NotNull EntityType<? extends CustomArrowEntity> arrowEntityType) {
        this.arrowEntityType = Validate.notNull(arrowEntityType);
    }

    private ArrowItem pickupItem;

    public ArrowItem getPickupItem(){
        return pickupItem;
    }

    public void setPickupItem(ArrowItem item){
        pickupItem = item;
    }

    private boolean persistsStatusEffects;

    public boolean getPersistsStatusEffects() {
        return persistsStatusEffects;
    }

    public void setPersistsStatusEffects(boolean persistsStatusEffects) {
        this.persistsStatusEffects = persistsStatusEffects;
    }

    public boolean getCraftsTipped() {
        return craftsTipped;
    }

    public void setCraftsTipped(boolean craftsTipped) {
        this.craftsTipped = craftsTipped;
    }

    private boolean craftsTipped;

    public CustomArrowItem(Settings settings, EntityType<? extends CustomArrowEntity> arrowEntityType, ArrowItem pickupItem, boolean persistsStatusEffects, boolean craftsTipped) {
        super(settings);
        setArrowEntityType(arrowEntityType);
        setPickupItem(pickupItem);
        setPersistsStatusEffects(persistsStatusEffects);
        setCraftsTipped(craftsTipped);
    }

    public CustomArrowItem(Settings settings, EntityType<? extends CustomArrowEntity> arrowEntityType, boolean persistsStatusEffects, boolean craftsTipped) {
        super(settings);
        setArrowEntityType(arrowEntityType);
        setPickupItem(this);
        setPersistsStatusEffects(persistsStatusEffects);
        setCraftsTipped(craftsTipped);
    }

    /**
     * Creates an arrow item with default (and vanilla-like behaviour)
     *
     * Can craft tipped versions, tipped effect persists
     * Picks up itself
     * Max count is 64
     * placed in this mod's default creative tab
     *
     * @param arrowEntityType What arrow entity to create when this arrow is fired
     * @return A CustomArrowItem with the settings above and the arrowEntityType specified
     */
    public static CustomArrowItem newDefault(EntityType<? extends  CustomArrowEntity> arrowEntityType){
        return new CustomArrowItem(new Settings().maxCount(64).group(Items.MOD_GROUP), arrowEntityType, true, true);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        CustomArrowEntity arrowEntity = getArrowEntityType().create(world);

        arrowEntity.setOwner(shooter);

        arrowEntity.setPickupItem(getPickupItem());

        //vanilla (and our own mixin-ed :]) implementation sets status effects here
        arrowEntity.initFromStack(stack);

        if(shooter instanceof PlayerEntity){
            arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        }

        //The strange y subtraction value is taken from a PersistentProjectileEntity constructor
        arrowEntity.updatePosition(shooter.getX(), shooter.getEyeY() - 0.10000000149011612D, shooter.getZ());

        return  arrowEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int i = tooltip.size();

        //Tipped arrows have the same float value :/
        PotionUtil.buildTooltip(stack, tooltip, 0.125F);
    }
}
