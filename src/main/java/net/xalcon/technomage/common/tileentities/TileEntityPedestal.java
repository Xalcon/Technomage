package net.xalcon.technomage.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityPedestal extends TileEntityTMBase
{
    private ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityPedestal self = TileEntityPedestal.this;
            IBlockState state = self.getWorld().getBlockState(self.getPos());
            self.getWorld().notifyBlockUpdate(self.getPos(), state, state, 3);
        }
    };

    public ItemStack getItemStack()
    {
        return this.inventory.getStackInSlot(0);
    }

    public void removeItemStack()
    {
        this.inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public boolean putItemStack(ItemStack itemStack)
    {
        ItemStack storedItem = this.inventory.getStackInSlot(0);
        if(!storedItem.isEmpty()) return false;
        this.inventory.setStackInSlot(0, itemStack);
        return true;
    }

    @Override
    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        super.readNbt(nbt, type);
        this.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        nbt.setTag("inventory", this.inventory.serializeNBT());
        return super.writeNbt(nbt, type);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory);
        return super.getCapability(capability, facing);
    }
}
