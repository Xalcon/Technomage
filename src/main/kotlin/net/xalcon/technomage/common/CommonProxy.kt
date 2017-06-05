package net.xalcon.technomage.common

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

abstract class CommonProxy
{
    open fun preInit(event:FMLPreInitializationEvent)
    {
    }
    open fun init(event:FMLInitializationEvent) { }
    open fun postInit(event:FMLInitializationEvent) { }
}