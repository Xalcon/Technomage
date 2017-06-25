package net.xalcon.technomage.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.xalcon.technomage.common.entities.EntityBoomerang;

public class ItemBoomerang extends Item
{
    /**
     * Called when the equipped item is right clicked.
     *
     * @param worldIn
     * @param playerIn
     * @param handIn
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if(worldIn.isRemote)
        {
            EntityBoomerang boomerang = new EntityBoomerang(worldIn, playerIn);
            boomerang.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 0.0F);
            //boomerang.setNoGravity(true);

            worldIn.spawnEntity(boomerang);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
