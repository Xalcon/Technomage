package net.xalcon.technomage.common.tileentities.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.xalcon.technomage.common.tileentities.EnumSyncType;
import net.xalcon.technomage.common.tileentities.TileEntityTMBase;

public class TileEntityTMMultiblockPart<T extends TileEntityTMMultiblockPart<T>> extends TileEntityTMBase
{
    public boolean formed = false;
    public boolean isMaster = false;
    public BlockPos masterPos = BlockPos.ORIGIN;
    public int index = -1;
    public EnumFacing facing = EnumFacing.NORTH;

    @Override
    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        super.readNbt(nbt, type);
        this.formed = nbt.getBoolean("formed");
        this.isMaster = nbt.getBoolean("master");
        this.masterPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("masterPos"));
        this.index = nbt.getInteger("index");
        this.facing = EnumFacing.getFront(nbt.getByte("facing"));
    }

    @Override
    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        nbt.setBoolean("formed", this.formed);
        nbt.setBoolean("master", this.isMaster);
        nbt.setTag("masterPos", NBTUtil.createPosTag(this.masterPos));
        nbt.setInteger("index", this.index);
        nbt.setByte("facing", (byte) this.facing.getIndex());
        return super.writeNbt(nbt, type);
    }

    @SuppressWarnings("unchecked")
    public T getMaster()
    {
        if(this.isMaster) return (T) this;
        if(this.masterPos != BlockPos.ORIGIN && this.getWorld().isBlockLoaded(this.masterPos))
        {
            TileEntity te = this.getWorld().getTileEntity(this.masterPos);
            if(this.getClass().isInstance(te))
                return (T) te;
        }
        return null;
    }
}
