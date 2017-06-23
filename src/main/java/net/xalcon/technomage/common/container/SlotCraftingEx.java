package net.xalcon.technomage.common.container;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SlotCraftingEx<T extends IItemHandler> extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final InventoryCrafting craftMatrix;
    private final T inventory;
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player;
    private final InventoryCraftResult craftingResultInventory;
    private final ContainerConstructionTable container;
    /** The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset. */
    private int amountCrafted;

    private final InventoryCrafting dummyMatrix = new InventoryCraftingDummy(3, 3);

    public SlotCraftingEx(ContainerConstructionTable container, EntityPlayer player, InventoryCrafting craftingInventory, InventoryCraftResult craftingResultInventory, T inventory, int slotIndex, int xPosition, int yPosition)
    {
        super(craftingResultInventory, slotIndex, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = craftingInventory;
        this.inventory = inventory;
        this.craftingResultInventory = craftingResultInventory;
        this.container = container;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onSwapCraft(int amount)
    {
        this.amountCrafted += amount;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack stack)
    {
        if (this.amountCrafted > 0)
        {
            stack.onCrafting(this.player.world, this.player, this.amountCrafted);
            FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, this.craftMatrix);
        }

        this.amountCrafted = 0;
        InventoryCraftResult inventorycraftresult = this.craftingResultInventory;
        IRecipe irecipe = inventorycraftresult.getRecipeUsed();

        if (irecipe != null && !irecipe.isHidden())
        {
            this.player.unlockRecipes(Lists.newArrayList(irecipe));
            inventorycraftresult.setRecipeUsed(null);
        }
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack)
    {
        this.onCrafting(stack);
        IRecipe recipe;
        NonNullList<ItemStack> remainingItems;
        try
        {
            ForgeHooks.setCraftingPlayer(player);
            //recipe = CraftingManager.findMatchingRecipe(this.craftMatrix, player.getEntityWorld());
            recipe = this.container.getCachedRecipe();
            if(recipe == null || !recipe.matches(this.craftMatrix, player.getEntityWorld())) return ItemStack.EMPTY;
            remainingItems = recipe.getRemainingItems(this.craftMatrix);

        }
        finally
        {
            ForgeHooks.setCraftingPlayer(null);
        }

        for(int i = 0; i < 9; i++)
            this.dummyMatrix.setInventorySlotContents(i, this.craftMatrix.getStackInSlot(i));
        boolean dummyCraftFailed = false;

        for (int i = 0; i < remainingItems.size(); ++i)
        {
            ItemStack existingItem = this.craftMatrix.getStackInSlot(i);
            ItemStack replacementItem = remainingItems.get(i);

            if (!existingItem.isEmpty())
            {
                this.craftMatrix.decrStackSize(i, 1);
                existingItem = this.craftMatrix.getStackInSlot(i);
            }

            // check for replacement items that are not the same as before (like water bucket -> bucket)
            // we want to move those items to the internal inventory or the player inventory if possible
            // to allow mass crafting things like cakes
            if(!replacementItem.isEmpty())
            {
                // check if the replacement item is the same as before, if not: apply replacement logic
                // i.e. Hammer to craft plates (immersive engineering), magic stone to craft magic seeds (magical crops)
                if(!replacementItem.isItemEqual(existingItem) || (replacementItem.isItemStackDamageable() && replacementItem.isItemDamaged()))
                {
                    // replacement item was changed, this might be a bucket or something similar
                    ItemStack remaining = ItemHandlerHelper.insertItemStacked(this.inventory, replacementItem, false);
                    if(remaining.isEmpty())
                    {
                        replacementItem = ItemStack.EMPTY;
                    }
                    else
                    {
                        // we dont need to check if insertion into the player inventory was successful or not
                        // the method reduces the itemstack accordingly, even if it only adds the item partially
                        // therefor the logic afterwards will add the item to the craft matrix if addItemToInv fails
                        player.addItemStackToInventory(remaining);
                    }

                }
            }

            if (!replacementItem.isEmpty())
            {
                if (existingItem.isEmpty())
                {
                    this.craftMatrix.setInventorySlotContents(i, replacementItem);
                }
                else if (ItemStack.areItemsEqual(existingItem, replacementItem) && ItemStack.areItemStackTagsEqual(existingItem, replacementItem))
                {
                    replacementItem.grow(existingItem.getCount());
                    this.craftMatrix.setInventorySlotContents(i, replacementItem);
                }
                else if (!this.player.inventory.addItemStackToInventory(replacementItem))
                {
                    this.player.dropItem(replacementItem, false);
                }
            }
            else
            {
                if(!dummyCraftFailed && existingItem.isEmpty())
                {
                    boolean found = false;
                    for(int l = 0; l < this.inventory.getSlots(); l++)
                    {
                        ItemStack intInvStack = this.inventory.extractItem(l, 1, true);
                        this.dummyMatrix.setInventorySlotContents(i, intInvStack);
                        if(recipe.matches(this.dummyMatrix, player.getEntityWorld()))
                        {
                            found = true;
                            ItemStack craftMatrixReplacementItem = this.inventory.extractItem(l, 1, false);
                            this.craftMatrix.setInventorySlotContents(i, craftMatrixReplacementItem);
                            break;
                        }
                    }
                    dummyCraftFailed = !found;
                }
            }
        }

        return stack;
    }
}
