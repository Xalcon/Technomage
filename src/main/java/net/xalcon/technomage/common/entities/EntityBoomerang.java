package net.xalcon.technomage.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTrackerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.xalcon.technomage.common.init.TMItems;

import java.util.List;

public class EntityBoomerang extends EntityThrowable
{
    public EntityBoomerang(World worldIn)
    {
        super(worldIn);
        this.setSize(0.4F, 0.4F);
    }

    public EntityBoomerang(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.setSize(0.4F, 0.4F);
    }

    public EntityBoomerang(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        this.setSize(0.4F, 0.4F);
    }

    private Vec3d startPos;

    @Override
    public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        super.setHeadingFromThrower(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy);
        this.startPos = entityThrower.getPositionVector().addVector(0, 1,0);
        //this.setEntityBoundingBox(new AxisAlignedBB());
    }

    private boolean isOnWayBack = false;

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(this.world.isRemote) return;

        EntityLivingBase thrower = this.getThrower();
        if(thrower == null || thrower.isDead)
        {
            this.setDead();
            return;
        }

        List<Entity> passengers = this.getPassengers();
        if(passengers.size() == 0)
        {
            List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(0.5D, 0.0D, 0.5D));
            if(items.size() > 0)
            {
                EntityItem item = items.get(0);
                item.setPickupDelay(100);
                item.startRiding(this, true);
                this.isOnWayBack = true;

            }
        }

        double dist = this.startPos.squareDistanceTo(this.getPositionVector());

        if(this.isOnWayBack || dist > (16*16))
        {
            Vec3d towardsDir = thrower.getPositionVector().addVector(0, 1, 0).subtract(this.getPositionVector()).normalize();
            this.motionX = towardsDir.x;
            this.motionY = towardsDir.y;
            this.motionZ = towardsDir.z;
            this.isOnWayBack = true;
        }

        if(this.ticksExisted > 60 * 20)
        {
            this.setDead();
        }
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     *
     * @param result
     */
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(this.world.isRemote) return;
        if (result.entityHit != null)
        {
            if(result.entityHit != this.getThrower())
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1);
        }

        if(result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            this.isOnWayBack = true;
        }
    }

    @Override
    protected boolean canBeRidden(Entity entityIn)
    {
        return entityIn instanceof EntityItem;
    }

    /**
     * Called by a player entity when they collide with an entity
     *
     * @param entityIn
     */
    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        if(this.getEntityWorld().isRemote) return;

        if(this.isOnWayBack && entityIn == this.getThrower())
        {
            List<Entity> passengers = this.getPassengers();
            if(passengers.size() > 0)
            {
                Entity passenger = passengers.get(0);
                if(passenger instanceof EntityItem)
                {
                    ItemStack itemStack = ((EntityItem) passenger).getItem();
                    if(entityIn.addItemStackToInventory(itemStack))
                    {
                        passenger.setDead();
                    }
                }
            }
            ((EntityPlayerMP)this.getThrower()).getCooldownTracker().setCooldown(TMItems.boomerang(), 0);
            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean canBePushed()
    {
        return false;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getMountedYOffset()
    {
        return 0;
    }
}
