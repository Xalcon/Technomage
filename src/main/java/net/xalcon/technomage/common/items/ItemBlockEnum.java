package net.xalcon.technomage.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.xalcon.technomage.lib.item.IMetaToEnumConverter;

public class ItemBlockEnum<T extends Enum<T> & IStringSerializable> extends ItemBlock
{
    private IMetaToEnumConverter<T> converter;

    public ItemBlockEnum(Block block, IMetaToEnumConverter<T> converter)
    {
        super(block);
        this.setHasSubtypes(true);
        this.converter = converter;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + this.converter.getFromMeta(stack.getMetadata()).getName();
    }
}
