package net.xalcon.technomage.lib.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public interface IItemModelRegisterHandler
{
    default void registerItemModels(Item item)
    {
        ResourceLocation rl = item.getRegistryName();
        assert rl != null;
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(rl, "inventory"));
    }
}
