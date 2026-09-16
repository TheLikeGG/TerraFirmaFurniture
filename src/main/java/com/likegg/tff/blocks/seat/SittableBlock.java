package com.likegg.tff.blocks.seat;

import com.likegg.tff.entity.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SittableBlock extends Block {

    public SittableBlock(Properties properties) {
        super(properties);
    }

    /**
     * @return The Y-offset from the block base where the player sits (in blocks).
     */
    public abstract double getSeatHeight(BlockState state);

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            return SeatEntity.sitAt(level, pos, getSeatHeight(state), player);
        }

        return InteractionResult.SUCCESS;
    }
}