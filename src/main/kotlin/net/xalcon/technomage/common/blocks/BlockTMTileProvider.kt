package net.xalcon.technomage.common.blocks

import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity

abstract class BlockTMTileProvider(internalName: String, material: Material) :
        BlockTM(internalName, material), ITileEntityProvider
{
    abstract fun getTileEntityClasses():Iterable<Pair<String, Class<out TileEntity>>>
}