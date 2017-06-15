package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.CreativeTabTechnomage;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class ItemTM extends Item implements IItemModelRegisterHandler
{
    public ItemTM(String internalName)
    {
        this.setUnlocalizedName(Technomage.MOD_ID + "." + internalName);
        this.setRegistryName(internalName);
        this.setCreativeTab(CreativeTabTechnomage.Main);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
