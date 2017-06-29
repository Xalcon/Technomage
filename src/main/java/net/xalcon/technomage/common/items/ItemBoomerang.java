package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.common.entities.EntityBoomerang;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class ItemBoomerang extends Item implements IItemModelRegisterHandler
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

        if(!worldIn.isRemote)
        {
            EntityBoomerang boomerang = new EntityBoomerang(worldIn, playerIn);
            boomerang.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0.0F);
            boomerang.setNoGravity(true);

            playerIn.getCooldownTracker().setCooldown(this, 10 * 20);

            worldIn.spawnEntity(boomerang);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation rl = item.getRegistryName();
        assert rl != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(rl, "inventory"));
    }
}
