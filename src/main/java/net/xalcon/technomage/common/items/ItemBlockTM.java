package net.xalcon.technomage.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

import javax.annotation.Nullable;

public class ItemBlockTM<T extends Block & IItemModelRegisterHandler> extends ItemBlock implements IItemModelRegisterHandler
{
    public ItemBlockTM(T block)
    {
        super(block);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void registerItemModels(Item item)
    {
        ((IItemModelRegisterHandler)this.getBlock()).registerItemModels(item);
    }
}
