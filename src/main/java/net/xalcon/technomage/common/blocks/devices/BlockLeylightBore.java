package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.TileEntityLeylightBore;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.tiles.HasTileEntity;

@HasTileEntity(teClass = TileEntityLeylightBore.class)
public class BlockLeylightBore extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "leylight_bore";

    public BlockLeylightBore()
    {
        super(Material.WOOD);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == TMBlocks.leylightBoreBase();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
}
