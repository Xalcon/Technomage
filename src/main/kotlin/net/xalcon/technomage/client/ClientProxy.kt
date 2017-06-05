package net.xalcon.technomage.client

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.xalcon.technomage.common.CommonProxy

@SideOnly(Side.CLIENT)
class ClientProxy : CommonProxy()
{
    override fun preInit(event: FMLPreInitializationEvent)
    {
        super.preInit(event)
    }
}