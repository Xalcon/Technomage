package net.xalcon.technomage.common.containers.multiblock

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.nbt.JsonToNBT

class ContainerBrickFurnace(val player: EntityPlayer) : Container()
{
    init
    {
        bindPlayerInventory()
    }

    fun bindPlayerInventory()
    {
        for (row in 0..2)
        {
            for (column in 0..8)
            {
                this.addSlotToContainer(Slot(this.player.inventory, column + row * 9 + 9, 8 + column * 18, 103 + row * 18))
            }
        }

        for (slot in 0..8)
        {
            this.addSlotToContainer(Slot(this.player.inventory, slot, 8 + slot * 18, 161))
        }
    }

    override fun canInteractWith(playerIn: EntityPlayer?): Boolean = true
}