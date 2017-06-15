package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class BlockAlchemicalCauldron extends BlockTMTileProvider
{
    public BlockAlchemicalCauldron()
    {
        super("alchemical_cauldron", Material.IRON);
    }

    @Override
    public Map<String, Class<? extends TileEntity>> getTileEntityClasses()
    {
        return Collections.emptyMap();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return null;
    }
}
