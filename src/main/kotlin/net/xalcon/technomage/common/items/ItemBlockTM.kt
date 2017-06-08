package net.xalcon.technomage.common.items

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.xalcon.technolib.item.IItemModelRegisterHandler

class ItemBlockTM<T>(block: T) : ItemBlock(block), IItemModelRegisterHandler where T : Block, T : IItemModelRegisterHandler
{
    private val registerHandler: IItemModelRegisterHandler;

    init
    {
        this.registerHandler = block
    }

    override fun registerItemModels(item: Item)
    {
        this.registerHandler.registerItemModels(item)
    }
}