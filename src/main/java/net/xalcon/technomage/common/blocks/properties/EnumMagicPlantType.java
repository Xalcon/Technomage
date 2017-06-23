package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum EnumMagicPlantType implements IStringSerializable, IMetaBlock
{
    INCINERLILY(0);

    private int meta;

    EnumMagicPlantType(int meta)
    {
        this.meta = meta;
    }

    @Override
    public int getMeta()
    {
        return this.meta;
    }

    @Override
    public String getName()
    {
        return this.name().toLowerCase();
    }

    public static EnumMagicPlantType getFromMeta(int meta)
    {
        return values()[meta % values().length];
    }
}