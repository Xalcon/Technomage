package net.xalcon.technomage.api.technonomicon

import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec2f
import org.lwjgl.util.Dimension
import org.lwjgl.util.Point
import org.lwjgl.util.Rectangle

interface IResearchPage
{
    fun getSize():Dimension
    fun getCenter():Point

    /**
     * Returns the unlocalized name of this research page. Will be displayed on the tab icon on mouse over
     */
    fun getUnlocalizedName():String

    /**
     * Returns the registry name of this research page, this needs to be unique
     */
    fun getRegistryName():ResourceLocation

    /**
     * Returns the icon that should be displayed on the tab for this research page.
     */
    fun getIcon():ItemStack

    /**
     * Returns if this tab should be visible
     */
    fun isVisible():Boolean

    /**
     * The lower this index, the higher it will be show up in the tab list
     */
    fun getSortingIndex():Int

    /**
     * Renders the view and its content.
     * The GL state will be translated to the top left corner of the viewport
     * Zoom needs to be applied manually if this view supports zoom
     * @param viewport the unscaled visible part of the view
     * @param zoom the zoom level of the view
     * @param mousePos the unscaled mouse position relative to the viewport
     */
    fun renderView(viewport:Rectangle, zoom:Float, mousePos: Vec2f)

    fun handleMouseOver(viewport:Rectangle, zoom:Float, mousePos: Vec2f)
}