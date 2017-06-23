package net.xalcon.technomage.common.blocks.world;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.xalcon.technomage.common.blocks.BlockTM;
import net.xalcon.technomage.common.blocks.properties.EnumWoodType;

public class BlockTMPlanks extends BlockTM
{
	public final static String INTERNAL_NAME = "planks";

	public BlockTMPlanks()
	{
		super(INTERNAL_NAME, Material.WOOD);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return super.damageDropped(state);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockTMLog.TYPE);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(EnumWoodType w : EnumWoodType.values())
			items.add(new ItemStack(this, 1, w.getMeta()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BlockTMLog.TYPE, EnumWoodType.getFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockTMLog.TYPE).getMeta();
	}

	@SuppressWarnings("deprecation")
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.getValue(BlockTMLog.TYPE).getMapColor();
	}
}
