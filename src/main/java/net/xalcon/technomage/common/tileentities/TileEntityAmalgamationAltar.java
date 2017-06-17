package net.xalcon.technomage.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.technomage.common.crafting.Registries;
import net.xalcon.technomage.common.crafting.amalgamation.AmalgamationRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityAmalgamationAltar extends TileEntityTMBase implements ITickable
{
    private static Vec3i[] pedestalOffsets =
    {
        new Vec3i(3,0, 1),
        new Vec3i(3,0, -1),
        new Vec3i(-3,0, 1),
        new Vec3i(-3,0, -1),
        new Vec3i(1,0, 3),
        new Vec3i(1,0, -3),
        new Vec3i(-1,0, 3),
        new Vec3i(-1,0, -3),
    };


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

    public void startCrafting()
    {
        if (this.getWorld().isRemote) return;

        NonNullList<ItemStack> items = this.getPedestalItems();
        ItemStack coreItem = this.getItemStack();
        AmalgamationRecipe recipe = Registries.AMALGAMATION.getRecipes().stream()
                .filter(r -> r.isMatch(coreItem, items))
                .findFirst()
                .orElse(null);
        if(recipe != null)
        {
            this.removeItemStack();
            this.putItemStack(recipe.getOutput());
            this.getWorld().playSound(null, this.getPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    public NonNullList<ItemStack> getPedestalItems()
    {
        NonNullList<ItemStack> items = NonNullList.create();
        for(Vec3i offset : pedestalOffsets)
        {
            TileEntity tile = this.getWorld().getTileEntity(this.getPos().add(offset));
            if(tile instanceof TileEntityPedestal)
            {
                ItemStack item = ((TileEntityPedestal) tile).getItemStack();
                if(!item.isEmpty())
                    items.add(item);
            }
        }
        return items;
    }
}
