package net.xalcon.technomage.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabTechnomage
{
    public static CreativeTabs Main = new CreativeTabs("technomage")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.ENCHANTED_BOOK);
        }
    };
}
