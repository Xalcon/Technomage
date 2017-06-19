package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryCraftingDummy extends InventoryCrafting
{
    public InventoryCraftingDummy(int width, int height)
    {
        super(new Container()
        {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn)
            {
                return true;
            }
        }, width, height);
    }
}
