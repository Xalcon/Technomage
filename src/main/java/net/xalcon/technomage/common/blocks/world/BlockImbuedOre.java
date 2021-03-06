package net.xalcon.technomage.common.blocks.world;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.properties.TMImbuedOreType;
import net.xalcon.technomage.common.items.ItemBlockEnum;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockImbuedOre extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "imbued_ore";
    public final static PropertyEnum<TMImbuedOreType> ORE_TYPE = PropertyEnum.create("type", TMImbuedOreType.class);

    public BlockImbuedOre()
    {
        super(Material.GROUND);
    }

    @Override
    public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> items)
    {
        if(creativeTab != CreativeTabsTechnomage.tabMain) return;
        items.addAll(Arrays.stream(TMImbuedOreType.values())
            .map(type -> new ItemStack(this, 1, type.getMeta()))
            .collect(Collectors.toList()));
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlockEnum<>(this, TMImbuedOreType::getFromMeta);
    }

    @Override
    public void registerItemModels(@Nonnull Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        for(TMImbuedOreType type : TMImbuedOreType.values())
            ModelLoader.setCustomModelResourceLocation(item, type.getMeta(), new ModelResourceLocation(loc, "type=" + type.getName()));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORE_TYPE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ORE_TYPE, TMImbuedOreType.getFromMeta(meta));
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

    /**
     * Queries if this block should render in a given layer.
     * ISmartBlockModel can use {@link MinecraftForgeClient#getRenderLayer()} to alter their model based on layer.
     *
     * @param state
     * @param layer
     */
    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return layer != BlockRenderLayer.TRANSLUCENT;
    }
}
