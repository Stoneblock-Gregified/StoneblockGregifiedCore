package org.mangorage.sbgc.content.storage.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.mangorage.sbgc.content.storage.StorageCore;
import org.mangorage.sbgc.content.storage.core.INode;

public final class StorageBusNodeBlockEntity extends AbstractStorageNodeBlockEntity implements INode {

    public StorageBusNodeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(StorageCore.STORAGE_BUS_NODE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
