package net.xalcon.technomage.client.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technolib.client.GuiHelper;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.gui.IGuiComponent;
import net.xalcon.technomage.api.gui.technonomicon.IResearchView;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiTechnonomiconTabList implements IGuiComponent
{
    private final static ResourceLocation RESEARCH_TEX = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png");
    private final static int TAB_SIZE = 18;
    private List<IResearchView> tabs = new ArrayList<>();
    private IResearchView activeView;
    private int left;
    private int top;

    public IResearchView getActiveView()
    {
        if(this.activeView == null && this.tabs.size() > 0)
            this.activeView = this.tabs.get(0);
        return this.activeView;
    }

    public void updateTabList(List<IResearchView> viewsList)
    {
        this.tabs.clear();
        this.tabs.addAll(viewsList);
        if(this.tabs.size() > 0)
            this.activeView = this.tabs.get(0);
    }

    public void setPosition(int left, int top)
    {
        this.left = left;
        this.top = top;
    }

    @Override
    public Rectangle getBounds()
    {
        int c = (int)this.tabs.stream().filter(IResearchView::isTabVisible).count();
        return new Rectangle(this.left, this.top, TAB_SIZE, TAB_SIZE * c);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY)
    {
        List<IResearchView> list = this.tabs.stream()
                .filter(IResearchView::isTabVisible)
                .sorted(Comparator.comparingInt(IResearchView::getSortingIndex))
                .collect(Collectors.toList());
        int i = 0;
        RenderHelper.enableGUIStandardItemLighting();
        for(IResearchView view : list)
        {
            Rectangle bounds = new Rectangle(this.left, this.top + 48 + 18 * i, 16, 16);

            if(view != this.activeView)
                GlStateManager.color(0.5f, 0.5f, 0.5f);

            Minecraft.getMinecraft().getTextureManager().bindTexture(RESEARCH_TEX);
            GuiHelper.INSTANCE.drawTexturedModalRect(this.left - 16, this.top + 32 + 18 * i, 0, 0, 48, 48, 0);

            if(bounds.contains(mouseX, mouseY))
                Gui.drawRect(bounds.getX(), bounds.getY(), bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(), 0x88FFFFFF);

            view.getIcon().renderAt(bounds.getX(), bounds.getY());
            i++;
        }
    }

    @Override
    public void renderTooltip(int mouseX, int mouseY)
    {
    }

    @Override
    public void onMouseClicked()
    {
    }

    @Override
    public void onMouseReleased()
    {
    }

    @Override
    public void onMouseDragged()
    {
    }
}
