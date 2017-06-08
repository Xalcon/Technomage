package net.xalcon.technolib.item

import net.minecraft.item.Item

interface IItemModelRegisterHandler
{
    fun registerItemModels(item: Item)
}