package net.xalcon.technomage.lib.utils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ClassUtils
{
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T create(Field field)
    {
        try
        {
            return (T) field.getType().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <T> T getOrNull(Field field)
    {
        return getOrNull(field, null);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getOrNull(Field field, @Nullable Object from)
    {
        try
        {
            return (T) field.get(from);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
