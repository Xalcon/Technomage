package net.xalcon.technomage.api.multiblocks

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
class MultiblockConstructionEvent(val multiblock:IMultiblock, val pos:BlockPos, player:EntityPlayer) : PlayerEvent(player)