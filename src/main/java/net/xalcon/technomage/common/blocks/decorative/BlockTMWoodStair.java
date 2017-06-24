package net.xalcon.technomage.common.blocks.decorative;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;
import net.xalcon.technomage.lib.item.IItemBlockProvider;
import net.xalcon.technomage.lib.utils.InstanceFactoryMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class BlockTMWoodStair extends BlockStairs implements IItemBlockProvider
{
    public static final String INTERNAL_NAME_ELDER = "wood_stairs_elder";
    public static final String INTERNAL_NAME_LEY = "wood_stairs_ley";
    public static final String INTERNAL_NAME_FEL = "wood_stairs_fel";
    public static final String INTERNAL_NAME_BAMBOO = "wood_stairs_bamboo";

    private BlockTMWoodStair(TMTreeType woodType)
    {
        super(getPlanksBlockState(woodType));
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "facing=south,half=bottom,shape=straight"));
    }

    @SuppressWarnings("ConstantConditions")
    public static IBlockState getPlanksBlockState(TMTreeType type)
    {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Technomage.MOD_ID, BlockTMPlanks.INTERNAL_NAME))
            .getDefaultState()
            .withProperty(BlockTMLog.TYPE, type);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface InstanceParameters
    {
        TMTreeType value();
    }

    @InstanceFactoryMethod
    public static BlockTMWoodStair createInstance(InstanceParameters params)
    {
        return new BlockTMWoodStair(params.value());
    }
}
