package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

public class ContainerConstructionTable extends ContainerTM
{
    private final EntityPlayer player;
    private final TileEntityConstructionTable tile;
    private final IItemHandler inventory;
    private final ItemStackHandler craftMatrix;
    private final InventoryCraftingProxy craftMatrixProxy;
    private final InventoryCraftResult craftResult;

    private int craftResultSlotId;

    private IRecipe lastRecipe;

    public ContainerConstructionTable(EntityPlayer player, TileEntityConstructionTable tile)
    {
        this.player = player;
        this.tile = tile;

        this.inventory = this.tile.getInventory();
        this.craftMatrix = this.tile.getMatrix();
        this.craftMatrixProxy = new InventoryCraftingProxy(this);
        this.craftResult = this.tile.getCraftResult();

        this.bindPlayerInventory(player);
        this.bindContainerSlots();
    }

    @Override
    public int getPlayerInventoryOffset() { return 142; }

    public void bindContainerSlots()
    {

        for(int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new SlotItemHandler(this.inventory, i, 8 + i * 18, 90));
            this.addSlotToContainer(new SlotItemHandler(this.inventory, i + 9, 8 + i * 18, 108));
        }

        for(int col = 0; col < 3; col++)
        {
            for(int row = 0; row < 3; row++)
            {
                int index = 3 * col + row;
                this.addSlotToContainer(new Slot(this.craftMatrixProxy, index, 48 + col * 18, 18 + row * 18));
            }
        }

        this.craftResultSlotId = this.addSlotToContainer(new Slot(this.craftResult, 0, 143, 36)).slotNumber;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.updateCraftOutput(this.player.getEntityWorld(), this.player, this.craftMatrixProxy, this.craftResult);
        System.out.println("MATRIX CHANGED");
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
        if (!world.isRemote)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
            ItemStack itemstack = ItemStack.EMPTY;
            IRecipe recipe = null; //this.lastRecipe;
            if(recipe == null || !recipe.matches(inventoryCrafting, world))
            {
                recipe = CraftingManager.findMatchingRecipe(inventoryCrafting, world);
            }

            if (recipe != null && (recipe.isHidden() || !world.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().containsRecipe(recipe)))
            {
                craftResult.func_193056_a(recipe);
                itemstack = recipe.getCraftingResult(inventoryCrafting);
            }

            craftResult.setInventorySlotContents(this.craftResultSlotId, itemstack);
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, this.craftResultSlotId, itemstack));

            this.lastRecipe = recipe;
        }
    }
}
