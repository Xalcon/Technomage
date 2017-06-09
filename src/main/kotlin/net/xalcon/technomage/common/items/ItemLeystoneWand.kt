package net.xalcon.technomage.common.items

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.xalcon.technomage.api.multiblocks.MultiblockRegistry

class ItemLeystoneWand : ItemTM("leystone_wand")
{
    override fun onItemUseFirst(player: EntityPlayer, world: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, hand: EnumHand): EnumActionResult
    {
        if(!world.isRemote)
        {
            MultiblockRegistry.getMultiblocks()
                    .filter { m -> m.isBlockTrigger(world.getBlockState(pos)) }
                    .filter { !MultiblockRegistry.postConstructionEvent(it, pos, player).isCanceled && it.createStructure(world, pos, side, player) }
                    .forEach { return EnumActionResult.SUCCESS }
        }
        return EnumActionResult.PASS
    }
}