package net.xalcon.technomage.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

import javax.annotation.Nullable;

public class ItemBlockTMMeta extends ItemBlock
{
    public ItemBlockTMMeta(Block block)
    {
        super(block);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
