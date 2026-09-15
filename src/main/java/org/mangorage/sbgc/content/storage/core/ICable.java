package org.mangorage.sbgc.content.storage.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.List;

public interface ICable {
    List<INode> getConnectedNodes(Level level, BlockPos pos);
}
