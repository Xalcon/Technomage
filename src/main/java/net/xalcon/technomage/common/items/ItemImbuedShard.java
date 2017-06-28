package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.properties.TMImbuedOreType;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

import java.util.Arrays;

public class ItemImbuedShard extends Item implements IItemModelRegisterHandler
{
    public ItemImbuedShard()
    {
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(this.isInCreativeTab(tab))
            Arrays.stream(TMImbuedOreType.values())
                .forEach(o -> items.add(new ItemStack(this, 1, o.getMeta())));
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation rl = this.getRegistryName();
        assert rl != null;
        // register a "blockstate" for our item to allow different models depending on the variant
        ResourceLocation loc = new ResourceLocation(Technomage.MOD_ID, "items/" + rl.getResourcePath());
        for(TMImbuedOreType ore : TMImbuedOreType.values())
        {
            ModelLoader.setCustomModelResourceLocation(this, ore.getMeta(), new ModelResourceLocation(loc, "type=" + ore.getName()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "." + TMImbuedOreType.getFromMeta(stack.getMetadata()).getName();
    }
}
