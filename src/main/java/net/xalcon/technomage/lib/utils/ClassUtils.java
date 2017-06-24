package net.xalcon.technomage.lib.utils;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClassUtils
{
    @SuppressWarnings("unchecked")
    public static <T> T create(Field field)
    {
        try
        {
            if(field.getType().getConstructors().length == 1)
                return (T) field.getType().newInstance();

            Method factoryMethod = Arrays.stream(field.getType().getDeclaredMethods())
                .filter(m -> m.getAnnotation(InstanceFactoryMethod.class) != null)
                .findFirst().orElse(null);

            if(factoryMethod.getParameterCount() > 0)
            {
                Class<?> paramClass = factoryMethod.getParameterTypes()[0];
                for(Annotation annotation: field.getAnnotations())
                {
                    if(annotation.annotationType() == paramClass)
                    {
                        return (T) factoryMethod.invoke(null, annotation);
                    }
                }
            }
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Unable to create instance for "+ field.getType() +". No empty constructor found and no factory method present");
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
