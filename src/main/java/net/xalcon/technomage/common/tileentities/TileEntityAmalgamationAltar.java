package net.xalcon.technomage.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityAmalgamationAltar extends TileEntityTMBase implements ITickable
{
    private ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityAmalgamationAltar self = TileEntityAmalgamationAltar.this;
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

    @Override
    public void update()
    {
        //if(this.world.isRemote) return;
        if(this.getItemStack().isEmpty()) return;
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK,
                this.pos.getX() + .5,
                this.pos.getY() + 1.3,
                this.pos.getZ() + .5,
                0, 0.5D, 0, Item.getIdFromItem(this.getItemStack().getItem()));
    }
}
