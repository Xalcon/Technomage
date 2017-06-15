package net.xalcon.technomage.lib.item;

import net.minecraft.item.ItemBlock;

public interface IItemBlockProvider
{
    ItemBlock createItemBlock();
    boolean hasItemBlock();
}
