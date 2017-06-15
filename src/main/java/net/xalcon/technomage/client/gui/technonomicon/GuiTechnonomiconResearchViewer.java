package net.xalcon.technomage.client.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.gui.technonomicon.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiTechnonomiconResearchViewer extends GuiScreen
{
    private final static ResourceLocation RESEARCH_TEX = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png");
    private final static int INDENT = 8;
    private List<IResearchView> viewList = new ArrayList<>();
    private GuiTechnonomiconTabList tabList = new GuiTechnonomiconTabList();

    private int winScaleFactor;
    private int guiWidth;
    private int guiHeight;
    private int guiLeft;
    private int guiTop;

    @Override
    public void initGui()
    {
        super.initGui();
        this.guiWidth = 256 + 96;
        this.guiHeight = 224;

        this.guiLeft = (this.width - this.guiWidth) / 2;
        this.guiTop = (this.height - this.guiHeight) / 2;

        this.viewList.clear();

        ResearchView view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test"), new ItemStackGuiIcon(Items.GOLD_NUGGET));
        view.addResearchItem(new IResearchViewItem()
        {
            @Override
            public Rectangle getBounds()
            {
                return new Rectangle(256, 256, 16, 16);
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

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test2"), new ItemStackGuiIcon(Items.DIAMOND));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_02.png"));
        this.viewList.add(view);

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test3"), new ItemStackGuiIcon(Items.REDSTONE));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_03.png"));
        this.viewList.add(view);

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test4"), new ItemStackGuiIcon(Blocks.CRAFTING_TABLE));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_02.png"));
        this.viewList.add(view);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);
        this.winScaleFactor = new ScaledResolution(mc).getScaleFactor();

        this.viewList.forEach(v -> v.setBounds(new Rectangle(this.guiLeft, this.guiTop, this.guiWidth, this.guiHeight)));
        this.tabList.setPosition(this.guiLeft, this.guiTop);
        this.tabList.updateTabList(this.viewList);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(this.tabList.getBounds().contains(mouseX, mouseY))
            this.tabList.onMouseClicked(mouseX, mouseY, mouseButton);
        else if(this.tabList.getActiveView().getBounds().contains(mouseX, mouseY))
            this.tabList.getActiveView().onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        if(this.tabList.getBounds().contains(mouseX, mouseY))
            this.tabList.onMouseReleased(mouseX, mouseY, state);
        else if(this.tabList.getActiveView().getBounds().contains(mouseX, mouseY))
            this.tabList.getActiveView().onMouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if(this.tabList.getBounds().contains(mouseX, mouseY))
            this.tabList.onMouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        else if(this.tabList.getActiveView().getBounds().contains(mouseX, mouseY))
            this.tabList.getActiveView().onMouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawDefaultBackground();
        GlStateManager.enableBlend();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        // setup glScissor to only render the visible part of the view
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((this.guiLeft + INDENT) * this.winScaleFactor, (this.guiTop + INDENT) * this.winScaleFactor,
                (this.guiWidth - INDENT * 2) * this.winScaleFactor, (this.guiHeight - INDENT * 2) * this.winScaleFactor);
        IResearchView activeView = this.tabList.getActiveView();
        if(activeView != null)
        {
            activeView.renderComponent(mouseX, mouseY);
        }
        else
        {
            Gui.drawRect(this.guiLeft, this.guiTop, this.guiWidth, this.guiHeight, 0);
            GlStateManager.color(1f, 1f, 1f);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


        this.drawBorder();
        this.tabList.renderComponent(mouseX, mouseY);
        if(activeView != null && activeView.getBounds().contains(mouseX, mouseY))
            activeView.renderTooltip(mouseX, mouseY);

    }

    private void drawBorder()
    {
        int left = (this.width - this.guiWidth) / 2;
        int top = (this.height - this.guiHeight) / 2;
        this.mc.getTextureManager().bindTexture(RESEARCH_TEX);

        // we need to substract 16 from the x and y positions because of the shadow effect on the border texture
        int hCount = 1 + (this.guiWidth - 16) / 64;
        for (int i = 0; i < hCount; i++)
        {
            int rW = Math.min(64, this.guiWidth - 32 - i * 64);
            this.drawTexturedModalRect(left + 16 + i * 64, top, 48, 16, rW, 16);
            this.drawTexturedModalRect(left + 16 + i * 64, top + this.guiHeight - 16, 48, 16, rW, 16);
        }

        int vCount = 1 + (this.guiHeight - 16) / 64;
        for (int i = 0; i < vCount; i++)
        {
            int rH = Math.min(64, this.guiHeight - 32 - i * 64);
            this.drawTexturedModalRect(left, top + 16 + i * 64, 16, 48, 16, rH);
            this.drawTexturedModalRect(left + this.guiWidth - 16, top + 16 + i * 64, 16, 48, 16, rH);
        }

        // TOP LEFT
        this.drawTexturedModalRect(left + -16, top + -16, 0, 0, 48, 48);
        // TOP RIGHT
        this.drawTexturedModalRect(left + this.guiWidth - 32, top + -16, 0, 0, 48, 48);
        // BOTTOM LEFT
        this.drawTexturedModalRect(left + -16, top + this.guiHeight - 32, 0, 0, 48, 48);
        // BOTTOM RIGHT
        this.drawTexturedModalRect(left + this.guiWidth - 32, top + this.guiHeight - 32, 0, 0, 48, 48);
    }
}
