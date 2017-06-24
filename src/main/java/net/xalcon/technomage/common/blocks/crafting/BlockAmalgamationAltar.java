package net.xalcon.technomage.common.blocks.crafting;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.common.blocks.BlockTMTileProvider;
import net.xalcon.technomage.common.init.TMItems;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;

import javax.annotation.Nullable;

public class BlockAmalgamationAltar extends BlockTMTileProvider
{
    public final static String INTERNAL_NAME = "amalgamation_altar";
    public BlockAmalgamationAltar()
    {
        super(INTERNAL_NAME, Material.ROCK);
    }

    @Nullable
    @Override
    public Class<? extends TileEntity> getTileEntityClass()
    {
        return TileEntityAmalgamationAltar.class;
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
            TileEntityAmalgamationAltar altar = (TileEntityAmalgamationAltar) te;
            ItemStack heldItem = playerIn.getHeldItem(hand);
            ItemStack itemStack = altar.getItemStack();
            if(heldItem.getItem() == TMItems.leystoneWand() && !itemStack.isEmpty())
            {
                // try start crafting
                altar.startCrafting();
            }
            else if(!itemStack.isEmpty())
            {
                if(playerIn.addItemStackToInventory(itemStack.copy()))
                {
                    altar.removeItemStack();
                }
            }
            else
            {
                if(!heldItem.isEmpty() && altar.putItemStack(heldItem.copy()))
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
