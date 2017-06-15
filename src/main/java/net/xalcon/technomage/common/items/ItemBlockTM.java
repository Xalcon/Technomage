package net.xalcon.technomage.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class ItemBlockTM<T extends Block & IItemModelRegisterHandler> extends ItemBlock implements IItemModelRegisterHandler
{
    public ItemBlockTM(T block)
    {
        super(block);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ((IItemModelRegisterHandler)this.getBlock()).registerItemModels(item);
    }
}
