package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum TMMagicPlantType implements IStringSerializable, IMetaData
{
    INCINERLILY(0);

    private int meta;

    TMMagicPlantType(int meta)
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

    public static TMMagicPlantType getFromMeta(int meta)
    {
        return values()[meta % values().length];
    }
}
