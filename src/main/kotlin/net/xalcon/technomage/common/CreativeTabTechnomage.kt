package net.xalcon.technomage.common

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.xalcon.technomage.Technomage

object CreativeTabTechnomage : CreativeTabs(Technomage.MOD_ID)
{
    override fun getTabIconItem(): ItemStack
    {
        return ItemStack(Items.BOOK)
    }
}