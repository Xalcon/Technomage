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
            if(field.getType().getConstructor() != null)
                return (T) field.getType().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException e)
        {
            throw new RuntimeException(e);
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
            field.setAccessible(true);
            return (T) field.get(from);
        }
        catch (IllegalAccessException ignored)
        {
            // PIKACHU GO!!
        }
        return null;
    }
}
