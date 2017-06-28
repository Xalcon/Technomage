package net.xalcon.technomage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Events
{
    //@SubscribeEvent
    public static void onEvent(LivingDeathEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer)
        {
            event.getEntityLiving().setHealth(2);
            event.setCanceled(true);
        }
    }

    //@SubscribeEvent
    public static void onEvent(EntityJoinWorldEvent event)
    {
        System.out.println("JOIN:" + event.getEntity() + ", Remote? " + event.getWorld().isRemote);
    }
}
