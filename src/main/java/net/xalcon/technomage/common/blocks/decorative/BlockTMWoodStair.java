package net.xalcon.technomage.common.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public class BlockTMWoodStair extends BlockStairs implements IItemBlockProvider
{
    public static final String INTERNAL_NAME_PREFIX = "wood_stairs_";

    public BlockTMWoodStair(TMTreeType woodType, Block planks)
    {
        super(planks.getDefaultState().withProperty(BlockTMLog.TYPE, woodType));
        this.setRegistryName(INTERNAL_NAME_PREFIX + woodType.getName());
        this.setUnlocalizedName(Technomage.MOD_ID + "." + INTERNAL_NAME_PREFIX + woodType.getName());
        this.setCreativeTab(CreativeTabsTechnomage.tabMain);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "facing=south,half=bottom,shape=straight"));
    }

    @Override
    public ItemBlock createItemBlock()
    {
        return new ItemBlock(this);
    }

    @Override
    public boolean hasItemBlock()
    {
        return true;
    }
}
