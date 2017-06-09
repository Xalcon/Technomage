package net.xalcon.technomage.api.technonomicon

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec2f
import net.xalcon.technolib.client.GuiHelper
import net.xalcon.technomage.Technomage
import org.lwjgl.util.Rectangle

class ResearchPage(modId:String, private val name: String, private val itemStack: ItemStack) : IResearchPage
{
    private val RESEARCH_TEX = ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png")
    private val resourceLocation:ResourceLocation = ResourceLocation(modId, name)
    private val researchTree:ArrayList<IResearchItem> = ArrayList()

    var backgroundTexture = ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_01.png")

    fun addResearchItem(researchItem:IResearchItem)
    {
        this.researchTree.add(researchItem)
    }

    override fun getUnlocalizedName():String = this.name

    override fun getRegistryName():ResourceLocation = this.resourceLocation

    override fun getIcon():ItemStack = itemStack

    override fun isVisible(): Boolean = true

    override fun getSortingIndex(): Int = 0

    override fun renderView(viewport: Rectangle, mousePos:Vec2f)
    {
        GuiHelper.bindTexture(this.backgroundTexture)
        GuiHelper.drawTexturedModalRect(-viewport.x, -viewport.y, -viewport.x, -viewport.y, viewport.width, viewport.height)
        //GuiHelper.drawTexturedModalRect(-viewport.x, -viewport.y, 0, 0, viewport.width, viewport.height)
        RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.pushMatrix()
        this.researchTree
                //.filter { i -> i.bounds.intersects(viewport) }
                .forEach { i -> i.renderItem(viewport) }
        GlStateManager.popMatrix()
        RenderHelper.disableStandardItemLighting()
    }

    override fun handleMouseOver(mousePos: Vec2f)
    {
    }
}