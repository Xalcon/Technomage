package net.xalcon.technomage.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.container.ContainerConstructionTable;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

public class GuiConstructionTable extends GuiContainer
{
    private final static ResourceLocation TEXTURE = new ResourceLocation(Technomage.MOD_ID, "textures/gui/construction_table.png");

    public GuiConstructionTable(EntityPlayer player, TileEntityConstructionTable tile)
    {
        super(new ContainerConstructionTable(player, tile));
        this.xSize = 176;
        this.ySize = 224;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.drawDefaultBackground();

        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 32, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.renderHoveredToolTip(mouseX - this.guiLeft, mouseY - this.guiTop);
    }
}
