package net.xalcon.technomage.client.renderer.block;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

public class TileEntityDisplayNameRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
    private boolean alwaysShowNameplate;

    public TileEntityDisplayNameRenderer(boolean alwaysShowNameplate)
    {
        this.alwaysShowNameplate = alwaysShowNameplate;
    }

    @Override
    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        ITextComponent itextcomponent = te.getDisplayName();
        if(itextcomponent != null && (this.alwaysShowNameplate || this.rendererDispatcher.cameraHitResult != null && te.getPos().equals(this.rendererDispatcher.cameraHitResult.getBlockPos())))
        {
            this.setLightmapDisabled(true);
            this.drawNameplate(te, itextcomponent.getFormattedText(), x, y, z, 12);
            this.setLightmapDisabled(false);
        }
    }
}
