package org.mangorage.sbgc.content.storage.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.mangorage.sbgc.content.storage.StorageCore;
import org.mangorage.sbgc.content.storage.core.ICable;
import org.mangorage.sbgc.content.storage.core.INode;
import org.mangorage.sbgc.core.util.ITick;

import java.util.ArrayList;
import java.util.List;

public class StorageControllerBlockEntity extends BlockEntity implements ITick {

    private final List<INode> nodes = new ArrayList<>();
    private int ticks = 0;

    public StorageControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(StorageCore.STORAGE_CONTROLLER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public int getConnectedNodes() {
        return nodes.size();
    }

    @Override
    public void tick() {
        // Requires level and worldPosition available within the BlockEntity
        if (this.level == null || this.level.isClientSide) {
            return;
        }
        ticks++;

        if (nodes.isEmpty() || ticks % 100 == 0) {

            nodes.clear();

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = getBlockPos().relative(direction);
                BlockState blockState = getLevel().getBlockState(neighborPos);

                if (blockState.getBlock() instanceof ICable cable) {
                    List<INode> connectedNodes = cable.getConnectedNodes(level, neighborPos);
                    nodes.addAll(connectedNodes);
                }
            }
        }

    }
}
