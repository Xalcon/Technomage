package net.xalcon.technomage.lib.item;

import net.minecraft.util.IStringSerializable;

public interface IMetaToEnumConverter<T extends Enum<T> & IStringSerializable>
{
    T getFromMeta(int meta);
}
