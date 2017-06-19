package net.xalcon.technomage.common.container;

import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.xalcon.technomage.Technomage;

public class InventoryCraftingProxy<T extends IItemHandler & IItemHandlerModifiable>
        extends InventoryCrafting
{
    /** List of the stacks in the crafting matrix. */
    //private final NonNullList<ItemStack> stackList;
    /** the width of the crafting inventory */
    private final int inventoryWidth;
    private final int inventoryHeight;
    /** Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged. */
    private final Container eventHandler;
    private final T matrix;

    public InventoryCraftingProxy(ContainerConstructionTable eventHandlerIn, T matrix)
    {
        super(eventHandlerIn, 0, 0);
        //this.stackList = NonNullList.withSize(3 * 3, ItemStack.EMPTY);
        this.eventHandler = eventHandlerIn;
        this.inventoryWidth = 3;
        this.inventoryHeight = 3;
        this.matrix = matrix;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.matrix.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        for (int i = 0; i < this.matrix.getSlots(); i++)
            if (!this.matrix.getStackInSlot(i).isEmpty())
                return false;
        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.matrix.getStackInSlot(index);
    }

    /**
     * Gets the ItemStack in the slot specified.
     */
    @Override
    public ItemStack getStackInRowAndColumn(int row, int column)
    {
        return row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight ? this.getStackInSlot(row + column * this.inventoryWidth) : ItemStack.EMPTY;
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName()
    {
        return Technomage.MOD_ID + ".container.construction_table";
    }

    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return this.matrix.extractItem(index, this.getInventoryStackLimit(), false);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = this.matrix.extractItem(index, count, false);

        if (!itemstack.isEmpty())
            this.eventHandler.onCraftMatrixChanged(this);

        return itemstack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.matrix.setStackInSlot(index, stack);
        this.eventHandler.onCraftMatrixChanged(this);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.matrix.getSlots(); i++)
            this.matrix.setStackInSlot(i, ItemStack.EMPTY);
    }

    @Override
    public int getHeight()
    {
        return this.inventoryHeight;
    }

    @Override
    public int getWidth()
    {
        return this.inventoryWidth;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void func_194018_a(RecipeItemHelper recipeItemhelper)
    {
        for (int i = 0; i < this.matrix.getSlots(); i++)
            recipeItemhelper.func_194112_a(this.matrix.getStackInSlot(i));
    }
}
