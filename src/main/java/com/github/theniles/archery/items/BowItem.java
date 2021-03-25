package com.github.theniles.archery.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.function.Predicate;

/**
 * This class contains code to instantiate configurable bow items.
 * Not all the code related to how bows works is located here.
 */
public class BowItem extends RangedWeaponItem implements Vanishable {

    /**
     * Constructs a bow item with the specified item settings and bow settings.
     * @param settings The item settings
     * @param pullProgressDivisor A value used to control how fast the bow charges up.
     */
    public BowItem(Settings settings, float pullProgressDivisor) {
        super(settings);

        this.pullProgressDivisor = pullProgressDivisor;
    }

    private final float pullProgressDivisor;

    /**
     * Returns a value used to control how quickly the bow charges up.
     *
     * Keep in mind that the pull level is not directly proportional to the use time of the bow.
     *
     * Higher values charge the bow slower.
     *
     * Vanilla bow is 3.0F
     * To charge it twice as fast, 1.25F would be used.
     *
     * A graphing tool can help fine tune this value : https://www.desmos.com/calculator
     *
     * @return A value used to scale the pull progress of the bow when calculating it.
     */
    public float getPullProgressDivisor(){
        return  pullProgressDivisor;
    }

    /**
     * Returns a value indicating the charge level of a bow.
     *
     * 0 - no charge
     * 1 - maximum charge
     *
     * This value does not scale linearly with useTicks.
     *
     * @param useTicks The amount of time in ticks that the bow has been used for.
     * @return The charge level of the bow
     */
    public float getPullProgress(int useTicks){
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / getPullProgressDivisor();
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        // Vanilla does this too :/ (72000 ticks == 6 minutes)
        // This means that after using a bow item for 6 minutes you will stop using it
        // and the arrow will be fired
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getArrowType(itemStack).isEmpty();
        if (!user.abilities.creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        //TODO maybe look over the vanilla code for your own learning :p (To Daniel)
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean bl = playerEntity.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = playerEntity.getArrowType(stack);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(net.minecraft.item.Items.ARROW);
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if (!((double)f < 0.1D)) {
                    boolean bl2 = bl && itemStack.getItem() == net.minecraft.item.Items.ARROW;
                    if (!world.isClient) {
                        ArrowItem arrowItem = (ArrowItem)((ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : net.minecraft.item.Items.ARROW));
                        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
                        persistentProjectileEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            persistentProjectileEntity.setCritical(true);
                        }

                        int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            persistentProjectileEntity.setPunch(k);
                        }

                        if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                            persistentProjectileEntity.setOnFireFor(100);
                        }

                        stack.damage(1, playerEntity, (p) -> {
                            p.sendToolBreakStatus(playerEntity.getActiveHand());
                        });
                        if (bl2 || playerEntity.abilities.creativeMode && (itemStack.getItem() == net.minecraft.item.Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
                            persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        world.spawnEntity(persistentProjectileEntity);
                    }

                    world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!bl2 && !playerEntity.abilities.creativeMode) {
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            playerEntity.inventory.removeOne(itemStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    @Override
    public int getEnchantability() {
        return ToolMaterials.GOLD.getEnchantability();
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return RangedWeaponItem.BOW_PROJECTILES;
    }

    @Override
    public int getRange() {
        //Vanilla has this too
        //Maybe its used for entity AI's?
        return 15;
    }
}
