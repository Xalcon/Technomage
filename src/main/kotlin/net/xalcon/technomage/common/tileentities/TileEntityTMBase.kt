package net.xalcon.technomage.common.tileentities

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

abstract class TileEntityTMBase : TileEntity()
{
    /**
     * Called when the TE is loaded from disk
     */
    final override fun readFromNBT(nbt: NBTTagCompound)
    {
        super.readFromNBT(nbt)
        this.readNbt(nbt, EnumSyncType.TILE_STORE)
    }

    /**
     * Called when the TE is saved to disk
     */
    final override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound
    {
        return this.writeNbt(super.writeToNBT(nbt), EnumSyncType.TILE_STORE)
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    final override fun onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity)
    {
        super.onDataPacket(net, pkt)
        readNbt(pkt.nbtCompound, EnumSyncType.TILE_UPDATE_FULL)
    }

    final override fun getUpdatePacket(): SPacketUpdateTileEntity?
    {
        return SPacketUpdateTileEntity(this.getPos(), -1, this.writeNbt(NBTTagCompound(), EnumSyncType.TILE_UPDATE_FULL))
    }

    /**
     * Called when the chunk's TE update tag, gotten from [.getUpdateTag], is received on the client.
     * @param tag The [NBTTagCompound] sent from [.getUpdateTag]
     */
    final override fun handleUpdateTag(tag: NBTTagCompound)
    {
        super.handleUpdateTag(tag)
        this.readNbt(tag, EnumSyncType.TILE_UPDATE_PARTIAL)
    }

    /**
     * Called when the TileEntity was marked for update via World.notifyBlockUpdate()
     */
    final override fun getUpdateTag(): NBTTagCompound
    {
        return this.writeNbt(super.getUpdateTag(), EnumSyncType.TILE_UPDATE_PARTIAL)
    }

    open fun readNbt(nbt:NBTTagCompound, type:EnumSyncType)
    {
    }

    open fun writeNbt(nbt:NBTTagCompound, type:EnumSyncType): NBTTagCompound
    {
        return nbt
    }
}