package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;
import net.xalcon.technomage.common.tileentities.TileEntityPedestal;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class BlockAmalgamationAltar extends BlockTMTileProvider
{
    public BlockAmalgamationAltar()
    {
        super("amalgamation_altar", Material.ROCK);
    }

    @Override
    public Map<String, Class<? extends TileEntity>> getTileEntityClasses()
    {
        return Collections.singletonMap(this.getRegistryName().toString(), TileEntityAmalgamationAltar.class);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return true;
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityAmalgamationAltar)
        {
            TileEntityAmalgamationAltar pedestal = (TileEntityAmalgamationAltar) te;
            ItemStack itemStack = pedestal.getItemStack();
            if(!itemStack.isEmpty())
            {
                if(playerIn.addItemStackToInventory(itemStack.copy()))
                {
                    pedestal.removeItemStack();
                }
            }
            else
            {
                ItemStack heldItem = playerIn.getHeldItem(hand);
                if(!heldItem.isEmpty() && pedestal.putItemStack(heldItem.copy()))
                {
                    heldItem.setCount(0);
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityAmalgamationAltar();
    }
}
