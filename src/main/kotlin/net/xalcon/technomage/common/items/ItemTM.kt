package net.xalcon.technomage.common.items

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.xalcon.technolib.item.IItemModelRegisterHandler
import net.xalcon.technomage.Technomage
import net.xalcon.technomage.common.CreativeTabTechnomage

open class ItemTM(internalName:String) : Item(), IItemModelRegisterHandler
{
    init
    {
        this.unlocalizedName = "${Technomage.MOD_ID}.$internalName"
        this.setRegistryName(internalName)
        this.creativeTab = CreativeTabTechnomage
    }

    override fun registerItemModels(item: Item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName, "inventory"))
    }
}