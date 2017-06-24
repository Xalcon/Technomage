package net.xalcon.technomage.lib.client.events;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ColorRegistrationEvent extends Event
{
    private ItemColors itemColors;
    private BlockColors blockColors;

    public ColorRegistrationEvent(ItemColors itemColors, BlockColors blockColors)
    {
        this.itemColors = itemColors;
        this.blockColors = blockColors;
    }

    public ItemColors getItemColors()
    {
        return this.itemColors;
    }

    public BlockColors getBlockColors()
    {
        return this.blockColors;
    }
}
