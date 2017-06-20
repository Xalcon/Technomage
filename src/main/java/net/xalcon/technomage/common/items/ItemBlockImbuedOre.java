package net.xalcon.technomage.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.xalcon.technomage.common.blocks.properties.EnumImbuedOre;
import net.xalcon.technomage.common.init.TMBlocks;

public class ItemBlockImbuedOre extends ItemBlock
{
    public ItemBlockImbuedOre(Block block)
    {
        super(block);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + EnumImbuedOre.getFromMeta(stack.getMetadata()).getName();
    }
}
