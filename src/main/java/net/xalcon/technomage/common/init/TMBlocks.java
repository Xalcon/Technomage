package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace;
import net.xalcon.technomage.common.multiblocks.MultiblockBrickFurnace;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.utils.ClassUtils;

import java.util.*;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Technomage.MOD_ID)
public class TMBlocks
{
    private final static Block[] blocks;

    @GameRegistry.ObjectHolder(BlockBrickFurnace.internalName)
    public final static BlockBrickFurnace brickFurnace = null;

    @GameRegistry.ObjectHolder(BlockAlchemicalCauldron.INTERNAL_NAME)
    public final static BlockAlchemicalCauldron alchemicalCauldron = null;

    @GameRegistry.ObjectHolder(BlockPedestal.INTERNAL_NAME)
    public final static BlockPedestal pedestal = null;

    @GameRegistry.ObjectHolder(BlockAmalgamationAltar.INTERNAL_NAME)
    public final static BlockAmalgamationAltar amalgamationAltar = null;

    @GameRegistry.ObjectHolder(BlockConstructionTable.INTERNAL_NAME)
    public final static BlockConstructionTable constructionTable = null;

    static
    {
        blocks = Arrays.stream(TMBlocks.class.getFields())
            .filter(f -> f.getAnnotation(GameRegistry.ObjectHolder.class) != null)
            .map(ClassUtils::create)
            .filter(Objects::nonNull)
            .toArray(Block[]::new);

        MultiblockRegistry.register(new MultiblockBrickFurnace());
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(blocks);

        Arrays.stream(blocks)
                .filter(b -> b instanceof BlockTMTileProvider)
                .forEach(block -> ((BlockTMTileProvider) block).getTileEntityClasses()
                        .forEach((s, t) -> GameRegistry.registerTileEntity(t, s)));
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(
                Arrays.stream(blocks)
                    .filter(b -> b instanceof IItemBlockProvider && ((IItemBlockProvider) b).hasItemBlock())
                    .map(block -> ((IItemBlockProvider)block).createItemBlock().setRegistryName(block.getRegistryName()))
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
}
