package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;
import net.xalcon.technomage.common.blocks.BlockAlchemicalCauldron;
import net.xalcon.technomage.common.blocks.BlockAmalgamationAltar;
import net.xalcon.technomage.common.blocks.BlockConstructionTable;
import net.xalcon.technomage.common.blocks.BlockPedestal;
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace;
import net.xalcon.technomage.common.multiblocks.MultiblockBrickFurnace;

import java.util.HashMap;
import java.util.Map;

public class TMBlocks
{
    public final static Map<ResourceLocation, Block> blocks = new HashMap<>();

    public final static BlockBrickFurnace brickFurnace = new BlockBrickFurnace();

    public final static BlockAlchemicalCauldron alchemicalCauldron = new BlockAlchemicalCauldron();
    public final static BlockPedestal pedestal = new BlockPedestal();
    public final static BlockAmalgamationAltar amalgamationAltar = new BlockAmalgamationAltar();
    public final static Block constructionTable = new BlockConstructionTable();


    public static void init()
    {
        MultiblockRegistry.register(new MultiblockBrickFurnace());

        Technomage.Proxy.register(brickFurnace);
        Technomage.Proxy.register(alchemicalCauldron);
        Technomage.Proxy.register(pedestal);
        Technomage.Proxy.register(amalgamationAltar);
        Technomage.Proxy.register(constructionTable);
    }
}
