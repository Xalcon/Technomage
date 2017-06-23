package net.xalcon.technomage.common.container;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

import javax.annotation.Nullable;

public class ContainerConstructionTable extends ContainerTM
{
    private final EntityPlayer player;
    private final TileEntityConstructionTable tile;
    private final IItemHandler inventory;
    private final ItemStackHandler craftMatrix;
    private final InventoryCraftingProxy craftMatrixProxy;
    private final InventoryCraftResult craftResult;

    private SlotCraftingEx craftingResultSlot;
    private int craftResultSlotId;
    private int internalInventoryStartIndex;
    private int internalInventoryEndIndex;
    private int craftMatrixStartIndex;
    private int craftMatrixEndIndex;

    private IRecipe cachedRecipe;

    public ContainerConstructionTable(EntityPlayer player, TileEntityConstructionTable tile)
    {
        this.player = player;
        this.tile = tile;

        this.inventory = this.tile.getInventory();
        this.craftMatrix = this.tile.getMatrix();
        this.craftMatrixProxy = new InventoryCraftingProxy<>(this, this.craftMatrix);
        this.craftResult = this.tile.getCraftResult();

        this.bindPlayerInventory(player);
        this.bindContainerSlots();
    }

    @Override
    public int getPlayerInventoryOffset() { return 142; }

    @Nullable
    public IRecipe getCachedRecipe() { return this.cachedRecipe; }

    public void bindContainerSlots()
    {
        this.internalInventoryStartIndex = this.inventorySlots.size();
        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new SlotItemHandler(this.inventory, i, 8 + i * 18, 90));
            this.addSlotToContainer(new SlotItemHandler(this.inventory, i + 9, 8 + i * 18, 108));
        }
        this.internalInventoryEndIndex = this.inventorySlots.size() - 1;

        this.craftMatrixStartIndex = this.inventorySlots.size();
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                int index = col + row * 3;
                //this.addSlotToContainer(new SlotItemHandler(this.craftMatrix, index, 48 + col * 18, 18 + row * 18));
                this.addSlotToContainer(new Slot(this.craftMatrixProxy, index, 48 + col * 18, 18 + row * 18));
            }
        }
        this.craftMatrixEndIndex = this.inventorySlots.size() - 1;

        this.craftingResultSlot = (SlotCraftingEx)this.addSlotToContainer(new SlotCraftingEx<>(this, this.player, this.craftMatrixProxy, this.craftResult, this.inventory, 0, 143, 36));
        this.craftResultSlotId = this.craftingResultSlot.slotNumber;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack outStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            outStack = slotStack.copy();

            if (index == this.craftResultSlotId)
            {
                // From craftslot to player inventory
                slotStack.getItem().onCreated(slotStack, player.getEntityWorld(), player);

                if (!this.mergeItemStack(slotStack, this.playerInventoryOffsetStart, this.playerInventoryOffsetEnd + 1, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, outStack);
            }
            else if (index >= this.playerInventoryOffsetStart && index <= this.playerInventoryOffsetEnd)
            {
                // from player inventory to internal inventory
                if (!this.mergeItemStack(slotStack, this.internalInventoryStartIndex, this.internalInventoryEndIndex + 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= this.internalInventoryStartIndex && index <= this.internalInventoryEndIndex)
            {
                // from internal inventory to player inventory
                if (!this.mergeItemStack(slotStack, this.playerInventoryOffsetStart, this.playerInventoryOffsetEnd + 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= this.craftMatrixStartIndex && index <= this.craftMatrixEndIndex)
            {
                // from craftmatrix
                if (!this.mergeItemStack(slotStack, this.internalInventoryStartIndex, this.internalInventoryEndIndex + 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == outStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(player, slotStack);

            if (index == 0)
            {
                player.dropItem(itemstack2, false);
            }
        }

        return outStack;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.updateCraftOutput(this.player.getEntityWorld(), this.player, this.craftMatrixProxy, this.craftResult);
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }

    public void updateCraftOutput(World world, EntityPlayer player, InventoryCrafting inventoryCrafting, InventoryCraftResult craftResult)
    {
        //if (!world.isRemote)
        {
            //EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
            RecipeBook book = world.isRemote ? ((EntityPlayerSP)player).getRecipeBook() : ((EntityPlayerMP)player).getRecipeBook();
            ItemStack itemstack = ItemStack.EMPTY;
            IRecipe recipe = this.cachedRecipe;
            if(recipe == null || !recipe.matches(inventoryCrafting, world))
            {
                recipe = CraftingManager.findMatchingRecipe(inventoryCrafting, world);
            }

            if (recipe != null && (recipe.isHidden() || !world.getGameRules().getBoolean("doLimitedCrafting") || book.containsRecipe(recipe)))
            {
                craftResult.setRecipeUsed(recipe);
                itemstack = recipe.getCraftingResult(inventoryCrafting);
            }

            craftResult.setInventorySlotContents(this.craftResultSlotId, itemstack);
            if(!world.isRemote)
                ((EntityPlayerMP)player).connection.sendPacket(new SPacketSetSlot(this.windowId, this.craftResultSlotId, itemstack));

            this.cachedRecipe = recipe;
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        if (clickTypeIn == ClickType.QUICK_MOVE && slotId == this.craftResultSlotId)
        {
            if (!this.craftingResultSlot.canTakeStack(player))
            {
                return ItemStack.EMPTY;
            }

            ItemStack craftOutput = this.craftingResultSlot.getStack();
            int maxCraftAmount = craftOutput.getMaxStackSize();
            int craftedAmount = 0;

            for (ItemStack returnStack = this.transferStackInSlot(player, slotId);
                 !returnStack.isEmpty() && ItemStack.areItemsEqual(this.craftingResultSlot.getStack(), returnStack);
                 returnStack = this.transferStackInSlot(player, slotId))
            {
                itemstack = returnStack.copy();
                craftedAmount += itemstack.getCount();
                if(craftedAmount >= maxCraftAmount)
                    break;
            }
        }
        else
            return super.slotClick(slotId, dragType, clickTypeIn, player);

        return itemstack;
    }
}
