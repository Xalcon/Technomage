package net.xalcon.technomage.api.gui;

import org.lwjgl.util.Rectangle;

public interface IGuiComponent
{
    Rectangle getBounds();
    void renderComponent(int mouseX, int mouseY);
    void renderTooltip(int mouseX, int mouseY);

    void onMouseClicked();
    void onMouseReleased();
    void onMouseDragged();
}
