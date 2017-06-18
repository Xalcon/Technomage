package net.xalcon.technomage.common.container;

import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.xalcon.technomage.Technomage;

import javax.annotation.Nonnull;

public class InventoryCraftingProxy extends InventoryCrafting implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound>
{
    private final static int INVENTORY_WIDTH = 3;
    private final static int INVENTORY_HEIGHT = 3;
    private final static int SIZE = INVENTORY_WIDTH * INVENTORY_HEIGHT;
    private final Container eventHandler;

    protected NonNullList<ItemStack> matrix = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    public InventoryCraftingProxy(Container eventHandlerIn, int width, int height)
    {
        super(eventHandlerIn, width, height);
        this.eventHandler = eventHandlerIn;
    }

    //region IItemHandler implementation
    @Override
    public int getSlots()
    {
        return SIZE;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        ItemStack existing = this.matrix.get(slot);

        int limit = this.getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.matrix.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            this.onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return ItemStack.EMPTY;

        ItemStack existing = this.matrix.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                this.matrix.set(slot, ItemStack.EMPTY);
                this.onContentsChanged(slot);
            }
            return existing;
        }
        else
        {
            if (!simulate)
            {
                this.matrix.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                this.onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.matrix.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        if (ItemStack.areItemStacksEqual(this.matrix.get(slot), stack))
            return;
        this.matrix.set(slot, stack);
        this.onContentsChanged(slot);
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.matrix.size(); i++)
        {
            if (!this.matrix.get(i).isEmpty())
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                this.matrix.get(i).writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < this.matrix.size())
            {
                this.matrix.set(slot, new ItemStack(itemTags));
            }
        }
        this.onLoad();
    }

    private int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return Math.min(this.getSlotLimit(slot), stack.getMaxStackSize());
    }

    protected void onLoad()
    {

    }

    protected void onContentsChanged(int slot)
    {

    }
    //endregion


    @Override
    public int getSizeInventory()
    {
        return this.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.matrix)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInRowAndColumn(int row, int column)
    {
        return this.getStackInSlot(row + column * this.getWidth());
    }

    @Override
    public String getName()
    {
        return Technomage.MOD_ID + ".container.construction_table";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack outStack = this.getStackInSlot(index);
        this.setStackInSlot(index, ItemStack.EMPTY);
        return outStack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = this.extractItem(index, count, false);

        if (!itemstack.isEmpty())
        {
            this.eventHandler.onCraftMatrixChanged(this);
        }

        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.setStackInSlot(index, stack);
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return this.getSlotLimit(0);
    }

    @Override
    public void clear()
    {
        for(int i = 0; i < SIZE; i++)
            this.matrix.set(i, ItemStack.EMPTY);
    }

    @Override
    public int getHeight()
    {
        return 3;
    }

    @Override
    public int getWidth()
    {
        return 3;
    }

    @Override
    public void func_194018_a(RecipeItemHelper recipeItemHelper)
    {
        for (ItemStack itemstack : this.matrix)
        {
            recipeItemHelper.func_194112_a(itemstack);
        }
    }
}
