package net.xalcon.technomage.lib.item;

import net.minecraft.item.ItemBlock;

public interface IItemBlockProvider extends IItemModelRegisterHandler
{
    ItemBlock createItemBlock();
    boolean hasItemBlock();
}
