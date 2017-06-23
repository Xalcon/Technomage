package net.xalcon.technomage.common.crafting.amalgamation;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technomage.common.crafting.IRecipeEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmalgamationRecipe implements IRecipeEntry
{
    private final ResourceLocation name;
    private final ItemStack output;
    private final Ingredient primaryItem;
    private final List<Ingredient> secondaryItems;

    public AmalgamationRecipe(ResourceLocation recipeName, ItemStack output, Ingredient primaryItem, List<Ingredient> secondaryItems)
    {
        this.name = recipeName;
        this.primaryItem = primaryItem;
        this.secondaryItems = secondaryItems;
        this.output = output;
    }

    public ItemStack getOutput()
    {
        return this.output.copy();
    }

    public boolean isMatch(ItemStack coreItem, List<ItemStack> pedestalInputs)
    {
        if(!this.primaryItem.apply(coreItem))
            return false;

        List<Ingredient> required = new ArrayList<>();
        required.addAll(this.secondaryItems);

        for(ItemStack item : pedestalInputs)
        {
            if(item.isEmpty()) continue;

            boolean inRecipe = false;
            Iterator<Ingredient> req = required.iterator();

            while (req.hasNext())
            {
                if (req.next().apply(item))
                {
                    inRecipe = true;
                    req.remove();
                    break;
                }
            }

            if (!inRecipe) return false;
        }

        return required.isEmpty();
    }

    @Override
    public ResourceLocation getResourceLocation()
    {
        return this.name;
    }
}
