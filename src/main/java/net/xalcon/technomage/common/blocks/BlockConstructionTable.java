package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class BlockConstructionTable extends BlockTMTileProvider
{
    public BlockConstructionTable()
    {
        super("construction_table", Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(Technomage.getInstance(), 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public Map<String, Class<? extends TileEntity>> getTileEntityClasses()
    {
        return Collections.singletonMap(this.getRegistryName().toString(), TileEntityConstructionTable.class);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityConstructionTable();
    }
}