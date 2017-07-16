package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public class BlockLeylightBoreBase extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "leylight_bore_base";

    public BlockLeylightBoreBase()
    {
        super(Material.WOOD);
        this.setHardness(1f);
        this.setResistance(10f);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return layer == BlockRenderLayer.CUTOUT;
    }
}
