package net.xalcon.technomage.common.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.xalcon.technomage.client.colors.ImbuedShardColor;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.items.*;
import net.xalcon.technomage.common.items.tools.ItemLeylightBow;
import net.xalcon.technomage.common.items.tools.ItemPickaxe;
import net.xalcon.technomage.common.items.tools.ItemSword;
import net.xalcon.technomage.lib.client.events.ColorRegistrationEvent;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.Arrays;

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

    @GameRegistry.ObjectHolder("sword")
    private final static ItemSword sword = null;

    @GameRegistry.ObjectHolder("farstep_feather")
    private final static ItemFarstepFeather farstepFeather = null;

    @GameRegistry.ObjectHolder("boomerang")
    private final static ItemBoomerang boomerang = null;

    @GameRegistry.ObjectHolder(ItemIngot.INTERNAL_NAME)
    private final static ItemIngot ingot = null;

    @GameRegistry.ObjectHolder("arkanium_pickaxe")
    @ItemPickaxe.InstanceParameters(TMMaterial.ARKANIUM)
    private final static ItemPickaxe arkaniumPickaxe = null;

    @GameRegistry.ObjectHolder("leylight_bow")
    private final static ItemLeylightBow leylightBow = null;

    @GameRegistry.ObjectHolder(ItemTranslocationOrb.INTERNAL_NAME)
    private final static ItemTranslocationOrb translocationOrb = null;

    @GameRegistry.ObjectHolder("debug_item")
    private final static DebugItem debugItem = null;

    @SuppressWarnings("ConstantConditions")
    public static ItemLeystoneWand leystoneWand() { return leystoneWand; }

    @SuppressWarnings("ConstantConditions")
    public static ItemTechnonomicon technonomicon() { return technonomicon; }

    @SuppressWarnings("ConstantConditions")
    public static ItemBoomerang boomerang()
    {
        return boomerang;
    }

    @SuppressWarnings("ConstantConditions")
    public static ItemLeylightBow leylightBow() { return leylightBow; }

    @SuppressWarnings("ConstantConditions")
    public static ItemTranslocationOrb translocationOrb()
    {
        return translocationOrb;
    }

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
                item.setRegistryName(name);
                item.setUnlocalizedName(Technomage.MOD_ID + "." + name);
                item.setCreativeTab(CreativeTabsTechnomage.tabMain);
                return item;
            })
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
        event.getItemColors().registerItemColorHandler(ImbuedShardColor.INSTANCE, imbuedShard);
    }

}
