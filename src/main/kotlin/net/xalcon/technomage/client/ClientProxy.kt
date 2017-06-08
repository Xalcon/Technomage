package net.xalcon.technomage.client

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.xalcon.technolib.item.IItemModelRegisterHandler
import net.xalcon.technomage.common.CommonProxy

@SideOnly(Side.CLIENT)
class ClientProxy : CommonProxy()
{
    override fun <T : Item> register(item: T): T
    {
        super.register(item)

        if(item is IItemModelRegisterHandler)
            item.registerItemModels(item)
        else
            ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName, "inventory"))

        return item
    }
}