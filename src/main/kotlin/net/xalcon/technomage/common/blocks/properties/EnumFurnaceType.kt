package net.xalcon.technomage.common.blocks.properties

import net.minecraft.util.IStringSerializable

enum class EnumFurnaceType : IStringSerializable
{
    BRICK(),
    NETHER(),
    BLAZE();

    override fun getName(): String = this.name.toLowerCase()
}