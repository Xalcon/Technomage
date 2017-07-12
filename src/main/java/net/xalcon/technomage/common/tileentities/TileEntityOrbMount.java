package net.xalcon.technomage.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.xalcon.technomage.common.items.ItemTranslocationOrb;

import javax.annotation.Nullable;

public class TileEntityOrbMount extends TileEntityTMBase
{
    private ItemStack orb = ItemStack.EMPTY;

    public ItemStack getOrbItemStack()
    {
        return this.orb;
    }

    public void setOrbItemStack(ItemStack orb)
    {
        if(orb.getItem() instanceof ItemTranslocationOrb)
        {
            this.orb = orb;
            this.markDirty();
        }
    }

    @Override
    public void readNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        super.readNbt(nbt, type);
        if(nbt.hasKey("orb"))
            this.orb = new ItemStack(nbt.getCompoundTag("orb"));
    }

    @Override
    public NBTTagCompound writeNbt(NBTTagCompound nbt, EnumSyncType type)
    {
        if(!this.orb.isEmpty())
            nbt.setTag("orb", this.orb.writeToNBT(new NBTTagCompound()));
        return super.writeNbt(nbt, type);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName()
    {
        if(!this.orb.isEmpty() && this.orb.hasDisplayName())
            return new TextComponentString(this.orb.getDisplayName());
        return super.getDisplayName();
    }
}
