package net.xalcon.technomage.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTMBase extends TileEntity
{
    /**
     * Called when the TE is loaded from disk
     */
    @Override
    public final void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.readNbt(nbt, EnumSyncType.TILE_STORE);
    }

    /**
     * Called when the TE is saved to disk
     */
    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        return this.writeNbt(super.writeToNBT(nbt), EnumSyncType.TILE_STORE);
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        this.readNbt(pkt.getNbtCompound(), EnumSyncType.TILE_UPDATE_PARTIAL);
    }

    /**
     * Called when the TileEntity was marked for update via World.notifyBlockUpdate()
     */
    @Override
    public final SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.getPos(), -1, this.writeNbt(new NBTTagCompound(), EnumSyncType.TILE_UPDATE_PARTIAL));
    }

    /**
     * Called when the chunk's TE update tag, gotten from [.getUpdateTag], is received on the client.
     * @param tag The [NBTTagCompound] sent from [.getUpdateTag]
     */
    @Override
    public final void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
        this.readNbt(tag, EnumSyncType.TILE_UPDATE_FULL);
    }
    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return this.writeNbt(super.getUpdateTag(), EnumSyncType.TILE_UPDATE_FULL);
    }

    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
    }

    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        return nbt;
    }
}
