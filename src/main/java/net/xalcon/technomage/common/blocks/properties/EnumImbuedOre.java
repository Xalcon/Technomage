package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum EnumImbuedOre implements IStringSerializable, IMetaBlock
{
    EARTH(0, 0x00CC00),
    FIRE(1, 0xFF0000),
    WATER(2, 0x44AAFF),
    WIND(3, 0xFFFF00),
    LEY(4, 0x6666FF),
    FEL(5, 0x99FF44),
    DEPLETED(6, 0x999999);

    private int meta;
    private int color;

    EnumImbuedOre(int meta, int color)
    {
        this.meta = meta;
        this.color = color;
    }

    @Override
    public int getMeta()
    {
        return this.meta;
    }

    public int getColor()
    {
        return this.color;
    }

    @Override
    public String getName()
    {
        return this.name().toLowerCase();
    }

    public static EnumImbuedOre getFromMeta(int meta)
    {
        return EnumImbuedOre.values()[meta % EnumImbuedOre.values().length];
    }
}
