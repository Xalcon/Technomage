package net.xalcon.technomage.client.gui.multiblocks

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.xalcon.technomage.common.containers.multiblock.ContainerBrickFurnace

class GuiBrickFurnace(player: EntityPlayer) : GuiContainer(ContainerBrickFurnace(player))
{
    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int)
    {
    }
}