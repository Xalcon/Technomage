package net.xalcon.technomage.api.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.lib.client.GuiHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
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
    private int max_width = 512;
    private int max_height = 512;
    private float zoom = 1;
    private int lastMouseX;
    private int lastMouseY;

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
        this.setToCenter();
    }

    public void setToCenter()
    {
        this.x = -((this.max_width - this.bounds.getWidth()) / 2);
        this.y = -((this.max_height - this.bounds.getHeight()) / 2);
        this.zoom = 1;
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

    private void renderItem(IResearchViewItem item)
    {
        GuiHelper.bindTexture(RESEARCH_TEXTURE);
        Rectangle itemBounds = item.getBounds();

        int rx = (int) ((this.x + itemBounds.getX()) * this.zoom + this.bounds.getX());
        int ry = (int) ((this.y + itemBounds.getY()) * this.zoom + this.bounds.getY());
        GuiHelper.drawTexturedModalRect( rx - 5, ry - 5, 42 + 26 * item.getResearchIconType().getIndex(), 107, 26, 26, 0);
        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        item.getIcon().renderAt(rx, ry);
    }

    @Override
    public void renderComponent(int mouseX, int mouseY)
    {
        int wheel = Mouse.getDWheel();
        if (wheel > 0)
            this.zoom += 0.10f;
        else if (wheel < 0)
            this.zoom -= 0.10f;
        this.zoom = MathHelper.clamp(this.zoom, 0.5f, 1.0f);

        GlStateManager.pushMatrix();
        GlStateManager.scale(this.zoom, this.zoom, this.zoom);
        GuiHelper.bindTexture(this.background);
        GuiHelper.drawTexturedModalRect(this.bounds.getX() / this.zoom, this.bounds.getY() / this.zoom, -this.x, -this.y,
                (int)(this.bounds.getWidth() * this.zoom), (int)(this.bounds.getHeight() * this.zoom), 0);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.x + "/" + this.y, 0, 0, -1);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        RenderHelper.enableGUIStandardItemLighting();
        for (IResearchViewItem item: this.researchTree)
            this.renderItem(item);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

    }

    @Override
    public void renderTooltip(int mouseX, int mouseY)
    {

    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int state)
    {

    }

    @Override
    public void onMouseDragged(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        int deltaX = mouseX - this.lastMouseX;
        int deltaY = mouseY - this.lastMouseY;

        this.x += deltaX / this.zoom;
        this.y += deltaY / this.zoom;

        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }
}
