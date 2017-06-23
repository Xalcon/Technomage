package net.xalcon.technomage.api.gui;

import org.lwjgl.util.Rectangle;

@SuppressWarnings("EmptyMethod")
public interface IGuiComponent
{
    Rectangle getBounds();
    void renderComponent(int mouseX, int mouseY);
    void renderTooltip(int mouseX, int mouseY);

    void onMouseClicked(int mouseX, int mouseY, int mouseButton);
    void onMouseReleased(int mouseX, int mouseY, int state);
    void onMouseDragged(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);
}
