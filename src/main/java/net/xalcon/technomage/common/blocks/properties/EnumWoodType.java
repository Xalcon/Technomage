package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum EnumWoodType implements IStringSerializable, IBlockMeta
{
    ELDER(0,MapColor.WOOD),
    LEY(1, MapColor.CLOTH),
    FEL(2, MapColor.WOOD),
    BAMBOO(3, MapColor.FOLIAGE),
    // Disabled due to meta size limitations, need to implement some form of paging
    // i.e. like BoP is doing
    //ABYSSAL_OAK(4, MapColor.OBSIDIAN),
    ;

    private int meta;
    private MapColor mapColor;

    EnumWoodType(int meta, MapColor mapColor)
    {
        this.meta = meta;
        this.mapColor = mapColor;
    }

    @Override
    public int getMeta()
    {
        return this.meta;
    }

    public MapColor getMapColor()
    {
        return this.mapColor;
    }

    @Override
    public String getName()
    {
        return this.name().toLowerCase();
    }

    public static EnumWoodType getFromMeta(int meta)
    {
        return values()[meta % values().length];
    }
}
