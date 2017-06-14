package net.xalcon.technomage.api.gui.technonomicon;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.xalcon.technolib.client.GuiHelper;

public class ItemStackGuiIcon implements IGuiIcon
{
    private final ItemStack icon;

    public ItemStackGuiIcon(ItemStack icon)
    {
        this.icon = icon;
    }

    public ItemStackGuiIcon(Item item)
    {
        this(new ItemStack(item));
    }

    public ItemStackGuiIcon(Block block)
    {
        this(new ItemStack(block));
    }

    @Override
    public void renderAt(int x, int y)
    {
        GuiHelper.INSTANCE.renderItemAndEffectIntoGUI(this.icon, x, y);
    }
}
