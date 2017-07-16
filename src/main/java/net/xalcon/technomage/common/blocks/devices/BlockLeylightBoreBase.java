package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public class BlockLeylightBoreBase extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "leylight_bore_base";
    public final static PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);
    public final static PropertyBool UPSIDEDOWN = PropertyBool.create("upsidedown");

    public BlockLeylightBoreBase()
    {
        super(Material.WOOD);
        this.setHardness(1f);
        this.setResistance(10f);
        this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, UPSIDEDOWN);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011))
                .withProperty(UPSIDEDOWN, (meta & 0b0100) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(UPSIDEDOWN) ? 0b0100 : 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState()
                .withProperty(FACING, placer.getHorizontalFacing().getOpposite())
                .withProperty(UPSIDEDOWN, facing == EnumFacing.DOWN);
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
        return layer == BlockRenderLayer.TRANSLUCENT;
    }
}
