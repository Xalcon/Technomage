package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerBrickFurnace extends Container
{
    private EntityPlayer player;

    public ContainerBrickFurnace(EntityPlayer player)
    {
        this.player = player;
        this.bindPlayerInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    private void bindPlayerInventory()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                this.addSlotToContainer(new Slot(this.player.inventory, col + row * 9 + 9, 8 + col * 18, 103 + row * 18));
            }
        }

        for (int slot = 0; slot < 9; slot++)
        {
            this.addSlotToContainer(new Slot(this.player.inventory, slot, 8 + slot * 18, 161));
        }
    }
}
