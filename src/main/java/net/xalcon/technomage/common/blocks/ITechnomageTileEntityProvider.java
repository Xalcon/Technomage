package net.xalcon.technomage.common.blocks;

import net.minecraft.block.ITileEntityProvider;

public interface ITechnomageTileEntityProvider extends ITileEntityProvider
{
    void registerTileEntities();
}
