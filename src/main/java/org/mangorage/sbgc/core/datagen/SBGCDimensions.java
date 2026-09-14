package org.mangorage.sbgc.core.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.mangorage.sbgc.SBGC;

public final class SBGCDimensions {
    public static final ResourceKey<Level> STONE_DIM_KEY = ResourceKey.create(
            Registries.DIMENSION, new ResourceLocation(SBGC.MOD_ID, "stone_dimension"));

    public static final ResourceKey<LevelStem> STONE_DIM_STEM_KEY = ResourceKey.create(
            Registries.LEVEL_STEM, new ResourceLocation(SBGC.MOD_ID, "stone_dimension"));

    public static final ResourceKey<DimensionType> STONE_DIM_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE, new ResourceLocation(SBGC.MOD_ID, "stone_dimension_type"));

    public static final ResourceKey<NoiseGeneratorSettings> STONE_NOISE_GEN_KEY = ResourceKey.create(
            Registries.NOISE_SETTINGS, new ResourceLocation(SBGC.MOD_ID, "stone_noise_settings"));

    public static final ResourceKey<Biome> CLEAN_STONE_BIOME_KEY = ResourceKey.create(
            Registries.BIOME,
            new ResourceLocation(SBGC.MOD_ID, "stone_biome"));
}