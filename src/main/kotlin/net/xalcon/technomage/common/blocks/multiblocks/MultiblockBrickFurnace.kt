package net.xalcon.technomage.common.blocks.multiblocks

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.xalcon.technomage.Technomage
import net.xalcon.technomage.api.multiblocks.IMultiblock

class MultiblockBrickFurnace : IMultiblock
{
    override fun isBlockTrigger(state: IBlockState): Boolean = state.block == Blocks.BRICK_BLOCK

    override fun createStructure(world: World, pos: BlockPos, facing: EnumFacing, player: EntityPlayer): Boolean
    {
        if(!isStructureValid(world, pos, facing, player)) return false
        world.setBlockState(pos, Blocks.FURNACE.getStateForPlacement(world, pos, facing, 0.5f, 0.5f, 0.5f, 0, player, EnumHand.MAIN_HAND))
        world.setBlockState(pos.up(), Blocks.FURNACE.getStateForPlacement(world, pos, facing, 0.5f, 0.5f, 0.5f, 0, player, EnumHand.MAIN_HAND))
        world.playSound(null, pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f)
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    private fun isStructureValid(world: World, pos: BlockPos, facing: EnumFacing, player: EntityPlayer): Boolean
    {
        if(world.getBlockState(pos).block != Blocks.BRICK_BLOCK) return false
        if(world.getBlockState(pos.up()).block != Blocks.HARDENED_CLAY) return false
        return true
    }

    override fun getName(): ResourceLocation = ResourceLocation(Technomage.MOD_ID, "brick_furnace")
}