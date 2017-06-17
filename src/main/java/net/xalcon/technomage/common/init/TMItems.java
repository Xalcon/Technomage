package net.xalcon.technomage.common.init;

import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.items.ItemLeystoneWand;
import net.xalcon.technomage.common.items.ItemTechnonomicon;

public class TMItems
{
    public final static ItemTechnonomicon technonomicon = new ItemTechnonomicon();
    public final static ItemLeystoneWand leystoneWand = new ItemLeystoneWand();

    public static void init()
    {
        Technomage.Proxy.register(technonomicon);
        Technomage.Proxy.register(leystoneWand);
    }
}
