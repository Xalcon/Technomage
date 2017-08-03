package net.xalcon.technomage.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.Properties;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.blocks.devices.BlockLeylightBore;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.TileEntityLeylightBore;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntityLeylightBoreRenderer extends TileEntitySpecialRenderer<TileEntityLeylightBore>
{
    @SuppressWarnings("ConstantConditions")
    private final static ModelResourceLocation MODEL = new ModelResourceLocation(TMBlocks.leylightBore().getRegistryName(), "normal");
    private final static ResourceLocation LASER_TEX = new ResourceLocation(Technomage.MOD_ID, "textures/fx/laser_fx.png");
    private static List<BakedQuad> baseQuads;
    private static List<BakedQuad> coreQuads;
    private static List<BakedQuad> ringBackQuads;
    private static List<BakedQuad> ringCenterQuads;
    private static List<BakedQuad> ringFrontQuads;
    private static List<BakedQuad> focusCrystalQuads;

    public TileEntityLeylightBoreRenderer()
    {
        final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IExtendedBlockState state = (IExtendedBlockState) TMBlocks.leylightBore().getDefaultState();
        IBakedModel model = blockRenderer.getModelForState(state);
        baseQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("BoreBase")), null, 0);
        coreQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("BoreCore")), null, 0);
        ringBackQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("RingBack")), null, 0);
        ringCenterQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("RingCenter")), null, 0);
        ringFrontQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("RingFront")), null, 0);
        focusCrystalQuads = model.getQuads(state.withProperty(Properties.AnimationProperty, new SingleGroupModelState("FocusCrystal")), null, 0);
    }

    @Override
    public boolean isGlobalRenderer(TileEntityLeylightBore te)
    {
        return true;
    }

    @Override
    public void render(TileEntityLeylightBore tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if(!tile.getWorld().getBlockState(tile.getPos()).getValue(BlockLeylightBore.RENDER_TE)) return;

        final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.color(1, 1, 1, 1);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder worldRenderer = tessellator.getBuffer();

        if(tile.directionProgress > 0)
        {
            tile.curPitch = tile.targetPitch - (tile.targetPitch - tile.startPitch) * (tile.directionProgress - partialTicks * .05f);
            tile.curYaw = tile.targetYaw - (tile.targetYaw - tile.startYaw) * (tile.directionProgress - partialTicks * .05f);
        }
        else
        {
            tile.curPitch = tile.targetPitch;
            tile.curYaw = tile.targetYaw;
        }

        GlStateManager.translate(0.5, 0.5, 0.5);
        GlStateManager.rotate(tile.curYaw, 0, 1, 0);
        GlStateManager.translate(-0.5, -0.5, -0.5);

        worldRenderer.begin(GL11.GL_QUADS, baseQuads.get(0).getFormat());
        renderModelTESRFast(baseQuads, worldRenderer);
        tessellator.draw();

        GlStateManager.translate(0.5, 0.5, 0.5);
        GlStateManager.rotate(tile.curPitch, 1, 0, 0);
        GlStateManager.translate(-0.5, -0.5, -0.5);

        worldRenderer.begin(GL11.GL_QUADS, baseQuads.get(0).getFormat());
        renderModelTESRFast(coreQuads, worldRenderer);
        renderModelTESRFast(ringBackQuads, worldRenderer);
        renderModelTESRFast(ringCenterQuads, worldRenderer);
        renderModelTESRFast(ringFrontQuads, worldRenderer);
        renderModelTESRFast(focusCrystalQuads, worldRenderer);
        tessellator.draw();
        renderLaser(16f);

        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }

    private void renderLaser(float length)
    {
        float f = -(System.currentTimeMillis() % 300) / 300f;
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.alphaFunc(GL11.GL_GREATER, .1f);
        GlStateManager.enableAlpha();
        Minecraft.getMinecraft().renderEngine.bindTexture(LASER_TEX);
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.color(1f, 0f, .3f);
        BufferBuilder worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-.5, 0.5, 0.5).tex(f, 0).endVertex();
        worldRenderer.pos(-.5, length, 0.5).tex(length + f, 0).endVertex();
        worldRenderer.pos(1.5, length, 0.5).tex(length + f, 1).endVertex();
        worldRenderer.pos(1.5, 0.5, 0.5).tex(f, 1).endVertex();

        worldRenderer.pos(0.5, 0.5, -.5).tex(f, 0).endVertex();
        worldRenderer.pos(0.5, length, -.5).tex(length + f, 0).endVertex();
        worldRenderer.pos(0.5, length, 1.5).tex(length + f, 1).endVertex();
        worldRenderer.pos(0.5, 0.5, 1.5).tex(f, 1).endVertex();
        tessellator.draw();
    }

    public static void renderModelTESRFast(List<BakedQuad> quads, BufferBuilder builder)
    {
        final int QUAD_VERTEXCOUNT = 4;
        for (BakedQuad quad : quads)
        {
            VertexFormat format = quad.getFormat();
            int[] vData = quad.getVertexData();
            int size = format.getIntegerSize();
            int uv = format.getUvOffsetById(0) / QUAD_VERTEXCOUNT;
            int n = format.getNormalOffset() / QUAD_VERTEXCOUNT;
            for (int i = 0; i < 4; ++i)
            {
                builder.pos(Float.intBitsToFloat(vData[size * i]), Float.intBitsToFloat(vData[size * i + 1]), Float.intBitsToFloat(vData[size * i + 2]))
                       .color(255, 255, 255, 255)
                       .tex(Float.intBitsToFloat(vData[size * i + uv]), Float.intBitsToFloat(vData[size * i + uv + 1]))
                       .normal(((vData[size * i + n]) & 0xFF) / 127f, ((vData[size * i + n] >> 8) & 0xFF) / 127f, ((vData[size * i + n] >> 16) & 0xFF) / 127f)
                       .endVertex();
            }

        }
    }
}
