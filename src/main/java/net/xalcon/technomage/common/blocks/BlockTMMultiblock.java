package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;

public abstract class BlockTMMultiblock extends BlockTMTileProvider
{
    public BlockTMMultiblock(String internalName, Material materialIn)
    {
        super(internalName, materialIn);
    }

    @Override
    public boolean hasItemBlock()
    {
        return false;
    }
}
