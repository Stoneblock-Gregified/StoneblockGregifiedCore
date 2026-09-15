package org.mangorage.sbgc.content.storage;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import org.mangorage.sbgc.content.storage.blocks.CableBlock;
import org.mangorage.sbgc.content.storage.blocks.StorageControllerBlock;
import org.mangorage.sbgc.content.storage.blocks.StorageBusNodeBlock;
import org.mangorage.sbgc.content.storage.blocks.entity.StorageBusNodeBlockEntity;
import org.mangorage.sbgc.content.storage.blocks.entity.StorageControllerBlockEntity;
import org.mangorage.sbgc.core.registry.SBGCRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class StorageCore {
    private static List<CreativeModeTab.DisplayItemsGenerator> displayItemGenerators = new ArrayList<>();


    public static final RegistryObject<Block> STORAGE_CONTROLLER_BLOCK = createBlockItem(
            SBGCRegistry.BLOCKS.register(
                "storage_controller",
                () -> new StorageControllerBlock(BlockBehaviour.Properties.of())
            ),
            item -> {
                return (context, output) -> {
                    output.accept(item.get().asItem());
                };
            }
    );

    public static RegistryObject<StorageBusNodeBlock> STORAGE_BUS_NODE_BLOCK = createBlockItem(
            SBGCRegistry.BLOCKS.register("storage_bus_node",
            () -> new StorageBusNodeBlock(BlockBehaviour.Properties.of())
    ),
            item -> (context, output) -> {
                output.accept(item.get().asItem());
            }
    );

    public static RegistryObject<CableBlock> CABLE_BLOCK = createBlockItem(
            SBGCRegistry.BLOCKS.register("cable",
            () -> new CableBlock(BlockBehaviour.Properties.of())
    ),
            item -> (context, output) -> {
                output.accept(item.get().asItem());
            }
    );

    public static final RegistryObject<BlockEntityType<StorageControllerBlockEntity>> STORAGE_CONTROLLER_BLOCK_ENTITY = SBGCRegistry.BLOCK_ENTITIES.register(
            "storage_controller",
            () -> BlockEntityType.Builder.of(
                    StorageControllerBlockEntity::new,
                    STORAGE_CONTROLLER_BLOCK.get()
            ).build(null)
    );

    public static final RegistryObject<BlockEntityType<StorageBusNodeBlockEntity>> STORAGE_BUS_NODE_BLOCK_ENTITY = SBGCRegistry.BLOCK_ENTITIES.register(
            "storage_bus_node",
            () -> BlockEntityType.Builder.of(
                    StorageBusNodeBlockEntity::new,
                    STORAGE_BUS_NODE_BLOCK.get()
            ).build(null)
    );

    private static <T extends Block> RegistryObject<T> createBlockItem(RegistryObject<T> registryObject, Function<RegistryObject<T>, CreativeModeTab.DisplayItemsGenerator> generatorFunction) {

        SBGCRegistry.ITEMS.register(
                registryObject.getId().getPath(),
                () -> new BlockItem(registryObject.get(), new Item.Properties())
        );

        displayItemGenerators.add(
                generatorFunction.apply(registryObject)
        );

        return registryObject;
    }

    public static void onCreativeTab(CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output output) {
        displayItemGenerators.forEach(generator -> generator.accept(context, output));
    }

    public static void register(IEventBus eventBus) {

    }
}
