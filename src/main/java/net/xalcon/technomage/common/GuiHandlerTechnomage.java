package net.xalcon.technomage.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.xalcon.technomage.client.gui.GuiConstructionTable;
import net.xalcon.technomage.client.gui.multiblocks.GuiBrickFurnace;
import net.xalcon.technomage.client.gui.technonomicon.GuiTechnonomiconResearchViewer;
import net.xalcon.technomage.common.container.ContainerBrickFurnace;
import net.xalcon.technomage.common.container.ContainerConstructionTable;
import net.xalcon.technomage.common.tileentities.TileEntityConstructionTable;

import javax.annotation.Nullable;

public class GuiHandlerTechnomage implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te;
        switch (ID)
        {
            case 1:
                return new ContainerBrickFurnace(player);
            case 2:
                te = world.getTileEntity(new BlockPos(x, y, z));
                if(te instanceof TileEntityConstructionTable)
                    return new ContainerConstructionTable(player, (TileEntityConstructionTable) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te;
        switch (ID)
        {
            case 0:
                return new GuiTechnonomiconResearchViewer();
            case 1:
                return new GuiBrickFurnace(player);
            case 2:
                te = world.getTileEntity(new BlockPos(x, y, z));
                if(te instanceof TileEntityConstructionTable)
                    return new GuiConstructionTable(player, (TileEntityConstructionTable) te);
        }
        return null;
    }
}
