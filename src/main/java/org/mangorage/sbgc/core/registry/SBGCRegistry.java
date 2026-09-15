package org.mangorage.sbgc.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mangorage.sbgc.SBGC;
import org.mangorage.sbgc.content.storage.StorageCore;

public final class SBGCRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SBGC.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SBGC.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SBGC.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SBGC.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CORE_TAB = CREATIVE_MODE_TABS.register("core_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab.sbgc.core_tab"))
            .icon(Items.STICK::getDefaultInstance)
            .displayItems((context, output) -> {

            })
            .build()
    );


    public static void register(IEventBus eventBus) {
        StorageCore.register(eventBus);

        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
