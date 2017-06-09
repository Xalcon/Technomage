package net.xalcon.technomage

import net.xalcon.technomage.common.items.ItemLeystoneWand
import net.xalcon.technomage.common.items.ItemTechnonomicon

object TMItems
{
    val technonomicon = ItemTechnonomicon()
    val leystoneWand = ItemLeystoneWand()

    fun init()
    {
        Technomage.Proxy.register(technonomicon)
        Technomage.Proxy.register(leystoneWand)
    }
}