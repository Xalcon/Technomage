package net.xalcon.technomage.lib.tiles;

import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.*;

@Repeatable(HasTileEntities.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasTileEntity
{
    Class<? extends TileEntity> teClass();
    String name() default "";
}
