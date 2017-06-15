package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.tileentities.TileEntityAlchemicalCauldron;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BlockAlchemicalCauldron extends BlockTMTileProvider
{
    public BlockAlchemicalCauldron()
    {
        super("alchemical_cauldron", Material.IRON);
    }

    @Override
    public Map<String, Class<? extends TileEntity>> getTileEntityClasses()
    {
        return Collections.singletonMap(Technomage.MOD_ID + "." + this.getRegistryName().getResourceDomain(), TileEntityAlchemicalCauldron.class);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityAlchemicalCauldron();
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
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 1f, 3f / 16f,1f));

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 1f, 1f,2f / 16f));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 14f / 16f, 1f, 1f,1f));

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 2f / 16f, 1f,1f));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(14f / 16f, 0, 0, 1f, 1f,1f));
    }
}
