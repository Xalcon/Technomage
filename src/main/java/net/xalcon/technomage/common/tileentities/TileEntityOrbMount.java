package net.xalcon.technomage.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOrbMount extends TileEntity
{
    private ItemStack orb = ItemStack.EMPTY;

    public ItemStack getOrbItemStack()
    {
        return this.orb;
    }

    public void setOrbItemStack(ItemStack orb)
    {

    }
}
