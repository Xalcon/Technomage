package net.xalcon.technomage.common.blocks.crafting;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.BlockTMTileProvider;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

import javax.annotation.Nullable;

public class BlockConstructionTable extends BlockTMTileProvider
{
    public final static String INTERNAL_NAME = "construction_table";
    public BlockConstructionTable()
    {
        super(INTERNAL_NAME, Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(Technomage.getInstance(), 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Nullable
    @Override
    public Class<? extends TileEntity> getTileEntityClass()
    {
        return TileEntityConstructionTable.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityConstructionTable();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
