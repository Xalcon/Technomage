package net.xalcon.technomage.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.xalcon.technomage.Technomage;

public class CreativeTabsTechnomage
{
    public final static CreativeTabs tabMain = new CreativeTabs(Technomage.MOD_ID + ".main")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.ENCHANTED_BOOK);
        }
    };
}
