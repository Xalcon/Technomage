package net.xalcon.technomage.common.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.common.blocks.ITechnomageTileEntityProvider;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;
import net.xalcon.technomage.common.items.ItemBlockEnum;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class BlockTMPlanks extends Block implements IItemBlockProvider
{
	public final static String INTERNAL_NAME = "planks";

	public BlockTMPlanks()
	{
		super(Material.WOOD);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(BlockTMLog.TYPE).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockTMLog.TYPE);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(TMTreeType w : TMTreeType.values())
			items.add(new ItemStack(this, 1, w.getMeta()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BlockTMLog.TYPE, TMTreeType.getFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockTMLog.TYPE).getMeta();
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockEnum<>(this, TMTreeType::getFromMeta);
	}

	@Override
	public void registerItemModels(Item item)
	{
		ResourceLocation loc = item.getRegistryName();
		assert loc != null;

		for(TMTreeType w : TMTreeType.values())
			ModelLoader.setCustomModelResourceLocation(item, w.getMeta(),
				new ModelResourceLocation(loc, "type=" + w.getName()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.getValue(BlockTMLog.TYPE).getMapColor();
	}
}
