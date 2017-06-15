package net.xalcon.technomage.client.gui.multiblocks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.xalcon.technomage.common.container.ContainerBrickFurnace;

public class GuiBrickFurnace extends GuiContainer
{
    public GuiBrickFurnace(EntityPlayer player)
    {
        super(new ContainerBrickFurnace(player));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
    }
}
