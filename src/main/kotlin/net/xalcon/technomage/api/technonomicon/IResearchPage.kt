package net.xalcon.technomage.api.technonomicon

import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec2f
import org.lwjgl.util.Rectangle

interface IResearchPage
{
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
     * Returns if this tab should be visible at all.
     */
    fun isVisible():Boolean

    /**
     * The lower this index, the higher it will be show up in the tab list
     */
    fun getSortingIndex():Int

    /**
     * Renders the view and its content
     * @param viewport the visible part of the view
     * @param mousePos the mouse position relative to the viewport
     */
    fun renderView(viewport:Rectangle, mousePos: Vec2f)

    fun handleMouseOver(mousePos: Vec2f)
}