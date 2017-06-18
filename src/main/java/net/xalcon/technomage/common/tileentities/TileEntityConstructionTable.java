package net.xalcon.technomage.common.tileentities;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityConstructionTable extends TileEntityTMBase
{
    private ItemStackHandler inventory = new ItemStackHandler(18);
    private ItemStackHandler craftMatrix = new ItemStackHandler(9);
    private InventoryCraftResult output = new InventoryCraftResult();

    public IItemHandler getInventory() { return this.inventory; }
    public ItemStackHandler getMatrix() { return this.craftMatrix; }
    public InventoryCraftResult getCraftResult() { return this.output; }

    @Override
    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        if(type.isFullSync())
        {
            this.craftMatrix.deserializeNBT(nbt.getCompoundTag("matrix"));
            this.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        }
        super.readNbt(nbt, type);
    }

    @Override
    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        if(type.isFullSync())
        {
            nbt.setTag("matrix", this.craftMatrix.serializeNBT());
            nbt.setTag("inventory", this.inventory.serializeNBT());
        }
        return super.writeNbt(nbt, type);
    }
}
