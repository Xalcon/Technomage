package net.xalcon.technomage.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.init.TMItems;

public class CreativeTabsTechnomage
{
    public final static CreativeTabs tabMain = new CreativeTabs(Technomage.MOD_ID + ".main")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(TMItems.technonomicon);
        }
    };
}
