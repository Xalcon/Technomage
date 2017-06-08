package net.xalcon.technomage.common

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.xalcon.technomage.client.gui.technonomicon.GuiTechnonomiconResearchViewer

object TechnomageGuiHandler : IGuiHandler
{
    override fun getClientGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any?
    {
        return when(ID)
        {
            0 -> GuiTechnonomiconResearchViewer()
            else -> null
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any?
    {
        return when(ID)
        {
            0 -> null
            else -> null
        }
    }

}