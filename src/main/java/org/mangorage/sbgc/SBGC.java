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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.mangorage.sbgc.core.datagen.SBGCDimensions;
import org.mangorage.sbgc.core.registry.SBGCRegistry;
import org.slf4j.Logger;

@Mod(SBGC.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SBGC {

    public static final String MOD_ID = "sbgc";
    private static final Logger LOGGER = LogUtils.getLogger();


    public SBGC(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        SBGCRegistry.register(modEventBus);
    }

}
