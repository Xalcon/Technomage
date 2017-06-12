package net.xalcon.technomage.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.xalcon.technolib.item.IItemBlockProvider
import net.xalcon.technolib.item.IItemModelRegisterHandler
import net.xalcon.technomage.Technomage
import net.xalcon.technomage.common.CreativeTabTechnomage
import net.xalcon.technomage.common.items.ItemBlockTM

open class BlockTM(internalName:String, materialIn: Material?)
    : Block(materialIn), IItemBlockProvider, IItemModelRegisterHandler
{
    init
    {
        this.setRegistryName(internalName)
        this.unlocalizedName = "${Technomage.MOD_ID}.$internalName"
        this.setCreativeTab(CreativeTabTechnomage)
    }

    override fun registerItemModels(item: Item) =
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName, "inventory"))

    override fun createItemBlock() = ItemBlockTM(this)

    override fun hasItemBlock() = true
}