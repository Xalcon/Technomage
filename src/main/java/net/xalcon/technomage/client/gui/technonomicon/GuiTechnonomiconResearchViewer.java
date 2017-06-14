package net.xalcon.technomage.client.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.technonomicon.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiTechnonomiconResearchViewer extends GuiScreen
{
    private final static ResourceLocation RESEARCH_TEX = new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/research.png");
    private final static int INDENT = 8;
    private List<IResearchView> viewList = new ArrayList<>();
    private IResearchView activeView = null;

    private int winScaleFactor;
    private int guiWidth;
    private int guiHeight;

    @Override
    public void initGui()
    {
        super.initGui();
        this.guiWidth = 256 + 96;
        this.guiHeight = 224;

        this.viewList.clear();

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

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test2"), new ItemStackGuiIcon(Items.DIAMOND));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_02.png"));
        this.viewList.add(view);

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test3"), new ItemStackGuiIcon(Items.REDSTONE));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_03.png"));
        this.viewList.add(view);

        view = new ResearchView(new ResourceLocation(Technomage.MOD_ID, "test4"), new ItemStackGuiIcon(Blocks.CRAFTING_TABLE));
        view.setBackgroundTexture(new ResourceLocation(Technomage.MOD_ID, "textures/gui/technonomicon/background_02.png"));
        this.viewList.add(view);

        this.activeView = this.viewList.get(0);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);
        this.winScaleFactor = new ScaledResolution(mc).getScaleFactor();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int left = (this.width - this.guiWidth) / 2;
        int top = (this.height - this.guiHeight) / 2;

        Point mousePos = new Point(mouseX - left, mouseY - top);
        List<IResearchView> list = this.viewList.stream()
                .filter(IResearchView::isVisible)
                .sorted(Comparator.comparingInt(IResearchView::getSortingIndex))
                .collect(Collectors.toList());
        int i = 0;
        for(IResearchView view : list)
        {
            Rectangle bounds = new Rectangle(0, 48 + 18 * i, 16, 16);
            if(bounds.contains(mousePos))
            {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.activeView = view;
                return;
            }
            i++;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
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

        if(this.activeView == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        // move context to top left corner of the gui, setup glScissor to only render the visible part of the view
        GlStateManager.translate(left, top, 0f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((left + INDENT) * this.winScaleFactor, (top + INDENT) * this.winScaleFactor,
                (this.guiWidth - INDENT * 2) * this.winScaleFactor, (this.guiHeight - INDENT * 2) * this.winScaleFactor);
        this.activeView.renderBackgroundLayer(viewport, relMousePos);
        this.activeView.renderForegroundLayer(viewport, relMousePos);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        this.drawBorder();
        this.drawTabs(relMousePos);
        if(viewport.contains(mouseX, mouseY))
            this.activeView.renderTooltipLayer(viewport, relMousePos);
        else
            this.handleTabMouseOver();

        GlStateManager.popMatrix();
    }

    private void drawTabs(Point mousePos)
    {
        List<IResearchView> list = this.viewList.stream()
                .filter(IResearchView::isVisible)
                .sorted(Comparator.comparingInt(IResearchView::getSortingIndex))
                .collect(Collectors.toList());
        int i = 0;
        RenderHelper.enableGUIStandardItemLighting();
        for(IResearchView view : list)
        {
            Rectangle bounds = new Rectangle(0, 48 + 18 * i, 16, 16);

            this.mc.getTextureManager().bindTexture(RESEARCH_TEX);
            if(view != this.activeView)
                GlStateManager.color(0.5f, 0.5f, 0.5f);
            this.drawTexturedModalRect(-16, 32 + 18 * i, 0, 0, 48, 48);
            GlStateManager.color(1f, 1f, 1f);
            if(bounds.contains(mousePos))
            {
                //GlStateManager.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ONE);
                drawRect(bounds.getX(), bounds.getY(), bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(), 0x88FFFFFF);
                //GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            }

            view.getIcon().renderAt(0, 48 + 18 * i);
            i++;
        }
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
