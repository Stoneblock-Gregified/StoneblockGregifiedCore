package org.mangorage.sbgc.core.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import org.mangorage.sbgc.SBGC;
import org.mangorage.sbgc.core.datagen.providers.SBGCWorldGenProvider;

import java.util.Set;

public final class WorldGenDataGen {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, SBGCWorldGenProvider::bootstrapDimensionType)
            .add(Registries.NOISE_SETTINGS, SBGCWorldGenProvider::bootstrapNoiseSettings);
            //.add(Registries.LEVEL_STEM, SBGCWorldGenProvider::bootstrapStem);

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                BUILDER,
                Set.of(SBGC.MOD_ID)
        ));
    }
}
