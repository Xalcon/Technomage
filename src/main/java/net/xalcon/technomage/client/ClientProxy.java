package net.xalcon.technomage.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.CommonProxy;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public <T extends Item> T register(T item)
    {
        super.register(item);

        if(item instanceof IItemModelRegisterHandler)
            ((IItemModelRegisterHandler)item).registerItemModels(item);
        else
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        return item;
    }
}
