package net.xalcon.technomage.common.init;

import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraftforge.common.DimensionManager;
import net.xalcon.technomage.common.world.WorldProviderDungeonDim;

public class TMDimensions
{
    public final static DimensionType DUNGEONS = DimensionType.register("tmdungeons", "_tmdun", -5400, WorldProviderDungeonDim.class, false);

    public static void init()
    {
        DimensionManager.registerDimension(-5400, DUNGEONS);

    }
}
