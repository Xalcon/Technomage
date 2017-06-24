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
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.*;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMBlocks
{
    private static Block[] blocks;

    @GameRegistry.ObjectHolder(BlockBrickFurnace.internalName)
    public final static BlockBrickFurnace brickFurnace = new BlockBrickFurnace();

    @GameRegistry.ObjectHolder(BlockAlchemicalCauldron.INTERNAL_NAME)
    public final static BlockAlchemicalCauldron alchemicalCauldron = new BlockAlchemicalCauldron();

    @GameRegistry.ObjectHolder(BlockPedestal.INTERNAL_NAME)
    public final static BlockPedestal pedestal = new BlockPedestal();

    @GameRegistry.ObjectHolder(BlockAmalgamationAltar.INTERNAL_NAME)
    public final static BlockAmalgamationAltar amalgamationAltar = new BlockAmalgamationAltar();

    @GameRegistry.ObjectHolder(BlockConstructionTable.INTERNAL_NAME)
    public final static BlockConstructionTable constructionTable = new BlockConstructionTable();

    @GameRegistry.ObjectHolder(BlockImbuedOre.INTERNAL_NAME)
    public final static BlockImbuedOre imbuedOre = new BlockImbuedOre();

    @GameRegistry.ObjectHolder(BlockPlant.INTERNAL_NAME)
    public final static BlockPlant plant = new BlockPlant();

    @GameRegistry.ObjectHolder(BlockTMLog.INTERNAL_NAME)
    public final static BlockTMLog log = new BlockTMLog();

    @GameRegistry.ObjectHolder(BlockTMPlanks.INTERNAL_NAME)
    public final static BlockTMPlanks planks = new BlockTMPlanks();

    @GameRegistry.ObjectHolder(BlockTMWoodSlab.INTERNAL_NAME)
    public final static BlockTMWoodSlab woodSlab = new BlockTMWoodSlab();

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "_elder")
    public final static BlockTMWoodStair elderWoodStairs = new BlockTMWoodStair(TMTreeType.ELDER);

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "_ley")
    public final static BlockTMWoodStair leyWoodStairs = new BlockTMWoodStair(TMTreeType.LEY);

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "_fel")
    public final static BlockTMWoodStair felWoodStairs = new BlockTMWoodStair(TMTreeType.FEL);

    @GameRegistry.ObjectHolder(BlockTMWoodStair.INTERNAL_NAME_PREFIX + "_bamboo")
    public final static BlockTMWoodStair bambooWoodStairs = new BlockTMWoodStair(TMTreeType.BAMBOO);

    @GameRegistry.ObjectHolder(BlockTMLeaves.INTERNAL_NAME)
    public final static BlockTMLeaves leaves = new BlockTMLeaves();

    static
    {
        MultiblockRegistry.register(new MultiblockBrickFurnace());
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        blocks = Arrays.stream(TMBlocks.class.getFields())
            .filter(f -> f.getAnnotation(GameRegistry.ObjectHolder.class) != null)
            .filter(f -> Block.class.isAssignableFrom(f.getType()))
            //.map(ClassUtils::create)
            .map(ClassUtils::getOrNull)
            .filter(Objects::nonNull)
            .toArray(Block[]::new);

        event.getRegistry().registerAll(blocks);

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
        event.getRegistry().registerAll(
                Arrays.stream(blocks)
                    .filter(b -> b instanceof IItemBlockProvider && ((IItemBlockProvider) b).hasItemBlock())
                    .map(block -> {
                        ResourceLocation loc = block.getRegistryName();
                        assert loc != null;
                        return ((IItemBlockProvider)block).createItemBlock().setRegistryName(loc);
                    })
                    .toArray(Item[]::new)
        );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        Arrays.stream(blocks)
            .filter(b -> b instanceof IItemBlockProvider && ((IItemBlockProvider) b).hasItemBlock())
            .forEach(block -> ((IItemBlockProvider) block).registerItemModels(Item.getItemFromBlock(block)));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterColors(ColorRegistrationEvent event)
    {
        for(Block block: blocks)
        {
            if(block instanceof IBlockColor)
                event.getBlockColors().registerBlockColorHandler((IBlockColor)block, block);
            if(block instanceof IItemColor)
                event.getItemColors().registerItemColorHandler((IItemColor)block, block);
        }
    }
}
