package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum EnumImbuedOre implements IStringSerializable
{
    EARTH(0, 0x00FF00),
    FIRE(1, 0xFF0000),
    WATER(2, 0x4444CC),
    WIND(3, 0xAAAA44),
    LEY(4, 0x3388CC),
    FEL(5, 0x88FF22),
    DEPLETED(6, 0x999999);

    private int meta;
    private int color;

    EnumImbuedOre(int meta, int color)
    {
        this.meta = meta;
        this.color = color;
    }

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
