package net.xalcon.technomage.common.crafting.alchemy;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.common.crafting.IRegistryEntry;

public class AlchemyRecipe implements IRegistryEntry
{
    private ResourceLocation name;
    private Ingredient ingredient;
    private ItemStack output;


    public AlchemyRecipe(ResourceLocation name, Ingredient input, ItemStack output)
    {
        this.name = name;
        this.ingredient = input;
        this.output = output;
    }

    @Override
    public ResourceLocation getResourceLocation()
    {
        return this.name;
    }

    public boolean isMatch(ItemStack input)
    {
        return this.ingredient.apply(input);
    }

    public ItemStack getOutput()
    {
        return this.output.copy();
    }
}
