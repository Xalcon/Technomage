package net.xalcon.technomage.lib.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface IItemBlockProvider extends IItemModelRegisterHandler
{
    default ItemBlock createItemBlock() { return new ItemBlock((Block) this); }
}
