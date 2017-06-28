package net.xalcon.technomage.common.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.client.renderer.entity.EntityBoomerangRenderer;
import net.xalcon.technomage.common.entities.EntityBoomerang;

@Mod.EventBusSubscriber
public class TMEntities
{
    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Technomage.MOD_ID, "boomerang"),
            EntityBoomerang.class, "boomerang", 0, Technomage.getInstance(), 64, 1, true);
    }

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, EntityBoomerangRenderer::new);
    }
}
