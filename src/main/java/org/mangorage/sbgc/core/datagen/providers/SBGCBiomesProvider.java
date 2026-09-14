package org.mangorage.sbgc.core.datagen.providers;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.mangorage.sbgc.core.datagen.SBGCDimensions;

public final class SBGCBiomesProvider {
    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        // 1. Generation settings without adding ANY features or carvers
        BiomeGenerationSettings generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers)
                .build(); // Empty settings = No caves, no carvers, no ores, no vegetation

        // 2. Empty mob spawns
        MobSpawnSettings mobSettings = new MobSpawnSettings.Builder()
                .addSpawn(
                        MobCategory.AMBIENT,
                        new MobSpawnSettings.SpawnerData(
                                EntityType.SILVERFISH, 90, 50, 200
                        )
                )
                .addMobCharge(
                        EntityType.SILVERFISH,
                        0.1f,
                        1000f
                )
                .creatureGenerationProbability(109f)
                .build();

        // 3. Build the Biome
        Biome cleanBiome = new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5F)
                .downfall(0.0F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                        .waterColor(0x3F76E4)
                        .waterFogColor(0x050533)
                        .fogColor(0xC0D8FF)
                        .skyColor(0x78A7FF)
                        .build()
                )
                .mobSpawnSettings(mobSettings)
                .generationSettings(generationSettings)
                .build();

        // 4. Register it
        context.register(SBGCDimensions.CLEAN_STONE_BIOME_KEY, cleanBiome);
    }
}
