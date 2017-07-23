package net.xalcon.technomage.client.renderer.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.common.model.TRSRTransformation;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.TileEntityLeylightBore;
import org.lwjgl.opengl.GL11;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class TileEntityLeylightBoreRenderer extends TileEntitySpecialRenderer<TileEntityLeylightBore>
{
    @SuppressWarnings("ConstantConditions")
    private final static ModelResourceLocation MODEL = new ModelResourceLocation(TMBlocks.leylightBore().getRegistryName(), "normal");
    private static List<BakedQuad> quads;

    private static TextureAtlasSprite getTexture(ResourceLocation location)
    {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    @Override
    public void render(TileEntityLeylightBore tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        quads = blockRenderer.getModelForState(state).getQuads(state, null, 0);

        IBakedModel model = blockRenderer.getModelForState(tile.getWorld().getBlockState(tile.getPos()));

        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableLighting();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        //worldRenderer.setTranslation(-tile.getPos().getX(), -tile.getPos().getY(), -tile.getPos().getZ());

        //renderModelTESRFast(quads, worldRenderer, tile.getWorld(), tile.getPos());
        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tile.getWorld(), model, state, tile.getPos(), worldRenderer, true);
        worldRenderer.setTranslation(0, 0, 0);
        tessellator.draw();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }

    public static void renderModelTESRFast(List<BakedQuad> quads, BufferBuilder renderer, World world, BlockPos pos)
        {
        int brightness = world.getCombinedLight(pos, 0);
        int l1 = (brightness >> 0x10) & 0xFFFF;
        int l2 = brightness & 0xFFFF;
        for (BakedQuad quad : quads)
            {
            int[] vData = quad.getVertexData();
            VertexFormat format = quad.getFormat();
            int size = format.getIntegerSize();
            int uv = format.getUvOffsetById(0)/4;
            for (int i = 0; i < 4; ++i)
        {
                renderer
                        .pos(Float.intBitsToFloat(vData[size*i]),
                                Float.intBitsToFloat(vData[size*i+1]),
                                Float.intBitsToFloat(vData[size*i+2]))
                        .tex(Float.intBitsToFloat(vData[size*i+uv]), Float.intBitsToFloat(vData[size*i+uv+1]))
                        .color(255, 0, 0, 255)
                        .normal(((vData[size*i+6] >> 0) & 0xFF) / 127f, ((vData[size*i+6] >> 8) & 0xFF) / 127f, ((vData[size*i+6] >> 16) & 0xFF) / 127f)
                        //.lightmap(l1, l2)
                        .endVertex();
                }

            }
        }
}
