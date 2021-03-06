package net.xalcon.technomage.api.gui.technonomicon;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.Rectangle;

@SuppressWarnings("EmptyMethod")
public interface IResearchViewItem
{
    Rectangle getBounds();
    TechnonomiconTooltip getTooltip();
    ResearchState getResearchState();
    ResourceLocation getRegistryName();
    IGuiIcon getIcon();
    ResearchIconType getResearchIconType();
    void onClicked();
}
