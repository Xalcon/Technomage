package net.xalcon.technomage.common.multiblocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.api.multiblock.IMultiblock;
import net.xalcon.technomage.common.blocks.multiblocks.BlockBrickFurnace;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.tileentities.multiblock.TileEntityBrickFurnace;

public class MultiblockBrickFurnace implements IMultiblock
{
    @Override
    public boolean isBlockTrigger(IBlockState state)
    {
        return state.getBlock() == Blocks.BRICK_BLOCK;
    }

    @Override
    public boolean createStructure(World world, BlockPos pos, EnumFacing facing, EntityPlayer player)
    {
        if(!this.isStructureValid(world, pos)) return false;
        //this.setAndUpdate(world, pos, TMBlocks.brickFurnace.getPlacementState(facing, true));
        //this.setAndUpdate(world, pos.up(), TMBlocks.brickFurnace.getPlacementState(facing, false));
        world.playSound(null, pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return true;
    }

    private void setAndUpdate(World world, BlockPos pos, IBlockState state)
    {
        world.setBlockState(pos, state);
        TileEntity te = world.getTileEntity(pos);

        if(te instanceof TileEntityBrickFurnace)
        {
            TileEntityBrickFurnace tile = (TileEntityBrickFurnace) te;
            tile.setLower(state.getValue(BlockBrickFurnace.IS_LOWER));
            tile.facing = state.getValue(BlockBrickFurnace.FACING);
            tile.isMaster = tile.isLower();
            tile.formed = true;
        }
    }

    private boolean isStructureValid(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.BRICK_BLOCK && world.getBlockState(pos.up()).getBlock() == Blocks.HARDENED_CLAY;
    }

    @Override
    public ResourceLocation getName()
    {
        return new ResourceLocation(Technomage.MOD_ID, "brick_furnace");
    }
}
