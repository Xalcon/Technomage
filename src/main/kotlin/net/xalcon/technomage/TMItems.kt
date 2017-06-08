package net.xalcon.technomage

import net.xalcon.technomage.Technomage
import net.xalcon.technomage.common.items.ItemTechnonomicon

object TMItems
{
    val technonomicon: ItemTechnonomicon = ItemTechnonomicon()

    fun init()
    {
        Technomage.Proxy.register(technonomicon)
    }
}