package net.xalcon.technomage

import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.xalcon.technomage.api.multiblocks.MultiblockRegistry
import net.xalcon.technomage.common.blocks.multiblocks.MultiblockBrickFurnace

object TMBlocks
{
    val blocks:Map<ResourceLocation, Block> = HashMap()

    fun init()
    {
        MultiblockRegistry.register(MultiblockBrickFurnace())
    }
}