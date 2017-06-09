package net.xalcon.technomage.api.multiblocks

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface IMultiblock
{
    fun isBlockTrigger(state:IBlockState):Boolean

    fun createStructure(world:World, pos:BlockPos, facing:EnumFacing, player:EntityPlayer):Boolean

    fun getName():ResourceLocation

    @Suppress("DEPRECATION")
    fun getBlockstateFromMeta(index:Int, stack:ItemStack):IBlockState?
    {
        if(!stack.isEmpty && stack.item is ItemBlock)
            return (stack.item as ItemBlock).getBlock().getStateFromMeta(stack.metadata)
        return null
    }
}