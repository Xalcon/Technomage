package net.xalcon.technomage.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public abstract class BlockTM extends Block implements IItemBlockProvider
{


    public BlockTM(String internalName, Material materialIn)
    {
        super(materialIn);
        this.setUnlocalizedName(Technomage.MOD_ID + "." + internalName);
        this.setRegistryName(internalName);
        this.setCreativeTab(CreativeTabsTechnomage.tabMain);
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

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "inventory"));
    }


}
