package net.xalcon.technomage.api.technonomicon

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.xalcon.technolib.client.GuiHelper
import net.xalcon.technomage.Technomage
import org.lwjgl.util.Dimension
import org.lwjgl.util.Point
import org.lwjgl.util.Rectangle

class ResearchItem(pos:Point, private val tooltip:String, private val icon:ItemStack) : IResearchItem
{
    private val TEXTURE = ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png")

    var iconType:IconType = IconType.DEFAULT

    /**
     * Returns the bounds (position and size) of this research item in the research view
     * Used for mouse over detection
     */
    override var bounds: Rectangle = Rectangle(pos, Dimension(26, 16))

    /**
     * Returns the tooltip that should be displayed on mouse over
     */
    override fun getTooltip(): String
    {
        return this.tooltip
    }

    /**
     * Called when the item needs to be rendered.
     * Will only be called if the icon is atleast partially visible (determined by {@see bounds})
     * @param viewport The visible part of the research view
     */
    override fun renderItem(viewport: Rectangle)
    {
        GuiHelper.bindTexture(this.TEXTURE)
        GuiHelper.drawTexturedModalRect(this.bounds.x, this.bounds.y, 42 + 26 * this.iconType.offset, 107, 26, 26)
        GlStateManager.color(1f, 1f, 1f)
        GlStateManager.disableLighting()
        GlStateManager.enableCull()
        GuiHelper.renderItemAndEffectIntoGUI(this.icon, this.bounds.x + 5, this.bounds.y + 5)
    }
}