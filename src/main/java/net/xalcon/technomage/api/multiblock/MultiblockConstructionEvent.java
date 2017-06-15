package net.xalcon.technomage.api.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class MultiblockConstructionEvent extends PlayerEvent
{
    private IMultiblock multiblock;
    private BlockPos pos;

    public MultiblockConstructionEvent(IMultiblock multiblock, BlockPos pos, EntityPlayer player)
    {
        super(player);
        this.multiblock = multiblock;
        this.pos = pos;
    }

    public IMultiblock getMultiblock()
    {
        return this.multiblock;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
