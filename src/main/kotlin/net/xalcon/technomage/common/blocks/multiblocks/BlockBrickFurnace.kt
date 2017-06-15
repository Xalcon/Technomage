package net.xalcon.technomage.common.blocks.multiblocks

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.xalcon.technomage.common.blocks.BlockTMMultiblock
import net.xalcon.technomage.common.tileentities.multiblock.TileEntityBrickFurnace

class BlockBrickFurnace : BlockTMMultiblock("brick_furnace", Material.ROCK)
{
    companion object
    {
        val FACING = PropertyEnum.create("facing", EnumFacing::class.java, EnumFacing.HORIZONTALS.toList())!!
        val IS_LOWER = PropertyBool.create("lower")!!
        val IS_ACTIVE = PropertyBool.create("active")!!
    }

    override fun getTileEntityClasses() = listOf(Pair("brick_furnace", TileEntityBrickFurnace::class.java))

    override fun createNewTileEntity(worldIn: World?, meta: Int) = TileEntityBrickFurnace()

    override fun createBlockState() = BlockStateContainer(this, FACING, IS_LOWER, IS_ACTIVE)

    fun getPlacementState(facing:EnumFacing, isLower:Boolean):IBlockState =
            this.defaultState.withProperty(IS_LOWER, isLower).withProperty(FACING, facing).withProperty(IS_ACTIVE, false)

    override fun getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState
    {
        val tile = (worldIn.getTileEntity(pos) ?: return state) as? TileEntityBrickFurnace ?: return state
        return state
                /*.withProperty(FACING, tile.facing)
                .withProperty(IS_LOWER, tile.isLower)
                .withProperty(IS_ACTIVE, tile.isActive)*/
    }

    override fun getStateFromMeta(meta: Int): IBlockState
    {
        val facing = EnumFacing.getHorizontal(meta and 0b0011)
        val isLower = meta and 0b0100 > 0
        val isActive = meta and 0b1000 > 0
        return this.defaultState
                .withProperty(FACING, facing)
                .withProperty(IS_LOWER, isLower)
                .withProperty(IS_ACTIVE, isActive)
    }

    override fun getMetaFromState(state: IBlockState): Int
    {
        val facing = state.getValue(FACING).horizontalIndex
        val isLower = if(state.getValue(IS_LOWER)) 1 else 0
        val isActive = if(state.getValue(IS_ACTIVE)) 1 else 0

        var meta: Int = facing
        meta = meta or (isLower shl 2)
        meta = meta or (isActive shl 3)
        return meta
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean
    {
        playerIn.openGui(Technomage, 1, worldIn, pos.x, pos.y, pos.y)
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
    }
}