package net.xalcon.technomage.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class BlockAmalgamationAltar extends BlockTMTileProvider
{
    public BlockAmalgamationAltar()
    {
        super("amalgamation_altar", Material.ROCK);
    }

    @Override
    public Map<String, Class<? extends TileEntity>> getTileEntityClasses()
    {
        return Collections.singletonMap(this.getRegistryName().toString(), TileEntityAmalgamationAltar.class);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityAmalgamationAltar();
    }
}
