package net.xalcon.technomage.client.colors;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.common.blocks.properties.TMTreeType;
import net.xalcon.technomage.common.blocks.world.BlockTMLog;

import javax.annotation.Nullable;

public class LeavesColor implements IBlockColor, IItemColor
{
    public final static LeavesColor INSTANCE = new LeavesColor();

    private LeavesColor() { }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
    {
        return tintIndex == 1 ? state.getValue(BlockTMLog.TYPE).getLeafColor() : -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tintIndex)
    {
        return tintIndex == 1 ? TMTreeType.getFromMeta(stack.getMetadata()).getLeafColor() : -1;
    }
}
