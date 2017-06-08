package net.xalcon.technolib.client

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

object GuiHelper
{
    private var lastBoundTexture:ResourceLocation? = null

    fun bindTexture(texture:ResourceLocation)
    {
        Minecraft.getMinecraft().textureManager.bindTexture(texture)
    }

    /**
     * Draws a textured rectangle at the current z-value.
     */
    fun drawTexturedModalRect(x: Int, y: Int, textureX: Int, textureY: Int, width: Int, height: Int, zLevel:Double = 0.0)
    {
        val tessellator = Tessellator.getInstance()
        val vertexbuffer = tessellator.buffer
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX)
        vertexbuffer.pos((x + 0).toDouble(), (y + height).toDouble(), zLevel).tex(((textureX + 0).toFloat() * 0.00390625f).toDouble(), ((textureY + height).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((x + width).toDouble(), (y + height).toDouble(), zLevel).tex(((textureX + width).toFloat() * 0.00390625f).toDouble(), ((textureY + height).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((x + width).toDouble(), (y + 0).toDouble(), zLevel).tex(((textureX + width).toFloat() * 0.00390625f).toDouble(), ((textureY + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((x + 0).toDouble(), (y + 0).toDouble(), zLevel).tex(((textureX + 0).toFloat() * 0.00390625f).toDouble(), ((textureY + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
        tessellator.draw()
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    fun drawTexturedModalRect(xCoord: Float, yCoord: Float, minU: Int, minV: Int, maxU: Int, maxV: Int, zLevel:Double = 0.0)
    {
        val tessellator = Tessellator.getInstance()
        val vertexbuffer = tessellator.buffer
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX)
        vertexbuffer.pos((xCoord + 0.0f).toDouble(), (yCoord + maxV.toFloat()).toDouble(), zLevel).tex(((minU + 0).toFloat() * 0.00390625f).toDouble(), ((minV + maxV).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((xCoord + maxU.toFloat()).toDouble(), (yCoord + maxV.toFloat()).toDouble(), zLevel).tex(((minU + maxU).toFloat() * 0.00390625f).toDouble(), ((minV + maxV).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((xCoord + maxU.toFloat()).toDouble(), (yCoord + 0.0f).toDouble(), zLevel).tex(((minU + maxU).toFloat() * 0.00390625f).toDouble(), ((minV + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
        vertexbuffer.pos((xCoord + 0.0f).toDouble(), (yCoord + 0.0f).toDouble(), zLevel).tex(((minU + 0).toFloat() * 0.00390625f).toDouble(), ((minV + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
        tessellator.draw()
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    fun drawTexturedModalRect(xCoord: Int, yCoord: Int, textureSprite: TextureAtlasSprite, widthIn: Int, heightIn: Int, zLevel:Double = 0.0)
    {
        val tessellator = Tessellator.getInstance()
        val vertexbuffer = tessellator.buffer
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX)
        vertexbuffer.pos((xCoord + 0).toDouble(), (yCoord + heightIn).toDouble(), zLevel).tex(textureSprite.minU.toDouble(), textureSprite.maxV.toDouble()).endVertex()
        vertexbuffer.pos((xCoord + widthIn).toDouble(), (yCoord + heightIn).toDouble(), zLevel).tex(textureSprite.maxU.toDouble(), textureSprite.maxV.toDouble()).endVertex()
        vertexbuffer.pos((xCoord + widthIn).toDouble(), (yCoord + 0).toDouble(), zLevel).tex(textureSprite.maxU.toDouble(), textureSprite.minV.toDouble()).endVertex()
        vertexbuffer.pos((xCoord + 0).toDouble(), (yCoord + 0).toDouble(), zLevel).tex(textureSprite.minU.toDouble(), textureSprite.minV.toDouble()).endVertex()
        tessellator.draw()
    }

    fun renderItemAndEffectIntoGUI(itemStack: ItemStack, x:Int, y:Int)
    {
        Minecraft.getMinecraft().renderItem.renderItemAndEffectIntoGUI(itemStack, x, y)
    }
}