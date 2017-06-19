package net.xalcon.technomage.common.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
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

public class ItemCursedClawWand extends ItemTM
{
    public static final String INTERNAL_NAME = "cursed_claw_wand";
    public ItemCursedClawWand()
    {
        super(INTERNAL_NAME);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        Vec3d lookAt = player.getLookVec();
        double d0 = player.posY;
        double d1 = player.posY + 1.0D;
        float f = (float) MathHelper.atan2(lookAt.z, lookAt.x);

        for (int l = 0; l < 10; l++)
        {
            int c = 3 + 2 * l;
            for (int i = 0; i < c; ++i)
            {
                float f1 = f + (float) i * (float) Math.PI * 2.0F / c + ((float) Math.PI * 2F / 5F);
                this.spawnFangs(player.posX + (double) MathHelper.cos(f1) * (1.5D + l), player.posZ + (double) MathHelper.sin(f1) * (1.5D + l), d0, d1, f1, l * 5, world, player);
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        /*Vec3d lookAt = playerIn.getLookVec();
        double d0 = playerIn.posY;
        double d1 = playerIn.posY + 1.0D;
        float f = (float)MathHelper.atan2(lookAt.z, lookAt.x);

        for (int l = 0; l < 16; ++l)
        {
            double d2 = 1.25D * (double)(l + 1);
            int j = 1 * l;
            this.spawnFangs(playerIn.posX + (double)MathHelper.cos(f) * d2, playerIn.posZ + (double)MathHelper.sin(f) * d2, d0, d1, f, j, worldIn, playerIn);
        }*/
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void spawnFangs(double x, double z, double y_min, double y_max, float angle, int timeUntilAttack, World world, EntityLivingBase caster)
    {
        BlockPos blockpos = new BlockPos(x, y_max, z);
        boolean flag = false;
        double d0 = 0.0D;

        while (true)
        {
            if (!world.isBlockNormalCube(blockpos, true) && world.isBlockNormalCube(blockpos.down(), true))
            {
                if (!world.isAirBlock(blockpos))
                {
                    IBlockState iblockstate = world.getBlockState(blockpos);
                    AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(world, blockpos);

                    if (axisalignedbb != null)
                    {
                        d0 = axisalignedbb.maxY;
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.down();

            if (blockpos.getY() < MathHelper.floor(y_min) - 1)
            {
                break;
            }
        }

        if (flag)
        {
            EntityEvokerFangs entityevokerfangs = new EntityEvokerFangs(world, x, (double) blockpos.getY() + d0, z, angle, timeUntilAttack, caster);
            world.spawnEntity(entityevokerfangs);
        }
    }
}
