package net.xalcon.technomage.client.colors;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.blocks.properties.TMImbuedOreType;

public class ImbuedShardColor implements IItemColor
{
    public final static ImbuedShardColor INSTANCE = new ImbuedShardColor();

    private ImbuedShardColor() { }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tintIndex)
    {
        return tintIndex == 0 ? TMImbuedOreType.getFromMeta(stack.getMetadata()).getColor() : -1;
    }
}
