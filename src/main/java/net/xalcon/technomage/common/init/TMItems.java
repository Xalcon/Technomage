package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.items.ItemCursedClawWand;
import net.xalcon.technomage.common.items.ItemImbuedShard;
import net.xalcon.technomage.common.items.ItemLeystoneWand;
import net.xalcon.technomage.common.items.ItemTechnonomicon;
import net.xalcon.technomage.lib.client.events.ColorRegistrationEvent;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMItems
{
    private static Item[] items;

    @GameRegistry.ObjectHolder("technonomicon")
    private final static ItemTechnonomicon technonomicon = null;

    @GameRegistry.ObjectHolder("leystone_wand")
    private final static ItemLeystoneWand leystoneWand = null;

    @GameRegistry.ObjectHolder("cursed_claw_wand")
    private final static ItemCursedClawWand cursedClawWand = null;

    @GameRegistry.ObjectHolder("imbued_shard")
    private final static ItemImbuedShard imbuedShard = null;

    @SuppressWarnings("ConstantConditions")
    public static ItemLeystoneWand leystoneWand() { return leystoneWand; }

    @SuppressWarnings("ConstantConditions")
    public static ItemTechnonomicon technonomicon() { return technonomicon; }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        items = Arrays.stream(TMItems.class.getDeclaredFields())
            .filter(field -> field.getAnnotation(GameRegistry.ObjectHolder.class) != null)
            .filter(field -> Item.class.isAssignableFrom(field.getType()))
            .map(field ->
            {
                Item item = ClassUtils.create(field);
                String name = field.getAnnotation(GameRegistry.ObjectHolder.class).value();
                if(item == null)
                    throw new RuntimeException("Unable to create item instance for " + name);

                item.setRegistryName(name);
                item.setUnlocalizedName(Technomage.MOD_ID + "." + name);
                item.setCreativeTab(CreativeTabsTechnomage.tabMain);
                return item;
            })
            .filter(Objects::nonNull)
            .toArray(Item[]::new);

        event.getRegistry().registerAll(items);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        for(Item item: items)
        {
            if(item instanceof IItemModelRegisterHandler)
            {
                ((IItemModelRegisterHandler) item).registerItemModels(item);
            }
            else
            {
                ResourceLocation rl = item.getRegistryName();
                assert rl != null;
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(rl, "inventory"));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterColors(ColorRegistrationEvent event)
    {
        for(Item item: items)
        {
            if(item instanceof IItemColor)
                event.getItemColors().registerItemColorHandler((IItemColor)item, item);
        }
    }
}
