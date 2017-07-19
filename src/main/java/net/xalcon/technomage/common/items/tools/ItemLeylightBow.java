package net.xalcon.technomage.common.items.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.xalcon.technomage.common.init.TMItems;

public class ItemLeylightBow extends Item
{
    public ItemLeylightBow()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(384);

        this.addPropertyOverride(new ResourceLocation("pull"), (stack, worldIn, entityIn) ->
            entityIn == null || entityIn.getActiveItemStack().getItem() != TMItems.leylightBow()
                ? 0.0F
                : (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / getDrawTime());

        this.addPropertyOverride(new ResourceLocation("pulling"), (stack, worldIn, entityIn) ->
            entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack
                ? 1.0F
                : 0.0F);
    }

    private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    protected boolean isArrow(ItemStack stack)
    {
        return stack.getItem() instanceof ItemArrow;
    }

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        int r = this.getMaxItemUseDuration(stack) - count;
        if(r >= getDrawTime())
        {
            EnumHand activeHand = player.getActiveHand();
            player.stopActiveHand();
            player.setActiveHand(activeHand);
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            boolean isCreativeMode = entityplayer.capabilities.isCreativeMode;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int power = this.getMaxItemUseDuration(stack) - timeLeft;
            power = ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, power, !itemstack.isEmpty() || isCreativeMode);
            if (power < 0) return;

            if (!itemstack.isEmpty() || isCreativeMode)
            {
                if (itemstack.isEmpty())
                    itemstack = new ItemStack(Items.ARROW);

                if (this.canShootArrow(itemstack, power))
                {
                    boolean isInfArrow = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        this.shootArrow(worldIn, itemstack, entityplayer, power);
                        stack.damageItem(1, entityplayer);
                    }

                    worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

                    if (!isInfArrow && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

    private boolean canShootArrow(ItemStack itemstack, int power)
    {
        return power > 1;
    }

    private void shootArrow(World world, ItemStack arrowStack, EntityLivingBase entity, int ticksUsed)
    {
        if(world.isRemote) return;
        float velocityMultiplier = getArrowVelocity(ticksUsed);
        EntityArrow arrow = ((ItemArrow)arrowStack.getItem()).createArrow(world, arrowStack, entity);
        arrow.setAim(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, velocityMultiplier * 3.0F, 1.0F);

        if (velocityMultiplier == 1.0F)
            arrow.setIsCritical(true);

        arrow.setDamage(10f);

        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)
            arrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

        world.spawnEntity(arrow);
    }

    protected static float getDrawTime()
    {
        return 2.0f;
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge)
    {
        float f = (float) charge / getDrawTime();
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
            f = 1.0F;

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean hasNoAmmo = this.findAmmo(playerIn).isEmpty();

        ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, hasNoAmmo);
        if (ret != null) return ret;

        if (!playerIn.capabilities.isCreativeMode && hasNoAmmo)
        {
            //noinspection ConstantConditions - No intellij, hasNoAmmo is not always true >_>
            return new ActionResult<>(hasNoAmmo ? EnumActionResult.PASS : EnumActionResult.FAIL, itemstack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return 0;
    }
}
