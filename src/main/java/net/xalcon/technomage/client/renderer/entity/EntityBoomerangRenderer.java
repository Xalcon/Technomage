package net.xalcon.technomage.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.common.entities.EntityBoomerang;
import net.xalcon.technomage.common.init.TMItems;

import javax.annotation.Nullable;

public class EntityBoomerangRenderer extends Render<EntityBoomerang>
{
    public EntityBoomerangRenderer(RenderManager renderManager)
    {
        super(renderManager);
    }

    /**
     * Renders the desired {@code T} type Entity.
     *
     * @param entity
     * @param x
     * @param y
     * @param z
     * @param entityYaw
     * @param partialTicks
     */
    @Override
    public void doRender(EntityBoomerang entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + .2f, (float)z);
        GlStateManager.enableRescaleNormal();
        //GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float rot = ((entity.ticksExisted + partialTicks) % 5f) * 4f * 18f;
        GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(TMItems.boomerang()), ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     *
     * @param entity
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBoomerang entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
