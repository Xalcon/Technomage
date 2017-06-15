package net.xalcon.technomage

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