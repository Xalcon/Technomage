package net.xalcon.technomage.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportDungeonDim extends Teleporter
{
    public TeleportDungeonDim(WorldServer worldIn)
    {
        super(worldIn);
    }

    @Override
    public boolean makePortal(Entity entityIn)
    {
        return true;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime)
    {
    }
}
