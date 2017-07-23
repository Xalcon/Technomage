package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.TileEntityLeylightBore;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.tiles.HasTileEntity;

import javax.annotation.Nullable;
import java.util.List;

@HasTileEntity(teClass = TileEntityLeylightBore.class)
public class BlockLeylightBore extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "leylight_bore";

    public BlockLeylightBore()
    {
        super(Material.WOOD);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos.down());
        return (state.getBlock() == TMBlocks.leylightBoreBase() && !state.getValue(BlockLeylightBoreBase.UPSIDEDOWN))
                || ((state = worldIn.getBlockState(pos.up())).getBlock() == TMBlocks.leylightBoreBase() && state.getValue(BlockLeylightBoreBase.UPSIDEDOWN));
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
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
