package net.xalcon.technomage.common.tileentities.multiblock;

public class TileEntityBrickFurnace extends TileEntityTMMultiblockPart<TileEntityBrickFurnace>
{
    public boolean isLower()
    {
        return this.isLower;
    }

    public void setLower(boolean lower)
    {
        this.isLower = lower;
    }

    public boolean isActive()
    {
        return this.isActive;
    }

    public void setActive(boolean active)
    {
        this.isActive = active;
    }

    private boolean isLower = false;
    private boolean isActive = false;
}
