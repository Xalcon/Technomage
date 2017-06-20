package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.properties.EnumImbuedOre;
import net.xalcon.technomage.common.items.ItemBlockEnum;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockImbuedOre extends BlockTM
{
    public final static String INTERNAL_NAME = "imbued_ore";
    public final static PropertyEnum<EnumImbuedOre> ORE_TYPE = PropertyEnum.create("type", EnumImbuedOre.class);

    public BlockImbuedOre()
    {
        super(INTERNAL_NAME, Material.GROUND);
    }

    @Override
    public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> items)
    {
        if(creativeTab != CreativeTabsTechnomage.tabMain) return;
        items.addAll(Arrays.stream(EnumImbuedOre.values())
            .map(type -> new ItemStack(this, 1, type.getMeta()))
            .collect(Collectors.toList()));
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockEnum<>(this, EnumImbuedOre::getFromMeta);
    }

    @Override
    public void registerItemModels(@Nonnull Item item)
    {
        for(EnumImbuedOre type : EnumImbuedOre.values())
            ModelLoader.setCustomModelResourceLocation(item, type.getMeta(), new ModelResourceLocation(this.getRegistryName(), "type=" + type.getName()));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORE_TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ORE_TYPE, EnumImbuedOre.getFromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ORE_TYPE).getMeta();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, state.getValue(ORE_TYPE).getMeta());
    }

    @SideOnly(Side.CLIENT)
    public final static IBlockColor BLOCK_COLOR_HANDLER = (state, worldIn, pos, tintIndex) -> state.getValue(ORE_TYPE).getColor();

    @SideOnly(Side.CLIENT)
    public final static IItemColor ITEM_COLOR_HANDLER = (stack, tint) -> EnumImbuedOre.getFromMeta(stack.getMetadata()).getColor();

    @Override
    @SideOnly(Side.CLIENT)
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) ? 1 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return layer == BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int index = TintIndexGetter.getCurrent();
        return MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT && index == 0
            ? 240
            : super.getPackedLightmapCoords(state, source, pos);
    }

    @SideOnly(Side.CLIENT)
    private static class TintIndexGetter
    {
        private static Field lighterField;
        private static Field tintField;

        public static int getCurrent()
        {
            if(lighterField == null) init();
            BlockModelRenderer renderer = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer();
            try
            {
                @SuppressWarnings("unchecked")
                VertexLighterFlat lighterFlat = ((ThreadLocal<VertexLighterFlat>) lighterField.get(renderer)).get();
                return (int) tintField.get(lighterFlat);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            return -1;
        }

        private static void init()
        {
            BlockModelRenderer renderer = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer();
            if(renderer instanceof ForgeBlockModelRenderer)
            {
                try
                {
                    lighterField = ForgeBlockModelRenderer.class.getDeclaredField("lighterFlat");
                    lighterField.setAccessible(true);
                    tintField = VertexLighterFlat.class.getDeclaredField("tint");
                    tintField.setAccessible(true);

                } catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
