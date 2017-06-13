package net.xalcon.technomage.api.technonomicon;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technolib.client.GuiHelper;
import net.xalcon.technomage.Technomage;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ResearchView implements IResearchView
{
    private final static ResourceLocation RESEARCH_TEXTURE = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png");
    private final static ResourceLocation RESEARCH_BACKGROUND_DEFAULT = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_01.png");

    private String unlocalizedName;
    private ResourceLocation registryName;
    private IGuiIcon icon;
    protected int sortingIndex;
    protected boolean isVisible = true;
    protected ResourceLocation background = RESEARCH_BACKGROUND_DEFAULT;

    private GuiScreen parent;
    private List<IResearchViewItem> researchTree = new ArrayList<>();

    public ResearchView(ResourceLocation registryName, IGuiIcon icon)
    {
        this.unlocalizedName = "technonomicon." + registryName.getResourceDomain() + "." + registryName.getResourcePath() + ".view_name";
        this.registryName = registryName;
        this.icon = icon;
    }

    @Override
    public String getUnlocalizedName()
    {
        return this.unlocalizedName;
    }

    @Override
    public final ResourceLocation getRegistryName()
    {
        return this.registryName;
    }

    @Override
    public IGuiIcon getIcon()
    {
        return this.icon;
    }

    @Override
    public boolean isVisible()
    {
        return this.isVisible;
    }

    @Override
    public int getSortingIndex()
    {
        return this.sortingIndex;
    }

    @Override
    public void renderBackgroundLayer(Rectangle viewport, Point mousePos)
    {
        GuiHelper.INSTANCE.bindTexture(this.background);
        GuiHelper.INSTANCE.drawTexturedModalRect(0, 0, -viewport.getX(), -viewport.getY(),
                viewport.getWidth(), viewport.getHeight(), 0);
    }

    @Override
    public void renderForegroundLayer(Rectangle viewport, Point mousePos)
    {
        RenderHelper.enableGUIStandardItemLighting();
        for (IResearchViewItem item: this.researchTree)
            renderItem(item);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public void renderTooltipLayer(Rectangle viewport, Point mousePos)
    {
    }

    @Override
    public void onShow(GuiScreen parent)
    {
        this.parent = parent;
    }

    public void setBackgroundTexture(ResourceLocation location)
    {
        this.background = location;
    }

    public void addResearchItem(IResearchViewItem item)
    {
        if(this.researchTree.stream().noneMatch(i -> i.getRegistryName().equals(item.getRegistryName())))
            this.researchTree.add(item);
    }

    private static void renderItem(IResearchViewItem item)
    {
        GuiHelper.INSTANCE.bindTexture(RESEARCH_TEXTURE);
        Rectangle bounds = item.getBounds();
        GuiHelper.INSTANCE.drawTexturedModalRect(bounds.getX() - 5, bounds.getY() - 5, 42 + 26 * item.getResearchIconType().getIndex(), 107, 26, 26, 0);
        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        item.getIcon().renderAt(bounds.getX(), bounds.getY());
    }
}
