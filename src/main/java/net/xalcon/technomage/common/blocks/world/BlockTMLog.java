package net.xalcon.technomage.common.blocks.world;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyEnum;
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
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.items.ItemBlockEnum;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockTMLog extends BlockLog implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "log";
    public final static PropertyEnum<TMTreeType> TYPE = PropertyEnum.create("type", TMTreeType.class);

    public BlockTMLog()
    {
        this.setRegistryName(INTERNAL_NAME);
        this.setUnlocalizedName(Technomage.MOD_ID + "." + INTERNAL_NAME);
        this.setCreativeTab(CreativeTabsTechnomage.tabMain);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        this.setHarvestLevel("axe", 0);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.addAll(Arrays.stream(TMTreeType.values())
            .map(w -> new ItemStack(this, 1, w.getMeta()))
            .collect(Collectors.toList()));
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockEnum<>(this, TMTreeType::getFromMeta);
    }

    @Override
    public boolean hasItemBlock()
    {
        return true;
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        Arrays.stream(TMTreeType.values())
            .forEach(t -> ModelLoader.setCustomModelResourceLocation(item, t.getMeta(),
                new ModelResourceLocation(loc, "axis=y,type=" + t.getName())));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, LOG_AXIS);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
            .withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[meta >> 2])
            .withProperty(TYPE, TMTreeType.getFromMeta(meta & 0b0011));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(LOG_AXIS).ordinal() << 2) | (state.getValue(TYPE).getMeta() & 0b0011);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getMeta();
    }
}
