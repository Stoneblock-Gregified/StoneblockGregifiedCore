package org.mangorage.sbgc.content.storage.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.mangorage.sbgc.content.storage.core.ICable;
import org.mangorage.sbgc.content.storage.core.INode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public final class CableBlock extends Block implements ICable {
    public CableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public List<INode> getConnectedNodes(Level level, BlockPos startPos) {
        if (level.isClientSide()) return List.of();

        List<INode> nodes = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();

        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);

                if (!visited.add(neighborPos)) {
                    continue;
                }

                BlockEntity blockEntity = level.getBlockEntity(neighborPos);
                if (blockEntity instanceof INode node) {
                    nodes.add(node);
                } else if (level.getBlockState(neighborPos).getBlock() instanceof ICable) {
                    queue.add(neighborPos);
                }
            }
        }

        return List.copyOf(nodes);
    }
}
