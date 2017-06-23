package net.xalcon.technomage.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public abstract class BlockTMTileProvider extends BlockTM implements ITileEntityProvider
{
    public BlockTMTileProvider(String internalName, Material materialIn)
    {
        super(internalName, materialIn);
    }

    @Nullable
    public abstract Class<? extends TileEntity> getTileEntityClass();
}
