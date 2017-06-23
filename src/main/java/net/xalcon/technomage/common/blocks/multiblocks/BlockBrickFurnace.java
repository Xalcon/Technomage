package net.xalcon.technomage.common.blocks.multiblocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.BlockTMMultiblock;

import javax.annotation.Nullable;

public class BlockBrickFurnace extends BlockTMMultiblock
{
    public static PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);
    public static PropertyBool IS_LOWER = PropertyBool.create("lower");
    public static PropertyBool IS_ACTIVE = PropertyBool.create("active");

    public final static String internalName = "brick_furnace";

    public BlockBrickFurnace()
    {
        super(internalName, Material.ROCK);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass()
    {
        return null;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return null;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, IS_LOWER, IS_ACTIVE);
    }

    public IBlockState getPlacementState(EnumFacing facing, boolean isLower)
    {
        return this.getDefaultState().withProperty(IS_LOWER, isLower).withProperty(FACING, facing).withProperty(IS_ACTIVE, false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 0b0011))
                .withProperty(IS_LOWER, (meta & 0b0100) > 0)
                .withProperty(IS_ACTIVE, (meta & 0b1000) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex()
                | (state.getValue(IS_LOWER) ? 0b0100 : 0)
                | (state.getValue(IS_ACTIVE) ? 0b1000 : 0);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(Technomage.getInstance(), 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
