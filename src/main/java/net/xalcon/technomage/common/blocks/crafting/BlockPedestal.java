package net.xalcon.technomage.common.blocks.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.blocks.ITechnomageTileEntityProvider;
import net.xalcon.technomage.common.tileentities.TileEntityPedestal;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPedestal extends Block implements ITechnomageTileEntityProvider, IItemBlockProvider
{
    public final static String INTERNAL_NAME = "pedestal";
    private final static AxisAlignedBB BOTTOM_SLAB = new AxisAlignedBB(0f, 0f, 0f, 1f, 4f / 16f, 1f);
    private final static AxisAlignedBB COLUMN = new AxisAlignedBB(4f / 16f, 5f / 16f, 4f / 16f, 12f / 16f, 12f / 16f, 12f / 16f);
    private final static AxisAlignedBB TOP_SLAB = new AxisAlignedBB(2f / 16f, 12f / 16f, 2f / 16f, 14f / 16f, 1f, 14f / 16f);
    private final static AxisAlignedBB[] BOXES = {BOTTOM_SLAB, COLUMN, TOP_SLAB};

    public BlockPedestal()
    {
        super(Material.ROCK);
    }

    @Override
    public void registerTileEntities()
    {
        ResourceLocation rl = this.getRegistryName();
        assert rl != null;
        GameRegistry.registerTileEntity(TileEntityPedestal.class, rl.toString());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPedestal();
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

    int loops = 100000;

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return true;
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileEntityPedestal)
        {
            TileEntityPedestal pedestal = (TileEntityPedestal) te;
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

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, TOP_SLAB);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLUMN);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, TOP_SLAB);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {

        RayTraceResult hitRay = null;
        Vec3d start1 = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        Vec3d end1 = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        double dist = Double.POSITIVE_INFINITY;

        for(int i = 0; i < BOXES.length; i++)
        {
            RayTraceResult ray = BOXES[i].calculateIntercept(start1, end1);
            if (ray != null)
            {
                double dist1 = ray.hitVec.squareDistanceTo(start1);

                if (dist >= dist1)
                {
                    dist = dist1;
                    hitRay = ray;
                    hitRay.subHit = i;
                }
            }
        }

        if(hitRay != null)
        {
            RayTraceResult outRay = new RayTraceResult(hitRay.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), hitRay.sideHit, pos);
            outRay.subHit = hitRay.subHit;
            return outRay;
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        RayTraceResult trace = Minecraft.getMinecraft().objectMouseOver;
        if (trace == null || trace.subHit < 0 || !pos.equals(trace.getBlockPos()))
        {
            return super.getSelectedBoundingBox(state, worldIn, pos);
        }
        return BOXES[trace.subHit].offset(pos);
    }
}
