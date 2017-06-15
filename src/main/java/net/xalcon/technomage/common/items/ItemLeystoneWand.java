package net.xalcon.technomage.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;

public class ItemLeystoneWand extends ItemTM
{
    public ItemLeystoneWand()
    {
        super("leystone_wand");
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        if(!world.isRemote)
        {
            if(MultiblockRegistry.getMultiblocks().stream()
                    .filter(m -> m.isBlockTrigger(world.getBlockState(pos)))
                    .anyMatch(it -> !MultiblockRegistry.postConstructionEvent(it, pos, player).isCanceled() && it.createStructure(world, pos, side, player)))
            {
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }
}
