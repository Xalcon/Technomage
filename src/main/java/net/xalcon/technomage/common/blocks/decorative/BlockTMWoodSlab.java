package net.xalcon.technomage.common.blocks.decorative;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;
import net.xalcon.technomage.common.items.ItemBlockEnum;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public class BlockTMWoodSlab extends BlockSlab implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "wood_slab";

    public BlockTMWoodSlab()
    {
        super(Material.WOOD);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (TMTreeType type : TMTreeType.values())
        {
            items.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return this.getUnlocalizedName() + "." + TMTreeType.getFromMeta(meta & 0b0111).getName();
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return BlockTMLog.TYPE;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return TMTreeType.getFromMeta(stack.getMetadata() & 0b0111);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF, BlockTMLog.TYPE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
            .withProperty(BlockTMLog.TYPE, TMTreeType.getFromMeta(meta & 0b0111))
            .withProperty(HALF, (meta & 0b1000) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockTMLog.TYPE).getMeta() | (state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 0b1000);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = item.getRegistryName();
        assert loc != null;
        for(TMTreeType type : TMTreeType.values())
            ModelLoader.setCustomModelResourceLocation(item, type.getMeta(), new ModelResourceLocation(loc, "half=bottom,type=" + type.getName()));

    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockEnum<>(this, TMTreeType::getFromMeta);
    }
}
