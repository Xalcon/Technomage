package net.xalcon.technomage.common.items;

import com.sun.javafx.geom.Vec3f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xalcon.technomage.api.multiblock.MultiblockRegistry;

public class ItemLeystoneWand extends ItemTM
{
    public static final String INTERNAL_NAME = "leystone_wand";
    public ItemLeystoneWand()
    {
        super(INTERNAL_NAME);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
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
