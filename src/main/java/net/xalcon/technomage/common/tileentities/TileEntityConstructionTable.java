package net.xalcon.technomage.common.tileentities;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityConstructionTable extends TileEntityTMBase
{
    private ItemStackHandler inventory = new ItemStackHandler(18);
    private ItemStackHandler craftMatrix = new ItemStackHandler(9);
    private InventoryCraftResult output = new InventoryCraftResult();

    public IItemHandler getInventory() { return this.inventory; }
    public IItemHandler getMatrix() { return this.craftMatrix; }
    public InventoryCraftResult getCraftResult() { return this.output; }
}
