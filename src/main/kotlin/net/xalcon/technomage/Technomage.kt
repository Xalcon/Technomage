package net.xalcon.technomage

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.xalcon.technomage.common.CommonProxy

@Mod
(
        modid = Technomage.MOD_ID,
        name = Technomage.NAME,
        version = Technomage.VERSION,
        acceptedMinecraftVersions = Technomage.MC_VERSION,
        dependencies = Technomage.DEPENDENCIES
)
object Technomage
{
    const val MOD_ID = "technomage"
    const val NAME = "Technomage"
    const val GROUP = "net.xalcon." + MOD_ID
    const val VERSION = "@VERSION@"
    const val MC_VERSION = "1.11.2"
    const val DEPENDENCIES = "required-after:forge;required-after:forgelin@[1.4.0,)"

    @SidedProxy(clientSide = GROUP + ".client.ClientProxy", serverSide = GROUP + ".server.ServerProxy")
    lateinit var Proxy : CommonProxy

    @Mod.EventHandler
    fun onConstruction(event:FMLConstructionEvent)
    {
    }

    @Mod.EventHandler
    fun onPreInit(event:FMLPreInitializationEvent)
    {
        Proxy.preInit(event)
    }

    @Mod.EventHandler
    fun onInit(event:FMLInitializationEvent)
    {
    }

    @Mod.EventHandler
    fun onPostInit(event:FMLPostInitializationEvent)
    {
    }

    @JvmStatic
    @Mod.InstanceFactory
    fun createInstance() = Technomage
}