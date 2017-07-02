package net.xalcon.technomage.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

public class ItemIngot extends Item implements IItemModelRegisterHandler
{
    public final static String INTERNAL_NAME = "ingot";

    @Override
    public void registerItemModels(Item item)
    {
        ResourceLocation rl = this.getRegistryName();
        assert rl != null;
        ResourceLocation itemLoc = new ResourceLocation(rl.getResourceDomain() , "items/" + rl.getResourcePath());
        for(TMMaterial material : TMMaterial.values())
            ModelLoader.setCustomModelResourceLocation(this, material.getMeta(), new ModelResourceLocation(itemLoc, "type=" + material.getName()));
    }
}
