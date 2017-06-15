package net.xalcon.technomage.api.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.xalcon.technomage.Technomage;

import java.util.ArrayList;
import java.util.List;

public class MultiblockRegistry
{
    private static ArrayList<IMultiblock> multiblocks = new ArrayList<>();

    public static void register(IMultiblock multiblock)
    {
        if(multiblocks.stream().anyMatch(m -> m.getName() == multiblock))
        {
            Technomage.Log.error("cannot to register ${multiblock.getName()} twice.");
            return;
        }

        multiblocks.add(multiblock);
    }

    public static List<IMultiblock> getMultiblocks()
    {
        return multiblocks;
    }

    public static MultiblockConstructionEvent postConstructionEvent(IMultiblock multiblock, BlockPos pos, EntityPlayer player)
    {
        MultiblockConstructionEvent event = new MultiblockConstructionEvent(multiblock, pos, player);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
