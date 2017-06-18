package net.xalcon.technomage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events
{
    //@SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer)
        {
            event.getEntityLiving().setHealth(2);
            event.setCanceled(true);
        }
    }
}
