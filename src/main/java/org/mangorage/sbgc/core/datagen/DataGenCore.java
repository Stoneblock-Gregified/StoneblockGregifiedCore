package org.mangorage.sbgc.core.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.mangorage.sbgc.SBGC;

@Mod.EventBusSubscriber(modid = SBGC.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenCore {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        WorldGenDataGen.onGatherData(event);
    }
}
