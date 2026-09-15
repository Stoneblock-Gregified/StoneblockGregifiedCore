package org.mangorage.sbgc.content.storage.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.mangorage.sbgc.content.base.blocks.SBGCBaseEntityBlock;
import org.mangorage.sbgc.content.storage.blocks.entity.StorageControllerBlockEntity;

public final class StorageControllerBlock extends SBGCBaseEntityBlock {

    public StorageControllerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (!level.isClientSide) {
            var be = level.getBlockEntity(pos);
            if (be instanceof StorageControllerBlockEntity entity) {
                player.sendSystemMessage(
                        Component.literal("Storage Nodes: " + entity.getConnectedNodes())
                );
            }
        }


        return super.use(pState, level, pos, player, pHand, pHit);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StorageControllerBlockEntity(pPos, pState);
    }
}
