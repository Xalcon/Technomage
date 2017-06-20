package net.xalcon.technomage.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.items.ItemCursedClawWand;
import net.xalcon.technomage.common.items.ItemLeystoneWand;
import net.xalcon.technomage.common.items.ItemTechnonomicon;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMItems
{
    private static Item[] items;

    @GameRegistry.ObjectHolder(ItemTechnonomicon.INTERNAL_NAME)
    public final static ItemTechnonomicon technonomicon = new ItemTechnonomicon();

    @GameRegistry.ObjectHolder(ItemLeystoneWand.INTERNAL_NAME)
    public final static ItemLeystoneWand leystoneWand = new ItemLeystoneWand();

    @GameRegistry.ObjectHolder(ItemCursedClawWand.INTERNAL_NAME)
    public final static ItemCursedClawWand cursedClawWand = new ItemCursedClawWand();

    static
    {

    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        items = Arrays.stream(TMItems.class.getFields())
            .filter(field -> field.getAnnotation(GameRegistry.ObjectHolder.class) != null)
            .filter(field -> Item.class.isAssignableFrom(field.getType()))
            .map(ClassUtils::create)
            .filter(Objects::nonNull)
            .toArray(Item[]::new);

        event.getRegistry().registerAll(items);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        Arrays.stream(items)
            .filter(item -> item instanceof IItemModelRegisterHandler)
            .forEach(item -> ((IItemModelRegisterHandler) item).registerItemModels(item));
    }
}
