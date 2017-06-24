package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Items;
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
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.*;
import net.xalcon.technomage.common.blocks.crafting.BlockAlchemicalCauldron;
import net.xalcon.technomage.common.blocks.crafting.BlockAmalgamationAltar;
import net.xalcon.technomage.common.blocks.crafting.BlockConstructionTable;
import net.xalcon.technomage.common.blocks.crafting.BlockPedestal;
import net.xalcon.technomage.common.blocks.decorative.BlockTMWoodSlab;
import net.xalcon.technomage.common.blocks.decorative.BlockTMWoodStair;
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockImbuedOre;
import net.xalcon.technomage.common.blocks.world.BlockPlant;
import net.xalcon.technomage.common.blocks.world.BlockTMLeaves;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;
import net.xalcon.technomage.common.blocks.decorative.BlockTMPlanks;
import net.xalcon.technomage.common.multiblocks.MultiblockBrickFurnace;
import net.xalcon.technomage.lib.client.events.ColorRegistrationEvent;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMBlocks
{
    @GameRegistry.ObjectHolder(BlockBrickFurnace.internalName)
    private final static BlockBrickFurnace brickFurnace = null;

    @GameRegistry.ObjectHolder(BlockAlchemicalCauldron.INTERNAL_NAME)
    private final static BlockAlchemicalCauldron alchemicalCauldron = null;

    @GameRegistry.ObjectHolder(BlockPedestal.INTERNAL_NAME)
    private final static BlockPedestal pedestal = null;

    @GameRegistry.ObjectHolder(BlockAmalgamationAltar.INTERNAL_NAME)
    private final static BlockAmalgamationAltar amalgamationAltar = null;

    @GameRegistry.ObjectHolder(BlockConstructionTable.INTERNAL_NAME)
    private final static BlockConstructionTable constructionTable = null;

    @GameRegistry.ObjectHolder(BlockImbuedOre.INTERNAL_NAME)
    private final static BlockImbuedOre imbuedOre = null;

    @GameRegistry.ObjectHolder(BlockPlant.INTERNAL_NAME)
    private final static BlockPlant plant = null;

    @GameRegistry.ObjectHolder(BlockTMLog.INTERNAL_NAME)
    private final static BlockTMLog log = null;

    @GameRegistry.ObjectHolder(BlockTMPlanks.INTERNAL_NAME)
    private final static BlockTMPlanks planks = null;

    @GameRegistry.ObjectHolder(BlockTMWoodSlab.INTERNAL_NAME)
    private final static BlockTMWoodSlab woodSlab = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_ELDER)
    @BlockTMWoodStair.InstanceParameters(TMTreeType.ELDER)
    private final static BlockTMWoodStair elderWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_LEY)
    @BlockTMWoodStair.InstanceParameters(TMTreeType.LEY)
    private final static BlockTMWoodStair leyWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_FEL)
    @BlockTMWoodStair.InstanceParameters(TMTreeType.FEL)
    private final static BlockTMWoodStair felWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_BAMBOO)
    @BlockTMWoodStair.InstanceParameters(TMTreeType.BAMBOO)
    private final static BlockTMWoodStair bambooWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMLeaves.INTERNAL_NAME)
    private final static BlockTMLeaves leaves = null;


    //region getter Methods

    @SuppressWarnings("ConstantConditions")
    public static BlockBrickFurnace brickFurnace()
    {
        return brickFurnace;
    }

    //endregion

    static
    {
        MultiblockRegistry.register(new MultiblockBrickFurnace());
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        for(Field field: TMBlocks.class.getDeclaredFields())
        {
            if(!Block.class.isAssignableFrom(field.getType()) || field.getAnnotation(GameRegistry.ObjectHolder.class) == null)
                continue;

            Block block = ClassUtils.create(field);
            String internalName = field.getAnnotation(GameRegistry.ObjectHolder.class).value();
            block.setRegistryName(internalName);
            block.setUnlocalizedName(Technomage.MOD_ID + "." + internalName);
            block.setCreativeTab(CreativeTabsTechnomage.tabMain);
            event.getRegistry().register(block);

            if(block instanceof ITechnomageTileEntityProvider)
                ((ITechnomageTileEntityProvider) block).registerTileEntities();
        }
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        for (Block block : getBlocks())
        {
            if(block instanceof IItemBlockProvider)
            {
                ResourceLocation loc = block.getRegistryName();
                assert loc != null;
                event.getRegistry().register(((IItemBlockProvider)block).createItemBlock().setRegistryName(loc));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        for(Block block: getBlocks())
        {
            Item item = Item.getItemFromBlock(block);
            if (item == Items.AIR) continue;
            if(block instanceof IItemBlockProvider)
            {
                ((IItemBlockProvider) block).registerItemModels(Item.getItemFromBlock(block));
            }
            else
            {
                ResourceLocation loc = block.getRegistryName();
                assert loc != null;
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "inventory"));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterColors(ColorRegistrationEvent event)
    {
        for(Block block: getBlocks())
        {
            if(block instanceof IBlockColor)
                event.getBlockColors().registerBlockColorHandler((IBlockColor)block, block);
            if(block instanceof IItemColor)
                event.getItemColors().registerItemColorHandler((IItemColor)block, block);
        }
    }

    private static List<Block> getBlocks()
    {
        return Arrays.stream(TMBlocks.class.getDeclaredFields())
            .filter(f -> f.getAnnotation(GameRegistry.ObjectHolder.class) != null)
            .filter(f -> Block.class.isAssignableFrom(f.getType()))
            .map(ClassUtils::<Block>getOrNull)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
