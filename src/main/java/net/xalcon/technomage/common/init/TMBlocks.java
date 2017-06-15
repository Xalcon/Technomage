package net.xalcon.technomage.common.init;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace;
import net.xalcon.technomage.common.blocks.multiblocks.MultiblockBrickFurnace;

import java.util.HashMap;
import java.util.Map;

public class TMBlocks
{
    public final static Map<ResourceLocation, Block> blocks = new HashMap<>();

    public final static BlockBrickFurnace brickFurnace = new BlockBrickFurnace();

    public static void init()
    {
        MultiblockRegistry.register(new MultiblockBrickFurnace());

        Technomage.Proxy.register(brickFurnace);
    }
}
