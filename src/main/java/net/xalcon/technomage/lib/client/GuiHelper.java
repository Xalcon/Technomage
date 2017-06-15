package net.xalcon.technomage.lib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiHelper
{
    public static void bindTexture(ResourceLocation texture)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }
    /**
     * Draws a textured rectangle at the current z-value.
     */
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, double zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x), (double)(y + height), zLevel).tex((double)((float)(textureX) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x), (double)(y), zLevel).tex((double)((float)(textureX) * 0.00390625F), (double)((float)(textureY) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV, double zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), zLevel).tex((double)((float)(minU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), zLevel).tex((double)((float)(minU) * 0.00390625F), (double)((float)(minV) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn, double zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(xCoord), (double)(yCoord + heightIn), zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        vertexbuffer.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        vertexbuffer.pos((double)(xCoord + widthIn), (double)(yCoord), zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        vertexbuffer.pos((double)(xCoord), (double)(yCoord), zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public static void renderItemAndEffectIntoGUI(ItemStack itemStack, int x, int y)
    {
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
    }
}
