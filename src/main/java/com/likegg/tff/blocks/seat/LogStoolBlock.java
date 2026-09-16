package com.likegg.tff.blocks.seat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LogStoolBlock extends SittableBlock {
    // Base shapes created using coordinates from Blockbench (X1, Y1, Z1, X2, Y2, Z2)
    private static final VoxelShape SEAT = Block.box(2, 4, 2, 14, 12, 14);
    private static final VoxelShape LEGS = Block.box(4, 0, 4, 12, 4, 12);
    private static final VoxelShape HITBOX = Shapes.or(SEAT, LEGS);

    public LogStoolBlock(Properties properties) {
        super(properties);
    }

    @Override
    public double getSeatHeight(BlockState state) {
        return 0.75;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        // Checks if the top face of the block below can sturdily support a block
        return level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // If the block underneath is broken, break the chair automatically
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return HITBOX;
    }
}