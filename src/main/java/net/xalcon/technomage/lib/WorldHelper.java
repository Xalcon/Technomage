package net.xalcon.technomage.lib;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class WorldHelper
{
    @Nullable
    public static <T extends TileEntity> T getTileEntitySafe(IBlockAccess world, BlockPos pos, Class<T> clazz)
    {
        TileEntity te = world.getTileEntity(pos);
        return te != null && clazz.isAssignableFrom(te.getClass()) ? clazz.cast(te) : null;
    }
}
