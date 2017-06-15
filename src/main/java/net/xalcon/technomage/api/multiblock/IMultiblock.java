package net.xalcon.technomage.api.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock
{
    boolean isBlockTrigger(IBlockState state);

    boolean createStructure(World world, BlockPos pos, EnumFacing facing, EntityPlayer player);

    ResourceLocation getName();

    default IBlockState getBlockstateFromItemStack(int index, ItemStack stack)
    {
        if(!stack.isEmpty() && stack.getItem() instanceof ItemBlock)
            return ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
        return null;
    }
}
