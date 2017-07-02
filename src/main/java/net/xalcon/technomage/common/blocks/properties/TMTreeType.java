package net.xalcon.technomage.common.blocks.properties;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum TMTreeType implements IStringSerializable, IMetaData
{
    ELDER(0,MapColor.WOOD, 0xFF33AA22),
    LEY(1, MapColor.CLOTH, 0xFF0066AA),
    FEL(2, MapColor.WOOD, 0xFFAAAA00),
    BAMBOO(3, MapColor.FOLIAGE, 0xFF66FF00),
    // Disabled due to meta size limitations, need to implement some form of paging
    // i.e. like BoP is doing
    //ABYSSAL_OAK(4, MapColor.OBSIDIAN),
    ;

    private int meta;
    private MapColor mapColor;
    private int leafColor;

    TMTreeType(int meta, MapColor mapColor, int leafColor)
    {
        this.meta = meta;
        this.mapColor = mapColor;
        this.leafColor = leafColor;
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

    public int getLeafColor()
    {
        return this.leafColor;
    }

    @Override
    public String getName()
    {
        return this.name().toLowerCase();
    }

    public static TMTreeType getFromMeta(int meta)
    {
        return values()[meta % values().length];
    }
}
