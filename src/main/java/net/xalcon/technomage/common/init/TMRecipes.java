package net.xalcon.technomage.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.crafting.Registries;
import net.xalcon.technomage.common.crafting.alchemy.AlchemyRecipe;

public class TMRecipes
{
    public static void init()
    {
        Registries.ALCHEMY.register(new AlchemyRecipe(
                new ResourceLocation(Technomage.MOD_ID, "redstone_to_glowstone"),
                Ingredient.fromItem(Items.REDSTONE),
                new ItemStack(Items.GLOWSTONE_DUST)
        ));

        Registries.ALCHEMY.register(new AlchemyRecipe(
                new ResourceLocation(Technomage.MOD_ID, "sand_to_clay"),
                Ingredient.fromItem(Item.getItemFromBlock(Blocks.SAND)),
                new ItemStack(Items.CLAY_BALL)
        ));
    }
}
