package net.xalcon.technomage.api.gui.technonomicon;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.api.gui.IGuiComponent;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

public interface IResearchView extends IGuiComponent
{
    void setBounds(Rectangle bounds);
    String getUnlocalizedName();
    ResourceLocation getRegistryName();
    IGuiIcon getIcon();
    boolean isTabVisible();
    int getSortingIndex();
    void onShow(GuiScreen parent);
}
