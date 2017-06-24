package net.xalcon.technomage.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockTMMultiblock extends Block implements ITechnomageTileEntityProvider
{
    public BlockTMMultiblock(Material materialIn)
    {
        super(materialIn);
    }
}
