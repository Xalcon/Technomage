package net.xalcon.technomage.common.crafting;

import net.xalcon.technomage.common.crafting.alchemy.AlchemyRecipe;
import net.xalcon.technomage.common.crafting.amalgamation.AmalgamationRecipe;

public class Registries
{
    public final static RecipeRegistry<AlchemyRecipe> ALCHEMY = new RecipeRegistry<>();
    public final static RecipeRegistry<AmalgamationRecipe> AMALGAMATION = new RecipeRegistry<>();
}
