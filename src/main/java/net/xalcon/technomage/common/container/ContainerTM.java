package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerTM extends Container
{
    protected int playerInventoryOffsetStart = -1;
    protected int playerInventoryOffsetEnd = -1;

    public int getPlayerInventoryOffset() { return 78; }

    protected void bindPlayerInventory(EntityPlayer player)
    {
        this.playerInventoryOffsetStart = this.inventorySlots.size();
        int offset = this.getPlayerInventoryOffset();
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                this.addSlotToContainer(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, offset + row * 18));
            }
        }

        for (int slot = 0; slot < 9; slot++)
        {
            this.addSlotToContainer(new Slot(player.inventory, slot, 8 + slot * 18, offset + 58));
        }
        this.playerInventoryOffsetEnd = this.inventorySlots.size() - 1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
