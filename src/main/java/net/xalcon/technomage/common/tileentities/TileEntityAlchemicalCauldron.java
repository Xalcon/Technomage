package net.xalcon.technomage.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.technomage.common.crafting.Registries;
import net.xalcon.technomage.common.crafting.alchemy.AlchemyRecipe;

import java.util.Collection;
import java.util.List;

public class TileEntityAlchemicalCauldron extends TileEntityTMBase implements ITickable
{
    public boolean hasWater = false;

    @Override
    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        super.readNbt(nbt, type);
        this.hasWater = nbt.getBoolean("hasWater");
    }

    @Override
    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        nbt.setBoolean("hasWater", this.hasWater);
        return super.writeNbt(nbt, type);
    }

    @Override
    public void update()
    {
        if(this.getWorld().isRemote || !this.hasWater) return;
        List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.getPos()));
        Collection<AlchemyRecipe> recipes = Registries.ALCHEMY.getRecipes();
        for(EntityItem item : items)
        {
            AlchemyRecipe recipe = recipes.stream().filter(a -> a.isMatch(item.getItem())).findFirst().orElse(null);
            if(recipe != null)
            {
                item.getItem().shrink(1);
                if(item.getItem().isEmpty())
                    item.setDead();
                InventoryHelper.spawnItemStack(this.getWorld(), this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ(),
                        recipe.getOutput());
                this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0f, 1.0f);
                this.hasWater = false;
                IBlockState state = this.getWorld().getBlockState(this.getPos());
                this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
                return;
            }
        }
    }
}
