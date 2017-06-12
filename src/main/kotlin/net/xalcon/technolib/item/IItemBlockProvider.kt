package net.xalcon.technolib.item

import net.minecraft.item.ItemBlock

interface IItemBlockProvider
{
    /**
     * Create a new ItemBlock instance. Doesnt need to set the registry name, this is done by the caller
     */
    fun createItemBlock(): ItemBlock
    fun hasItemBlock(): Boolean
}