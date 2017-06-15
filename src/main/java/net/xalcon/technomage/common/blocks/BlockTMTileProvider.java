package net.xalcon.technomage.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;

public abstract class BlockTMTileProvider extends BlockTM implements ITileEntityProvider
{
    public BlockTMTileProvider(String internalName, Material materialIn)
    {
        super(internalName, materialIn);
    }

    public abstract Map<String, Class<? extends TileEntity>> getTileEntityClasses();
}
