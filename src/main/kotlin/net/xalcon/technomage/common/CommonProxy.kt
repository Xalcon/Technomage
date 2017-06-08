package net.xalcon.technomage.common

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.xalcon.technolib.item.IItemBlockProvider

abstract class CommonProxy
{
    open fun preInit(event:FMLPreInitializationEvent)
    {
    }

    open fun init(event:FMLInitializationEvent)
    {
    }

    open fun postInit(event:FMLInitializationEvent)
    {
    }

    open fun <T : Block> register(block:T):T
    {
        GameRegistry.register(block)
        if(block is IItemBlockProvider)
        {
            val itemBlock = block.createItemBlock()
            itemBlock.registryName = block.registryName
            register(itemBlock)
        }
        return block
    }

    open fun <T : Item> register(item:T):T
    {
        GameRegistry.register(item)
        return item
    }
}