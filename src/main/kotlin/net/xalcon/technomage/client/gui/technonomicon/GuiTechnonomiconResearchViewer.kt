package net.xalcon.technomage.client.gui.technonomicon

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec2f
import net.xalcon.technomage.Technomage
import net.xalcon.technomage.api.technonomicon.IResearchPage
import net.xalcon.technomage.api.technonomicon.ResearchItem
import net.xalcon.technomage.api.technonomicon.ResearchPage
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Point
import org.lwjgl.util.Rectangle

class GuiTechnonomiconResearchViewer : GuiScreen()
{
    private val RESEARCH_TEX = ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png")

    private var winScaleFactor: Int = 0
    private var zoom = 1f
    private var x: Int = 0
    private var y: Int = 0
    private var scrolling: Boolean = false
    private var lastMouseX: Int = 0
    private var lastMouseY: Int = 0

    private var guiWidth: Int = 0
    private var guiHeight: Int = 0

    private val pageList:ArrayList<IResearchPage> = ArrayList()

    override fun doesGuiPauseGame(): Boolean = false

    override fun initGui()
    {
        super.initGui()
        this.guiWidth = 256 + 96
        this.guiHeight = 224
        val page = ResearchPage(Technomage.MOD_ID, "main", ItemStack(Items.DIAMOND_SWORD))
        this.pageList.add(page)

        page.addResearchItem(ResearchItem(Point(32, 32), "Test 01", ItemStack(Items.DIAMOND_SWORD)))
        page.addResearchItem(ResearchItem(Point(64, 64), "Test 02", ItemStack(Items.DIAMOND_SHOVEL)))
    }

    override fun setWorldAndResolution(mc: Minecraft, width: Int, height: Int)
    {
        super.setWorldAndResolution(mc, width, height)
        this.winScaleFactor = ScaledResolution(mc).scaleFactor
    }

    /**
     * Draws the screen and all the components in it.
     */
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float)
    {
        // calculate the top + left position to center the gui
        val left = (this.width - this.guiWidth) / 2
        val top = (this.height - this.guiHeight) / 2

        // relative mouse position to the window
        val relMouseX = mouseX - left
        val relMouseY = mouseY - top

        // handle input like dragging the view
        this.handleInput(relMouseX, relMouseY)
        this.drawDefaultBackground()

        GlStateManager.pushMatrix()
        GlStateManager.enableBlend()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        // translate the state so 0,0 is the top left corner of our gui
        GlStateManager.translate(left.toFloat(), top.toFloat(), 0f)
        GlStateManager.pushMatrix()
        // apply the zoom, then render the view
        val INSET = 8
        val viewWidth = this.guiWidth
        val viewHeight = this.guiHeight
        val viewport = Rectangle(this.x, this.y, (viewWidth * this.zoom).toInt(), (viewHeight * this.zoom).toInt())
        val relMousePos = Vec2f(relMouseX * this.zoom, relMouseY * this.zoom)
        GlStateManager.scale(1.0f / this.zoom, 1.0f / this.zoom, 1.0f)

        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        GL11.glScissor((left + INSET) * this.winScaleFactor, (top + INSET) * this.winScaleFactor, (viewWidth - INSET * 2) * this.winScaleFactor, (viewHeight - INSET * 2) * this.winScaleFactor)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.translate(this.x.toDouble(), this.y.toDouble(), 0.0)
        this.pageList[0].renderView(viewport, relMousePos)
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
        GlStateManager.popMatrix()

        GlStateManager.disableDepth()
        GlStateManager.enableBlend()
        //GlStateManager.color(1.0f, 1.0f, 1.0f, 0.3f)
        this.drawBorder()
        //GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.enableDepth()
        GlStateManager.popMatrix()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    private fun handleInput(relMouseX:Int, relMouseY:Int)
    {
        if (Mouse.isButtonDown(0))
        {
            if (relMouseX > 0 && relMouseX < this.guiWidth && relMouseY > 0 && relMouseY < this.guiHeight)
            {
                if (!this.scrolling)
                {
                    // we werent scrolling before, so start scrolling mode
                    this.scrolling = true
                }
                else
                {
                    // we are scrolling, move the viewport by the relative mouse movement since last tick
                    this.x += ((relMouseX - this.lastMouseX) * this.zoom).toInt()
                    this.y += ((relMouseY - this.lastMouseY) * this.zoom).toInt()
                }

                this.lastMouseX = relMouseX
                this.lastMouseY = relMouseY
            }
        }
        else
        {
            // no longer scrolling
            this.scrolling = false
        }

        val wheel = Mouse.getDWheel()
        if (wheel < 0)
            this.zoom += 0.25f
        else if (wheel > 0)
            this.zoom -= 0.25f
        this.zoom = MathHelper.clamp(this.zoom, 1.0f, 2.0f)
    }

    private fun drawBorder()
    {
        this.mc.textureManager.bindTexture(RESEARCH_TEX)

        // we need to substract 16 from the x and y positions because of the shadow effect on the border texture
        val hCount = 1 + (this.guiWidth - 16) / 64
        for (i in 0..hCount - 1)
        {
            val rW = Math.min(64, this.guiWidth - 32 - i * 64)
            this.drawTexturedModalRect(16 + i * 64, 0, 48, 16, rW, 16)
            this.drawTexturedModalRect(16 + i * 64, this.guiHeight - 16, 48, 16, rW, 16)
        }

        val vCount = 1 + (this.guiHeight - 16) / 64
        for (i in 0..vCount - 1)
        {
            val rH = Math.min(64, this.guiHeight - 32 - i * 64)
            this.drawTexturedModalRect(0, 16 + i * 64, 16, 48, 16, rH)
            this.drawTexturedModalRect(this.guiWidth - 16, 16 + i * 64, 16, 48, 16, rH)
        }

        // TOP LEFT
        this.drawTexturedModalRect(-16, -16, 0, 0, 48, 48)
        // TOP RIGHT
        this.drawTexturedModalRect(this.guiWidth - 32, -16, 0, 0, 48, 48)
        // BOTTOM LEFT
        this.drawTexturedModalRect(-16, this.guiHeight - 32, 0, 0, 48, 48)
        // BOTTOM RIGHT
        this.drawTexturedModalRect(this.guiWidth - 32, this.guiHeight - 32, 0, 0, 48, 48)
    }
}