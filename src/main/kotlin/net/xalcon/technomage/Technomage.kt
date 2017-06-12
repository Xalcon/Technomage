package net.xalcon.technomage

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.xalcon.technomage.common.CommonProxy
import net.xalcon.technomage.common.TechnomageGuiHandler
import org.apache.logging.log4j.LogManager
import kotlin.experimental.and

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
    const val DEPENDENCIES = "required-after:forge@[13.20.0.2304,);required-after:forgelin@[1.4.0,)"

    @SidedProxy(clientSide = GROUP + ".client.ClientProxy", serverSide = GROUP + ".server.ServerProxy")
    lateinit var Proxy : CommonProxy

    val Log = LogManager.getLogger(MOD_ID)

    val x:Byte = -1
    var y:Int = 0

    @Mod.EventHandler
    fun onConstruction(event:FMLConstructionEvent)
    {
        y = (x and 0xFF.toByte()).toInt()
    }

    @Mod.EventHandler
    fun onPreInit(event:FMLPreInitializationEvent)
    {
        Proxy.preInit(event)
        TMBlocks.init()
        TMItems.init()

        NetworkRegistry.INSTANCE.registerGuiHandler(this, TechnomageGuiHandler)
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