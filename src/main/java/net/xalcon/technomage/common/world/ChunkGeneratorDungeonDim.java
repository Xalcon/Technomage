package net.xalcon.technomage.common.world;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ChunkGeneratorDungeonDim implements IChunkGenerator
{
    private final World world;

    public ChunkGeneratorDungeonDim(World world)
    {
        this.world = world;
    }

    /**
     * Generates the chunk at the specified position, from scratch
     *
     * @param chunkX
     * @param chunkZ
     */
    @Override
    public Chunk generateChunk(int chunkX, int chunkZ)
    {
        ChunkPrimer primer = new ChunkPrimer();
        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                primer.setBlockState(x, 0, z, Blocks.BEDROCK.getDefaultState());
                primer.setBlockState(x, 1, z, Blocks.SANDSTONE.getDefaultState());
                primer.setBlockState(x, 2, z, Blocks.GLASS.getDefaultState());
            }
        }
        return new Chunk(this.world, primer, chunkX, chunkZ);
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     *
     * @param x
     * @param z
     */
    @Override
    public void populate(int x, int z)
    {
    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     *
     * @param chunkIn
     * @param x
     * @param z
     */
    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        return null;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     *
     * @param chunkIn
     * @param x
     * @param z
     */
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        return false;
    }
}
