package net.xalcon.technomage.common.tileentities;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

import javax.annotation.Nullable;

public class TileEntityConstructionTable extends TileEntityTMBase
{
    private ItemStackHandler inventory = new ItemStackHandler(18);
    private ItemStackHandler craftMatrix = new ItemStackHandler(9);
    private InventoryCraftResult output = new InventoryCraftResult();

    public IItemHandler getInventory() { return this.inventory; }
    public ItemStackHandler getMatrix() { return this.craftMatrix; }
    public InventoryCraftResult getCraftResult() { return this.output; }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == ITEM_HANDLER_CAPABILITY
                ? ITEM_HANDLER_CAPABILITY.cast(this.inventory)
                : super.getCapability(capability, facing);
    }

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
