package net.xalcon.technomage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.xalcon.technomage.common.CommonProxy;
import net.xalcon.technomage.common.GuiHandlerTechnomage;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.init.TMItems;
import net.xalcon.technomage.common.init.TMRecipes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod
(
        modid = Technomage.MOD_ID,
        name = Technomage.NAME,
        version = Technomage.VERSION,
        acceptedMinecraftVersions = Technomage.MC_VERSION,
        dependencies = Technomage.DEPENDENCIES
)
public class Technomage
{
    public final static String MOD_ID = "technomage";
    public final static String NAME = "Technomage";
    public final static String GROUP = "net.xalcon." + MOD_ID;
    public final static String VERSION = "@VERSION@";
    public final static String MC_VERSION = "1.12";
    public final static String DEPENDENCIES = "required-after:forge@[14.21.0.2328,)";

    private static Technomage instance = new Technomage();

    @SidedProxy(clientSide = GROUP + ".client.ClientProxy", serverSide = GROUP + ".server.ServerProxy")
    public static CommonProxy Proxy;

    public final static Logger Log = LogManager.getLogger(MOD_ID);

    @Mod.EventHandler
    public static void onConstruction(FMLConstructionEvent event)
    {
    }

    @Mod.EventHandler
    public static void onPreInit(FMLPreInitializationEvent event)
    {
        Proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerTechnomage());
    }

    @Mod.EventHandler
    public static void onInit(FMLInitializationEvent event)
    {
        Proxy.init(event);
        TMRecipes.init();
    }

    @Mod.EventHandler
    public static void onPostInit(FMLPostInitializationEvent event)
    {
        Proxy.postInit(event);
    }

    @Mod.InstanceFactory
    public static Technomage getInstance()
    {
        return instance;
    }
}
