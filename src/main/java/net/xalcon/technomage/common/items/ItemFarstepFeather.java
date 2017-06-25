package net.xalcon.technomage.common.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemFarstepFeather extends Item
{
    /**
     * Called when the equipped item is right clicked.
     *
     * @param worldIn
     * @param playerIn
     * @param handIn
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        /*Vec3d lookAt = playerIn.getLookVec();
        Vec3d start = playerIn.getPositionEyes(0);
        Vec3d end = start.add(lookAt.scale(32));
        RayTraceResult result = worldIn.rayTraceBlocks(start, end, false, false, false);
        if(result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            IBlockState state = worldIn.getBlockState(result.getBlockPos());
            playerIn.sendStatusMessage(new TextComponentString("HIT: " + state), true);
            playerIn.setPositionAndUpdate(result.getBlockPos().getX() + 0.5, result.getBlockPos().getY() + 1, result.getBlockPos().getZ() + 0.5);
            worldIn.playSound(playerIn, result.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0f, 0.3f);
            playerIn.getCooldownTracker().setCooldown(this, 100);
        }*/

        if(worldIn.isRemote)
        {
            Vec3d look = playerIn.getLookVec();
            playerIn.motionX += look.x;
            playerIn.motionY += look.y;
            playerIn.motionZ += look.z;
            playerIn.fallDistance = 0;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
