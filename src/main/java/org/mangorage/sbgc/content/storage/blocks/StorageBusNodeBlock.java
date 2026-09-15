package org.mangorage.sbgc.content.storage.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.mangorage.sbgc.content.base.blocks.SBGCBaseEntityBlock;
import org.mangorage.sbgc.content.storage.blocks.entity.StorageBusNodeBlockEntity;
import org.mangorage.sbgc.content.storage.core.INode;

public final class StorageBusNodeBlock extends SBGCBaseEntityBlock {

    public StorageBusNodeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StorageBusNodeBlockEntity(pPos, pState);
    }
}
