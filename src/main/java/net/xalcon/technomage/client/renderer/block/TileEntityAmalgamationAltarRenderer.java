package net.xalcon.technomage.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;

public class TileEntityAmalgamationAltarRenderer extends TileEntitySpecialRenderer<TileEntityAmalgamationAltar>
{
    @Override
    public void renderTileEntityAt(TileEntityAmalgamationAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage, alpha);
        ItemStack itemStack = te.getItemStack();
        //ItemStack itemStack = new ItemStack(Items.PAPER);
        if(itemStack.isEmpty()) return;

        GlStateManager.pushMatrix();
        float interval = ((System.currentTimeMillis() % 10000) / 10000f);
        float yBounce = (float) Math.sin(interval * (2 * Math.PI)) * 0.05f;
        GlStateManager.enableLighting();
        GlStateManager.translate(x + 0.5f, y + 1.2 + yBounce, z + 0.5f);
        GlStateManager.rotate(interval * 360f, 0, 1f, 0);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.popMatrix();
    }
}
