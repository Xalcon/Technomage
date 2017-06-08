package net.xalcon.technomage.common.items

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.xalcon.technomage.Technomage

class ItemTechnonomicon : ItemTM("technonomicon")
{
    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack>
    {
        playerIn.openGui(Technomage, 0, worldIn, 0, 0, 0)
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }
}