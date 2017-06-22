package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.properties.EnumImbuedOre;

import java.util.Arrays;

public class ItemImbuedShard extends ItemTM
{
    public final static String INTERNAL_NAME = "imbued_shard";

    public ItemImbuedShard()
    {
        super(INTERNAL_NAME);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(this.isInCreativeTab(tab))
            Arrays.stream(EnumImbuedOre.values())
                .forEach(o -> items.add(new ItemStack(this, 1, o.getMeta())));
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = new ResourceLocation(Technomage.MOD_ID, "items/" + INTERNAL_NAME);
        Arrays.stream(EnumImbuedOre.values())
            .forEach(o -> ModelLoader.setCustomModelResourceLocation(this, o.getMeta(),
                new ModelResourceLocation(loc, "type=" + o.getName())));

    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "." + EnumImbuedOre.getFromMeta(stack.getMetadata()).getName();
    }
}
