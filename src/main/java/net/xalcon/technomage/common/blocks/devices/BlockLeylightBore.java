package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.xalcon.technomage.client.renderer.block.SingleGroupModelState;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.TileEntityLeylightBore;
import net.xalcon.technomage.lib.WorldHelper;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.tiles.HasTileEntity;

import javax.annotation.Nullable;
import java.util.List;

@HasTileEntity(teClass = TileEntityLeylightBore.class)
public class BlockLeylightBore extends Block implements IItemBlockProvider
{
    public final static PropertyBool RENDER_TE = PropertyBool.create("render_te");
    public final static String INTERNAL_NAME = "leylight_bore";

    public BlockLeylightBore()
    {
        super(Material.WOOD);
        this.setDefaultState(this.getDefaultState().withProperty(RENDER_TE, true));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos.down());
        return (state.getBlock() == TMBlocks.leylightBoreBase() && !state.getValue(BlockLeylightBoreBase.UPSIDEDOWN))
                || ((state = worldIn.getBlockState(pos.up())).getBlock() == TMBlocks.leylightBoreBase() && state.getValue(BlockLeylightBoreBase.UPSIDEDOWN));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty[] { RENDER_TE }, new IUnlistedProperty[] { Properties.AnimationProperty });
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        state = super.getExtendedState(state, world, pos);
        if(state instanceof IExtendedBlockState)
        {
            IExtendedBlockState exState = (IExtendedBlockState) state;
            return exState.withProperty(Properties.AnimationProperty, new SingleGroupModelState("BoreCore"));
        }
        return state;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        //worldIn.setBlockState(pos, state.withProperty(RENDER_TE, !state.getValue(RENDER_TE)));
        if(!state.getValue(RENDER_TE))
        {
            worldIn.setBlockState(pos, state.withProperty(RENDER_TE, !state.getValue(RENDER_TE)));
            return true;
        }

        TileEntityLeylightBore te = WorldHelper.getTileEntitySafe(worldIn, pos, TileEntityLeylightBore.class);
        if(te != null)
        {
            if(te.direction != facing)
            {
                te.direction = facing;
                te.startPitch = te.curPitch;
                te.startYaw = te.curYaw;
                Vec2f newTarget = TileEntityLeylightBore.ROTATIONS[facing.getIndex()];
                te.targetPitch = newTarget.x;
                te.targetYaw = newTarget.y;
                te.directionProgress = 1;
            }
        }
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(RENDER_TE, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(RENDER_TE) ? 1 : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return state.getValue(RENDER_TE) ? EnumBlockRenderType.ENTITYBLOCK_ANIMATED : EnumBlockRenderType.MODEL;
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
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return 0;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format(this.getUnlocalizedName() + ".usage"));
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
        return new TileEntityLeylightBore();
    }
}
