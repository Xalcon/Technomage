package net.xalcon.technomage.client.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.technonomicon.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GuiTechnonomiconResearchViewer extends GuiScreen
{
    private final static ResourceLocation RESEARCH_TEX = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png");
    private final static int INDENT = 8;
    private List<IResearchView> viewList = new ArrayList<>();

    private int winScaleFactor;
    private int guiWidth;
    private int guiHeight;

    @Override
    public void initGui()
    {
        super.initGui();
        this.guiWidth = 256 + 96;
        this.guiHeight = 224;

        ResearchView view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test"), new ItemStackGuiIcon(Items.GOLD_NUGGET));
        view.addResearchItem(new IResearchViewItem()
        {
            @Override
            public Rectangle getBounds()
            {
                return new Rectangle(64, 64, 16, 16);
            }

            @Override
            public TechnonomiconTooltip getTooltip()
            {
                return new TechnonomiconTooltip("ITEM!", "Yep, just a test.");
            }

            @Override
            public ResearchState getResearchState()
            {
                return ResearchState.NOT_RESEARCHED;
            }

            @Override
            public ResourceLocation getRegistryName()
            {
                return new ResourceLocation(Technomage.MOD_ID, "testitem");
            }

            @Override
            public IGuiIcon getIcon()
            {
                return new ItemStackGuiIcon(Items.GOLDEN_PICKAXE);
            }

            @Override
            public ResearchIconType getResearchIconType()
            {
                return ResearchIconType.DEFAULT;
            }

            @Override
            public void onClicked()
            {
            }
        });
        this.viewList.add(view);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);
        this.winScaleFactor = new ScaledResolution(mc).getScaleFactor();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int left = (this.width - this.guiWidth) / 2;
        int top = (this.height - this.guiHeight) / 2;

        Rectangle viewport = new Rectangle(left, top, this.guiWidth, this.guiHeight);
        Point relMousePos = new Point(mouseX - left, mouseY - top);
        this.drawDefaultBackground();

        IResearchView view = this.viewList.stream().findFirst().orElse(null);
        if(view == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        // move context to top left corner of the gui, setup glScissor to only render the visible part of the view
        GlStateManager.translate(left, top, 0f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((left + INDENT) * this.winScaleFactor, (top + INDENT) * this.winScaleFactor,
                (this.guiWidth - INDENT * 2) * this.winScaleFactor, (this.guiHeight - INDENT * 2) * this.winScaleFactor);
        view.renderBackgroundLayer(viewport, relMousePos);
        view.renderForegroundLayer(viewport, relMousePos);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        this.drawTabs();
        this.drawBorder();
        if(viewport.contains(mouseX, mouseY))
            view.renderTooltipLayer(viewport, relMousePos);
        else
            this.handleTabMouseOver();

        GlStateManager.popMatrix();
    }

    private void drawTabs()
    {
    }

    private void handleTabMouseOver()
    {
    }

    private void drawBorder()
    {
        this.mc.getTextureManager().bindTexture(RESEARCH_TEX);

        // we need to substract 16 from the x and y positions because of the shadow effect on the border texture
        int hCount = 1 + (this.guiWidth - 16) / 64;
        for (int i = 0; i < hCount; i++)
        {
            int rW = Math.min(64, this.guiWidth - 32 - i * 64);
            this.drawTexturedModalRect(16 + i * 64, 0, 48, 16, rW, 16);
            this.drawTexturedModalRect(16 + i * 64, this.guiHeight - 16, 48, 16, rW, 16);
        }

        int vCount = 1 + (this.guiHeight - 16) / 64;
        for (int i = 0; i < vCount; i++)
        {
            int rH = Math.min(64, this.guiHeight - 32 - i * 64);
            this.drawTexturedModalRect(0, 16 + i * 64, 16, 48, 16, rH);
            this.drawTexturedModalRect(this.guiWidth - 16, 16 + i * 64, 16, 48, 16, rH);
        }

        // TOP LEFT
        this.drawTexturedModalRect(-16, -16, 0, 0, 48, 48);
        // TOP RIGHT
        this.drawTexturedModalRect(this.guiWidth - 32, -16, 0, 0, 48, 48);
        // BOTTOM LEFT
        this.drawTexturedModalRect(-16, this.guiHeight - 32, 0, 0, 48, 48);
        // BOTTOM RIGHT
        this.drawTexturedModalRect(this.guiWidth - 32, this.guiHeight - 32, 0, 0, 48, 48);
    }
}
