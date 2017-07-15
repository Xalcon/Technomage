package net.xalcon.technomage.common.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.xalcon.technomage.lib.item.IItemBlockProvider;

public class BlockLeylightBoreBase extends Block implements IItemBlockProvider
{
    public final static String INTERNAL_NAME = "leylight_bore_base";

    public BlockLeylightBoreBase()
    {
        super(Material.WOOD);
        this.setHardness(1f);
        this.setResistance(10f);
    }
}
