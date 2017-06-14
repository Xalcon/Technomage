package net.xalcon.technomage.api.gui.technonomicon;

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

    private int x;
    private int y;
    private float zoom;
    private boolean scrolling;

    protected int sortingIndex;
    protected boolean isVisible = true;
    protected ResourceLocation background = RESEARCH_BACKGROUND_DEFAULT;

    private List<IResearchViewItem> researchTree = new ArrayList<>();
    private Rectangle bounds = new Rectangle();

    public ResearchView(ResourceLocation registryName, IGuiIcon icon)
    {
        this.unlocalizedName = "technonomicon." + registryName.getResourceDomain() + "." + registryName.getResourcePath() + ".view_name";
        this.registryName = registryName;
        this.icon = icon;
    }

    @Override
    public Rectangle getBounds()
    {
        return this.bounds;
    }

    @Override
    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
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
    public boolean isTabVisible()
    {
        return true;
    }

    @Override
    public int getSortingIndex()
    {
        return this.sortingIndex;
    }

    @Override
    public void onShow(GuiScreen parent)
    {
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

    @Override
    public void renderComponent(int mouseX, int mouseY)
    {
        GuiHelper.INSTANCE.bindTexture(this.background);
        GuiHelper.INSTANCE.drawTexturedModalRect(this.bounds.getX(), this.bounds.getY(), -this.x, -this.y,
                this.bounds.getWidth(), this.bounds.getHeight(), 0);

        RenderHelper.enableGUIStandardItemLighting();
        for (IResearchViewItem item: this.researchTree)
            renderItem(item);
        RenderHelper.disableStandardItemLighting();
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
