package net.xalcon.technomage.common.blocks.world;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.items.ItemBlockEnum;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockTMLeaves extends BlockLeaves implements IItemBlockProvider, IBlockColor, IItemColor
{
    public final static String INTERNAL_NAME = "leaves";

    public BlockTMLeaves()
    {
        super();
        this.setRegistryName(INTERNAL_NAME);
        this.setUnlocalizedName(Technomage.MOD_ID + "." + INTERNAL_NAME);
        this.setCreativeTab(CreativeTabsTechnomage.tabMain);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(TMTreeType type : TMTreeType.values())
            items.add(new ItemStack(this, 1, type.getMeta()));
    }

    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() != this) return Collections.emptyList(); // wait wuat? Dont ping me if you dont want anything from me, idiot.
        TMTreeType type = state.getValue(BlockTMLog.TYPE);
        return Collections.singletonList(new ItemStack(this, 1, type.getMeta()));
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation rl = BlockTMLeaves.this.getRegistryName();
        assert rl != null;

        final ModelResourceLocation[] mrlCache = Arrays.stream(TMTreeType.values())
            .map(t -> new ModelResourceLocation(rl, "type=" + t.getName()))
            .toArray(ModelResourceLocation[]::new);

        for(TMTreeType type : TMTreeType.values())
            ModelLoader.setCustomModelResourceLocation(item, type.getMeta(), mrlCache[type.getMeta()]);

        // Setup custom state mapper to ignore the `decayable` and `check_decay` properties since the leaves look the
        // same, independant of the decay state
        // TODO: This code should not run during item model registration, it should be in its own method
        ModelLoader.setCustomStateMapper(this, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return mrlCache[state.getValue(BlockTMLog.TYPE).getMeta()];
            }
        });
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE, BlockTMLog.TYPE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
            .withProperty(BlockTMLog.TYPE, TMTreeType.getFromMeta(meta & 0b0011))
            .withProperty(DECAYABLE, (meta & 0b0100) > 0)
            .withProperty(CHECK_DECAY, (meta & 0b1000) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockTMLog.TYPE).getMeta()
            | (state.getValue(DECAYABLE) ? 0b0100 : 0)
            | (state.getValue(CHECK_DECAY) ? 0b1000 : 0);
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

    // Abstract in BlockLeaves, therefore we are forced to override it. Since we dont use it, we can just return anything
    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.OAK;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
    {
        return tintIndex == 0 ? state.getValue(BlockTMLog.TYPE).getLeafColor() : -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tintIndex)
    {
        return tintIndex == 0 ? TMTreeType.getFromMeta(stack.getMetadata()).getLeafColor() : -1;
    }
}
