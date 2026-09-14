package org.mangorage.sbgc;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.mangorage.sbgc.core.datagen.SBGCDimensions;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SBGC.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SBGC {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "sbgc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public SBGC() {

    }

    @SubscribeEvent
    public static void onSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(
                EntityType.SILVERFISH,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SBGC::checkSilverfishSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

    public static boolean checkSilverfishSpawnRules(
            EntityType<Silverfish> entityType,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random
    ) {
        // 1. Calculate total light level at the position (0 to 15)
        int maxLight = level.getMaxLocalRawBrightness(pos);

        // 2. Enforce Light Level between 0 and 7
        boolean isDarkEnough = maxLight >= 0 && maxLight <= 7;

        // 3. Combine with standard monster rules (solid block underneath, etc.)
        return isDarkEnough && level.getLevel().dimension() == SBGCDimensions.STONE_DIM_KEY;
    }
}
