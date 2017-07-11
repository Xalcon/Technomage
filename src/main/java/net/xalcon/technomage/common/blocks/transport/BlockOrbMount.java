package net.xalcon.technomage.common.blocks.transport;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.technomage.common.tileentities.TileEntityOrbMount;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import javax.annotation.Nullable;

public class BlockOrbMount extends Block implements IItemBlockProvider
{
    public final static PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
    public final static PropertyBool HAS_ORB = PropertyBool.create("has_orb");
    public static final String INTERNAL_NAME = "orb_mount";

    public final static AxisAlignedBB BOUNDS_DOWN = new AxisAlignedBB(4f/16f, 2f/16f, 4f/16f, 12f/16f, 16f/16f, 12f/16f);
    public final static AxisAlignedBB BOUNDS_UP = new AxisAlignedBB(4f/16f, 0, 4f/16f, 12f/16f, 14f/16f, 12f/16f);
    public final static AxisAlignedBB BOUNDS_SOUTH = new AxisAlignedBB(4f/16f,4f/16f,  0, 12f/16f, 12f/16f, 14f/16f);
    public final static AxisAlignedBB BOUNDS_NORTH = new AxisAlignedBB(4f/16f,4f/16f,  2f/16f, 12f/16f, 12f/16f, 16f/16f);
    public final static AxisAlignedBB BOUNDS_WEST = new AxisAlignedBB(2f/16f, 4f/16f, 4f/16f, 16f/16f, 12f/16f, 12f/16f);
    public final static AxisAlignedBB BOUNDS_EAST = new AxisAlignedBB(0, 4f/16f, 4f/16f, 14f/16f, 12f/16f, 12f/16f);

    private final static AxisAlignedBB[] BOUNDS = { BOUNDS_DOWN, BOUNDS_UP, BOUNDS_NORTH, BOUNDS_SOUTH, BOUNDS_WEST, BOUNDS_EAST };

    public BlockOrbMount()
    {
        super(Material.IRON);
        this.setHardness(1f);
        this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(HAS_ORB, false));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, HAS_ORB);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getFront(meta & 0b0111))
                .withProperty(HAS_ORB, (meta & 0b1000) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING).getIndex() & 0b0111) | (state.getValue(HAS_ORB) ? 0b1000 : 0);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDS[state.getValue(FACING).getIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        worldIn.setBlockState(pos, state.withProperty(HAS_ORB, !state.getValue(HAS_ORB)));
        return true;
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
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityOrbMount();
    }
}
