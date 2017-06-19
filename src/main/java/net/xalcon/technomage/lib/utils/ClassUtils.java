package net.xalcon.technomage.lib.utils;

import java.lang.reflect.Field;

public class ClassUtils
{
    @SuppressWarnings("unchecked")
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
}
