package net.xalcon.technomage.api.technonomicon

import org.lwjgl.util.Rectangle

interface IResearchItem
{
    /**
     * Returns the bounds (position and size) of this research item in the research view
     * Used for mouse over detection
     */
    var bounds:Rectangle

    /**
     * Returns the tooltip that should be displayed on mouse over
     */
    fun getTooltip():String

    /**
     * Called when the icon is mouse overed
     * Is called before the tooltip is rendered
     */
    fun onMouseOver() { }

    /**
     * Called when the item needs to be rendered.
     * Will only be called if the icon is atleast partially visible (determined by {@see bounds})
     * @param viewport The visible part of the research view
     */
    fun renderItem(viewport:Rectangle)
}