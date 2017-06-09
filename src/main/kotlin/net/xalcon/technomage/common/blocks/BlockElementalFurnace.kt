package net.xalcon.technomage.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.xalcon.technomage.common.blocks.properties.EnumFurnaceType

class BlockElementalFurnace : BlockTM("elemental_furnace", Material.ROCK)
{
    companion object
    {
        val FURNACE_TYPE = PropertyEnum.create("type", EnumFurnaceType::class.java)!!
        val FACING = PropertyEnum.create("facing", EnumFacing::class.java, EnumFacing.HORIZONTALS.toList())!!
    }

    override fun createBlockState(): BlockStateContainer
    {
        return BlockStateContainer(this, FURNACE_TYPE, FACING)
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Suppress("OverridingDeprecatedMember")
    override fun getStateFromMeta(meta: Int): IBlockState
    {
        return this.defaultState
                .withProperty(FURNACE_TYPE, EnumFurnaceType.values()[meta and 0b0011])
                .withProperty(FACING, EnumFacing.getHorizontal((meta and 0b1100) shr 2))
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    override fun getMetaFromState(state: IBlockState): Int
    {
        return (state.getValue(FURNACE_TYPE).ordinal and 0b0011) or ((state.getValue(FACING).horizontalIndex and 0b0011) shl 2)
    }
}