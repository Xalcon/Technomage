package net.xalcon.technomage.common.crafting;

import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RecipeRegistry<T extends IRecipeEntry>
{
    private Map<ResourceLocation, T> recipes = new HashMap<>();

    public void register(T recipe)
    {
        this.recipes.put(recipe.getResourceLocation(), recipe);
    }

    public Collection<T> getRecipes() { return this.recipes.values(); }
}
