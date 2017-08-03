package net.xalcon.technomage.common.tileentities;

import com.sun.javafx.geom.Vec3f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class TileEntityLeylightBore extends TileEntityTMBase implements ITickable
{
    /* D-U-N-S-W-E */
    public final static Vec2f[] ROTATIONS =
    {
        new Vec2f(180, -90), /* DOWN */
        new Vec2f(0, -90), /* UP */
        new Vec2f(90, -180), /* NORTH */
        new Vec2f(90, 0), /* SOUTH */
        new Vec2f(90, -90), /* WEST */
        new Vec2f(90, -270), /* EAST */
    };

    public float directionProgress;
    public EnumFacing direction = EnumFacing.UP;

    public float startPitch;
    public float startYaw;
    public float curPitch;
    public float curYaw;
    public float targetPitch;
    public float targetYaw;

    @Override
    public void update()
    {
        if(this.directionProgress > 0)
            this.directionProgress -= 0.05;
        else this.directionProgress = 0;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }
}
