package net.xalcon.technomage.common.tileentities.multiblock

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.xalcon.technomage.common.tileentities.EnumSyncType
import net.xalcon.technomage.common.tileentities.TileEntityTMBase

open class TileEntityTMMultiblockPart<T> : TileEntityTMBase() where T : TileEntityTMMultiblockPart<T>
{
    var formed:Boolean = false
    var isMaster:Boolean = false
    var masterPos:BlockPos = BlockPos.ORIGIN
    var index:Int = -1
    var facing:EnumFacing = EnumFacing.NORTH

    override fun readNbt(nbt: NBTTagCompound, type: EnumSyncType)
    {
        super.readNbt(nbt, type)
        this.formed = nbt.getBoolean("formed")
        this.isMaster = nbt.getBoolean("master")
        this.masterPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("masterPos"))
        this.index = nbt.getInteger("index")
        this.facing = EnumFacing.getFront(nbt.getByte("facing").toInt())
    }

    override fun writeNbt(nbt: NBTTagCompound, type: EnumSyncType): NBTTagCompound
    {
        nbt.setBoolean("formed", this.formed)
        nbt.setBoolean("master", this.isMaster)
        nbt.setTag("masterPos", NBTUtil.createPosTag(this.masterPos))
        nbt.setInteger("index", this.index)
        nbt.setByte("facing", this.facing.index.toByte())
        return super.writeNbt(nbt, type)
    }

    @Suppress("UNCHECKED_CAST")
    fun getMaster(): T?
    {
        if(this.isMaster) return this as T
        if(this.masterPos != BlockPos.ORIGIN && this.getWorld().isBlockLoaded(this.masterPos))
        {
            val te = this.getWorld().getTileEntity(this.masterPos)
            if(this.javaClass.isInstance(te))
                return te as T
        }
        return null
    }
}