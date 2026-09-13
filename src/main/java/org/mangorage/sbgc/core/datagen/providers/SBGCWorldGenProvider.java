package org.mangorage.sbgc.core.datagen.providers;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.mangorage.sbgc.core.datagen.SBGCDimensions;

import java.util.List;
import java.util.OptionalLong;

public class SBGCWorldGenProvider {
    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
        context.register(SBGCDimensions.STONE_DIM_TYPE_KEY, new DimensionType(
                OptionalLong.of(6000L), // Fixed time (optional)
                true,                  // Has skylight
                false,                 // Has ceiling
                false,                 // Ultrawarm
                true,                  // Natural
                1.0D,                  // Coordinate scale
                true,                  // Bed works
                false,                 // Respawn anchor works
                -64,                   // Min Y
                384,                   // Height
                384,                   // Logical height
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0F,                  // Ambient light
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        // Standard height parameters (-64 to 384, total height 448 blocks)
        NoiseSettings standardSettings = NoiseSettings.create(-64, 448, 1, 2);

        // 1. Layer Definitions (Each 64 blocks tall)
        SurfaceRules.RuleSource layer1_stone     = SurfaceRules.state(Blocks.STONE.defaultBlockState());
        SurfaceRules.RuleSource layer2_endstone  = SurfaceRules.state(Blocks.END_STONE.defaultBlockState());
        SurfaceRules.RuleSource layer3_deepslate = SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState());
        SurfaceRules.RuleSource layer4_netherrack= SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
        SurfaceRules.RuleSource layer5_obsidian  = SurfaceRules.state(Blocks.OBSIDIAN.defaultBlockState());
        SurfaceRules.RuleSource layer6_basalt    = SurfaceRules.state(Blocks.SMOOTH_BASALT.defaultBlockState());

        SurfaceRules.RuleSource bedrock = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());

        // 2. Vertical Y Checks
        SurfaceRules.RuleSource layeredSurfaceRules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, bedrock),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, bedrock),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0), layer6_basalt),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(192), 0), layer5_obsidian),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(128), 0), layer4_netherrack),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(64), 0), layer3_deepslate),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0), layer2_endstone),
                layer1_stone // Fallback for Y < 0
        );

        // 3. Create a Dummy Solid Noise Router (Evaluates all space as 100% solid)
        DensityFunction zeroDensity = DensityFunctions.zero();

        // Setting initialDensity to constant positive value forces all voxels to fill completely
        DensityFunction solidDensity = DensityFunctions.constant(1.0D);

        NoiseRouter solidNoiseRouter = new NoiseRouter(
                zeroDensity,  // barrierNoise
                zeroDensity,  // fluidLevelFloodednessNoise
                zeroDensity,  // fluidLevelSpreadNoise
                zeroDensity,  // lavaNoise
                zeroDensity,  // temperature
                zeroDensity,  // vegetation
                zeroDensity,  // continents
                zeroDensity,  // erosion
                zeroDensity,  // depth
                zeroDensity,  // ridges
                solidDensity, // initialDensityWithoutJaggedness (FORCES FULL SOLIDITY)
                solidDensity, // finalDensity (FORCES FULL SOLIDITY)
                zeroDensity,  // veinToggle
                zeroDensity,  // veinRidged
                zeroDensity   // veinGap
        );

        // 4. Build Noise Settings
        NoiseGeneratorSettings solidLayeredSettings = new NoiseGeneratorSettings(
                standardSettings,
                Blocks.STONE.defaultBlockState(), // Default filler block
                Blocks.STONE.defaultBlockState(), // Default fluid (set to stone)
                solidNoiseRouter,                 // Custom noise router (100% solid mass)
                layeredSurfaceRules,              // Surface rules (Applies 64-block layers)
                List.of(),                        // No spawn targets
                -64,                              // Sea level
                false,                            // Disable mob spawning
                false,                            // Disable aquifers (no liquid caves)
                true,                            // Disable ore veins
                false                             // Legacy random source
        );

        context.register(SBGCDimensions.STONE_NOISE_GEN_KEY, solidLayeredSettings);
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        // Use Plains as the single biome source for climate data
        FixedBiomeSource biomeSource = new FixedBiomeSource(biomes.getOrThrow(Biomes.PLAINS));

        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(
                biomeSource,
                noiseSettings.getOrThrow(SBGCDimensions.STONE_NOISE_GEN_KEY)
        );

        LevelStem stem = new LevelStem(
                dimTypes.getOrThrow(SBGCDimensions.STONE_DIM_TYPE_KEY),
                chunkGenerator
        );

        context.register(SBGCDimensions.STONE_DIM_STEM_KEY, stem);
    }
}
