package net.xalcon.technomage.api.multiblocks

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.MinecraftForge
import net.xalcon.technomage.Technomage

object MultiblockRegistry
{
    private val multiblocks:ArrayList<IMultiblock> = ArrayList()

    fun register(multiblock:IMultiblock)
    {
        if(this.multiblocks.any { m -> m.getName() == multiblock })
        {
            Technomage.Log.error("cannot to register ${multiblock.getName()} twice.")
            return
        }

        this.multiblocks.add(multiblock)
    }

    fun getMultiblocks():Iterable<IMultiblock> = this.multiblocks

    fun postConstructionEvent(multiblock: IMultiblock, pos: BlockPos, player: EntityPlayer):MultiblockConstructionEvent
    {
        val event = MultiblockConstructionEvent(multiblock, pos, player)
        MinecraftForge.EVENT_BUS.post(event)
        return event
    }
}