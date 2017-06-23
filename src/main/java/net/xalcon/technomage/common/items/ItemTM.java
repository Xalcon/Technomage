package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabsTechnomage;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class ItemTM extends Item implements IItemModelRegisterHandler
{
    public ItemTM(String internalName)
    {
        this.setUnlocalizedName(Technomage.MOD_ID + "." + internalName);
        this.setRegistryName(internalName);
        this.setCreativeTab(CreativeTabsTechnomage.tabMain);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation loc = this.getRegistryName();
        assert loc != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(loc, "inventory"));
    }
}
