package net.xalcon.technomage.common.tileentities;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityConstructionTable extends TileEntityTMBase
{
    private ItemStackHandler inventory = new ItemStackHandler(18);
    private ItemStackHandler craftMatrix = new ItemStackHandler(9);
    private ItemStackHandler output = new ItemStackHandler(1);

    public IItemHandler getInventory() { return this.inventory; }
    public IItemHandler getMatrix() { return this.craftMatrix; }
    public IItemHandler getOutput() { return this.output; }
}
