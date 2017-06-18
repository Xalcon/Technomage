package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

public class ContainerConstructionTable extends ContainerTM
{
    private final EntityPlayer player;
    private final TileEntityConstructionTable tile;

    public ContainerConstructionTable(EntityPlayer player, TileEntityConstructionTable tile)
    {
        this.player = player;
        this.tile = tile;

        this.bindPlayerInventory(player);
        this.bindContainerSlots();
    }

    @Override
    public int getPlayerInventoryOffset() { return 142; }

    public void bindContainerSlots()
    {
        IItemHandler inventory = this.tile.getInventory();
        IItemHandler craftMatrix = this.tile.getMatrix();
        IItemHandler output = this.tile.getOutput();

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new SlotItemHandler(inventory, i, 8 + i * 18, 90));
            this.addSlotToContainer(new SlotItemHandler(inventory, i + 9, 8 + i * 18, 108));
        }

        for(int col = 0; col < 3; col++)
        {
            for(int row = 0; row < 3; row++)
            {
                int index = 3 * col + row;
                this.addSlotToContainer(new SlotItemHandler(craftMatrix, index, 48 + col * 18, 18 + row * 18));
            }
        }

        this.addSlotToContainer(new SlotItemHandler(output, 0, 143, 36));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }
}
