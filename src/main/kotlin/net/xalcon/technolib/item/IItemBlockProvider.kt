package net.xalcon.technolib.item

interface IItemBlockProvider
{
    /**
     * Create a new ItemBlock instance. Doesnt need to set the registry name, this is done by the caller
     */
    fun createItemBlock(): net.minecraft.item.ItemBlock
}