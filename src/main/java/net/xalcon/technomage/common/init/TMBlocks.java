package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;
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
import net.xalcon.technomage.lib.utils.AutoInstantiate;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMBlocks
{
    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockBrickFurnace.internalName)
    private final static BlockBrickFurnace brickFurnace = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockAlchemicalCauldron.INTERNAL_NAME)
    private final static BlockAlchemicalCauldron alchemicalCauldron = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockPedestal.INTERNAL_NAME)
    private final static BlockPedestal pedestal = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockAmalgamationAltar.INTERNAL_NAME)
    private final static BlockAmalgamationAltar amalgamationAltar = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockConstructionTable.INTERNAL_NAME)
    private final static BlockConstructionTable constructionTable = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockImbuedOre.INTERNAL_NAME)
    private final static BlockImbuedOre imbuedOre = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockPlant.INTERNAL_NAME)
    private final static BlockPlant plant = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockTMLog.INTERNAL_NAME)
    private final static BlockTMLog log = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockTMPlanks.INTERNAL_NAME)
    private final static BlockTMPlanks planks = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockTMWoodSlab.INTERNAL_NAME)
    private final static BlockTMWoodSlab woodSlab = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "elder")
    private final static BlockTMWoodStair elderWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "ley")
    private final static BlockTMWoodStair leyWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "fel")
    private final static BlockTMWoodStair felWoodStairs = null;

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "bamboo")
    private final static BlockTMWoodStair bambooWoodStairs = null;

    @AutoInstantiate
    @GameRegistry.ObjectHolder(BlockTMLeaves.INTERNAL_NAME)
    private final static BlockTMLeaves leaves = null;

    static
    {
        MultiblockRegistry.register(new MultiblockBrickFurnace());
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        Block[] blocks = Arrays.stream(TMBlocks.class.getDeclaredFields())
            .filter(f -> f.getAnnotation(GameRegistry.ObjectHolder.class) != null && f.getAnnotation(AutoInstantiate.class) != null)
            .filter(f -> Block.class.isAssignableFrom(f.getType()))
            .map(ClassUtils::create)
            .filter(Objects::nonNull)
            .toArray(Block[]::new);

        event.getRegistry().registerAll(blocks);

        BlockTMPlanks planks = (BlockTMPlanks)Arrays.stream(blocks).filter(b -> b instanceof BlockTMPlanks)
            .findFirst().orElseThrow(() -> new RuntimeException("This shouldnt happen :x"));

        for(TMTreeType type : TMTreeType.values())
            event.getRegistry().register(new BlockTMWoodStair(type, planks));

        Arrays.stream(blocks)
                .filter(b -> b instanceof BlockTMTileProvider)
                .map(block -> (BlockTMTileProvider)block)
                .forEach(block ->
                {
                    ResourceLocation loc = block.getRegistryName();
                    assert loc != null;
                    Class<? extends TileEntity> clazz = block.getTileEntityClass();
                    if(clazz != null)
                        GameRegistry.registerTileEntity(clazz, loc.toString());
                });
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        for (Block block : getBlocks())
        {
            if(block instanceof IItemBlockProvider && ((IItemBlockProvider) block).hasItemBlock())
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
            if(block instanceof IItemBlockProvider && ((IItemBlockProvider) block).hasItemBlock())
            {
                ((IItemBlockProvider) block).registerItemModels(Item.getItemFromBlock(block));
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
