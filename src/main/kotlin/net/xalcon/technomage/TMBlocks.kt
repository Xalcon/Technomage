package net.xalcon.technomage

import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.xalcon.technomage.api.multiblocks.MultiblockRegistry
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace
import net.xalcon.technomage.common.blocks.multiblocks.MultiblockBrickFurnace

object TMBlocks
{
    val blocks:Map<ResourceLocation, Block> = HashMap()

    val brickFurnace = BlockBrickFurnace()

    fun init()
    {
        MultiblockRegistry.register(MultiblockBrickFurnace())

        Technomage.Proxy.register(brickFurnace)
    }
}