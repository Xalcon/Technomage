package net.xalcon.technomage.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.xalcon.technomage.common.tileentities.TileEntityAlchemicalCauldron;
import org.lwjgl.opengl.GL11;

public class TileEntityAlchemicalCauldronRenderer extends TileEntitySpecialRenderer<TileEntityAlchemicalCauldron>
{
    @Override
    public void renderTileEntityAt(TileEntityAlchemicalCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(!te.hasWater) return;
        GlStateManager.translate(x, y, z);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        Fluid fluid = FluidRegistry.WATER;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());

        if(sprite == null)
            sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0f, 0.9f, 0f).tex(sprite.getMinU(), sprite.getMinV()).color(1f, 0.5f, 0.5f, 1f).endVertex();
        buffer.pos(0f, 0.9f, 1f).tex(sprite.getMinU(), sprite.getMaxV()).color(1f, 0.5f, 0.5f, 1f).endVertex();
        buffer.pos(1f, 0.9f, 1f).tex(sprite.getMaxU(), sprite.getMaxV()).color(1f, 0.5f, 0.5f, 1f).endVertex();
        buffer.pos(1f, 0.9f, 0f).tex(sprite.getMaxU(), sprite.getMinV()).color(1f, 0.5f, 0.5f, 1f).endVertex();
        tessellator.draw();
        GlStateManager.translate(-x, -y, -z);
    }
}
