package net.xalcon.technomage.common.blocks.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.technomage.common.blocks.ITechnomageTileEntityProvider;
import net.xalcon.technomage.common.init.TMItems;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import javax.annotation.Nullable;

public class BlockAmalgamationAltar extends Block implements ITechnomageTileEntityProvider, IItemBlockProvider
{
    public final static String INTERNAL_NAME = "amalgamation_altar";
    public BlockAmalgamationAltar()
    {
        super(Material.ROCK);
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

    @Override
    public void registerTileEntities()
    {
        ResourceLocation rl = this.getRegistryName();
        assert rl != null;
        GameRegistry.registerTileEntity(TileEntityAmalgamationAltar.class, rl.toString());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityAmalgamationAltar();
    }
}
