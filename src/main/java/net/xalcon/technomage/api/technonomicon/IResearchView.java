package net.xalcon.technomage.api.technonomicon;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

public interface IResearchView
{
    String getUnlocalizedName();
    ResourceLocation getRegistryName();
    IGuiIcon getIcon();
    boolean isVisible();
    int getSortingIndex();
    void renderBackgroundLayer(Rectangle viewport, Point mousePos);
    void renderForegroundLayer(Rectangle viewport, Point mousePos);
    void renderTooltipLayer(Rectangle viewport, Point mousePos);
    void onShow(GuiScreen parent);
}
