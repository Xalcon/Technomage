package net.xalcon.technomage.common.items;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemSword extends net.minecraft.item.ItemSword
{
    private double attackDamage;

    public ItemSword()
    {
        super(ToolMaterial.WOOD);
        this.attackDamage = 100;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            AttributeModifier atkDmgMod = new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0);
            AttributeModifier atkSpdMod = new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 110.0D, 0);
            multimap.remove(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), atkDmgMod);
            multimap.remove(SharedMonsterAttributes.ATTACK_SPEED.getName(), atkSpdMod);

            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), atkDmgMod);
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), atkSpdMod);
        }

        return multimap;
    }
}
