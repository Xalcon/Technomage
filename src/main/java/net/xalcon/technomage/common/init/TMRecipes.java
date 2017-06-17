package net.xalcon.technomage.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.common.crafting.Registries;
import net.xalcon.technomage.common.crafting.alchemy.AlchemyRecipe;
import net.xalcon.technomage.common.crafting.amalgamation.AmalgamationRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

        ItemStack output = new ItemStack(Items.DIAMOND_PICKAXE, 1, 0);
        output.addEnchantment(Enchantment.getEnchantmentByLocation("fortune"), 5);
        Registries.AMALGAMATION.register(new AmalgamationRecipe(
                new ResourceLocation(Technomage.MOD_ID, "fortune_v_diamond_pickaxe"),
                output,
                Ingredient.fromItem(Items.DIAMOND_PICKAXE),
                Arrays.asList(
                        Ingredient.fromItem(Items.DIAMOND),
                        Ingredient.fromItem(Items.DIAMOND),
                        Ingredient.fromItem(Items.GLOWSTONE_DUST),
                        Ingredient.fromItem(Items.GLOWSTONE_DUST)
                )
        ));

        Registries.AMALGAMATION.register(new AmalgamationRecipe(
                new ResourceLocation(Technomage.MOD_ID, "balanced_clay"),
                new ItemStack(Items.CLAY_BALL),
                Ingredient.fromItem(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK)),
                Arrays.asList(
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.SAND)),
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.SAND)),
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.SAND)),
                        Ingredient.fromItem(Item.getItemFromBlock(Blocks.SAND))
                )
        ));
    }
}
