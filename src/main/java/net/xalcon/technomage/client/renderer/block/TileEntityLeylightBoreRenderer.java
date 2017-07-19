package net.xalcon.technomage.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
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

    private static TextureAtlasSprite getTexture(ResourceLocation location)
    {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    @Override
    public void render(TileEntityLeylightBore te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        IModel model = ModelLoaderRegistry.getModelOrMissing(MODEL);

        IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter());

        RenderHelper.disableStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180, 0, 0, 1);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        builder.setTranslation(-0.5, -1.5, -0.5);

        throw new NotImplementedException();
        VertexLighterFlat lighter = null; // TODO: Grab the vertex lighters from the ForgeBlockModelRenderer (and maybe some other places)

        lighter.setParent(new VertexBufferConsumer(builder));
        lighter.setWorld(te.getWorld());
        lighter.setState(Blocks.AIR.getDefaultState());
        lighter.setBlockPos(te.getPos());
        boolean empty = true;
        List<BakedQuad> quads = bakedModel.getQuads(null, null, 0);
        if(!quads.isEmpty())
        {
            lighter.updateBlockInfo();
            empty = false;
            for(BakedQuad quad : quads)
            {
                quad.pipe(lighter);
            }
        }
        for(EnumFacing side : EnumFacing.values())
        {
            quads = bakedModel.getQuads(null, side, 0);
            if(!quads.isEmpty())
            {
                if(empty) lighter.updateBlockInfo();
                empty = false;
                for(BakedQuad quad : quads)
                {
                    quad.pipe(lighter);
                }
            }
        }

        // debug quad
        /*VertexBuffer.pos(0, 1, 0).color(0xFF, 0xFF, 0xFF, 0xFF).tex(0, 0).lightmap(240, 0).endVertex();
        VertexBuffer.pos(0, 1, 1).color(0xFF, 0xFF, 0xFF, 0xFF).tex(0, 1).lightmap(240, 0).endVertex();
        VertexBuffer.pos(1, 1, 1).color(0xFF, 0xFF, 0xFF, 0xFF).tex(1, 1).lightmap(240, 0).endVertex();
        VertexBuffer.pos(1, 1, 0).color(0xFF, 0xFF, 0xFF, 0xFF).tex(1, 0).lightmap(240, 0).endVertex();*/

        builder.setTranslation(0, 0, 0);

        tessellator.draw();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }
}
