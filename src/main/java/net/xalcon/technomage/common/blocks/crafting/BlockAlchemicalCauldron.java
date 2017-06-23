package net.xalcon.technomage.common.blocks.crafting;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.xalcon.technomage.common.blocks.BlockTMTileProvider;
import net.xalcon.technomage.common.tileentities.TileEntityAlchemicalCauldron;

import javax.annotation.Nullable;
import java.util.List;

public class BlockAlchemicalCauldron extends BlockTMTileProvider
{
    public final static String INTERNAL_NAME = "alchemical_cauldron";

    public BlockAlchemicalCauldron()
    {
        super(INTERNAL_NAME, Material.IRON);
    }

    @Nullable
    @Override
    public Class<? extends TileEntity> getTileEntityClass()
    {
        return TileEntityAlchemicalCauldron.class;
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

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 1f, 3f / 16f,1f));

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 1f, 1f,2f / 16f));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 14f / 16f, 1f, 1f,1f));

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 2f / 16f, 1f,1f));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(14f / 16f, 0, 0, 1f, 1f,1f));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if(!(te instanceof TileEntityAlchemicalCauldron)) return false;

        TileEntityAlchemicalCauldron cauldron = (TileEntityAlchemicalCauldron) te;
        if(cauldron.hasWater) return false;

        ItemStack itemStack = playerIn.getHeldItem(hand);
        if(itemStack.getItem().getContainerItem() != Items.BUCKET) return false;

        IFluidHandlerItem fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(fluidHandler == null) return false;

        FluidStack fluidStack = fluidHandler.drain(new FluidStack(FluidRegistry.WATER, 1000), !playerIn.isCreative());
        if(fluidStack == null) return false;

        cauldron.hasWater = true;
        worldIn.notifyBlockUpdate(pos, state, state, 3);
        worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);

        return true;
    }
}
