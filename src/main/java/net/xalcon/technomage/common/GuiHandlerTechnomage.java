package net.xalcon.technomage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.xalcon.technomage.client.gui.multiblocks.GuiBrickFurnace;
import net.xalcon.technomage.client.gui.technonomicon.GuiTechnonomiconResearchViewer;
import net.xalcon.technomage.common.container.ContainerBrickFurnace;

import javax.annotation.Nullable;

public class GuiHandlerTechnomage implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 1:
                return new ContainerBrickFurnace(player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0:
                return new GuiTechnonomiconResearchViewer();
            case 1:
                return new GuiBrickFurnace(player);
        }
        return null;
    }
}
