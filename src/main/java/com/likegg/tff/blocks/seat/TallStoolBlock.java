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

public class TallStoolBlock extends SittableBlock {

    // Hitbox for legs and seat top
    private static final VoxelShape SEAT = Block.box(3, 10, 3, 13, 12, 13);
    private static final VoxelShape LEGS = Block.box(4, 0, 4, 12, 10, 12);
    private static final VoxelShape SHAPE = Shapes.or(SEAT, LEGS);

    public TallStoolBlock(Properties properties) {
        super(properties);
    }

    @Override
    public double getSeatHeight(BlockState state) {
        // Match the top Y-coordinate of your stool model's seat (e.g., 10 pixels = ~0.52)
        return 0.75;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}