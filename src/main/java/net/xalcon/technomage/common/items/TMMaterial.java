package net.xalcon.technomage.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.EnumHelper;
import net.xalcon.technomage.common.blocks.properties.IMetaData;

public enum TMMaterial implements IStringSerializable, IMetaData
{
    ARKANIUM("arkanium", 0, 640, 2, 6.5f, 2.5f, 18),
    ;

    private int meta;
    private int durability;
    private String name;
    private Item.ToolMaterial toolMaterial;
    //private ItemArmor.ArmorMaterial armorMaterial;

    TMMaterial(String name, int meta, int durability, int harvestLevel, float efficiency, float damage, int enchantability)
    {
        this.name = name;
        this.meta = meta;
        this.durability = durability;
        this.toolMaterial = EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, damage, enchantability);
        //this.armorMaterial = EnumHelper.addArmorMaterial(name, "", durability, new int[]{2, 5, 6, 2}, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
    }

    public int getDurability()
    {
        return this.durability;
    }

    @Override
    public int getMeta()
    {
        return this.meta;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public Item.ToolMaterial getToolMaterial()
    {
        return this.toolMaterial;
    }
}
