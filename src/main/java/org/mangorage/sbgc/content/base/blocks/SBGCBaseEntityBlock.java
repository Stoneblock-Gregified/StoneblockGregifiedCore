package org.mangorage.sbgc.content.base.blocks;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.mangorage.sbgc.core.util.ITick;

public abstract class SBGCBaseEntityBlock extends Block implements EntityBlock {

    public SBGCBaseEntityBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (level, blockPos, state, entity) -> {
            if (entity instanceof ITick tick) {
                tick.tick();
            }
        };
    }


}
