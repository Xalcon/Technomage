package net.xalcon.technomage.common.world;

import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.xalcon.technomage.common.init.TMDimensions;

public class WorldProviderDungeonDim extends WorldProvider
{
    @Override
    public DimensionType getDimensionType()
    {
        return TMDimensions.DUNGEONS;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorDungeonDim(this.world);
    }

    @Override
    public BiomeProvider getBiomeProvider()
    {
        return new BiomeProviderSingle(Biomes.PLAINS);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return new Vec3d(1.0, 0.0, 0.0);
    }

    @Override
    public int getHeight()
    {
        return 256;
    }

    @Override
    public int getActualHeight()
    {
        return 256;
    }
}
