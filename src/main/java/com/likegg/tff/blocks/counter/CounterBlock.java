package com.likegg.tff.blocks.counter;

import com.likegg.tff.registry.ModItems;
import com.mojang.serialization.MapCodec;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nullable;
import java.util.Map;

public class CounterBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<CounterBlock> CODEC = simpleCodec(CounterBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    // Common Top Shape
    private static final VoxelShape TOP = Block.box(0, 14, 0, 16, 16, 16);

    // Straight Shapes
    private static final VoxelShape STRAIGHT_NORTH = Shapes.or(TOP, Block.box(0, 13, 0, 16, 14, 1), Block.box(0, 0, 2, 16, 14, 16));
    private static final VoxelShape STRAIGHT_SOUTH = Shapes.or(TOP, Block.box(0, 13, 15, 16, 14, 16), Block.box(0, 0, 0, 16, 14, 14));
    private static final VoxelShape STRAIGHT_WEST  = Shapes.or(TOP, Block.box(0, 13, 0, 1, 14, 16), Block.box(2, 0, 0, 16, 14, 16));
    private static final VoxelShape STRAIGHT_EAST  = Shapes.or(TOP, Block.box(15, 13, 0, 16, 14, 16), Block.box(0, 0, 0, 14, 14, 16));

    // Outer Corner Shapes (Left = Front + Left side; Right = Front + Right side)
    private static final VoxelShape OUTER_NORTH_LEFT  = Shapes.or(TOP, Block.box(0, 13, 0, 16, 14, 1), Block.box(0, 13, 1, 1, 14, 16), Block.box(2, 0, 2, 16, 14, 16));
    private static final VoxelShape OUTER_NORTH_RIGHT = Shapes.or(TOP, Block.box(0, 13, 0, 16, 14, 1), Block.box(15, 13, 1, 16, 14, 16), Block.box(0, 0, 2, 14, 14, 16));

    private static final VoxelShape OUTER_SOUTH_LEFT  = Shapes.or(TOP, Block.box(0, 13, 15, 16, 14, 16), Block.box(15, 13, 0, 16, 14, 15), Block.box(0, 0, 0, 14, 14, 14));
    private static final VoxelShape OUTER_SOUTH_RIGHT = Shapes.or(TOP, Block.box(0, 13, 15, 16, 14, 16), Block.box(0, 13, 0, 1, 14, 15), Block.box(2, 0, 0, 16, 14, 14));

    private static final VoxelShape OUTER_WEST_LEFT   = Shapes.or(TOP, Block.box(0, 13, 0, 1, 14, 16), Block.box(1, 13, 15, 16, 14, 16), Block.box(2, 0, 0, 16, 14, 14));
    private static final VoxelShape OUTER_WEST_RIGHT  = Shapes.or(TOP, Block.box(0, 13, 0, 1, 14, 16), Block.box(1, 13, 0, 16, 14, 1), Block.box(2, 0, 2, 16, 14, 16));

    private static final VoxelShape OUTER_EAST_LEFT   = Shapes.or(TOP, Block.box(15, 13, 0, 16, 14, 16), Block.box(0, 13, 0, 15, 14, 1), Block.box(0, 0, 2, 14, 14, 16));
    private static final VoxelShape OUTER_EAST_RIGHT  = Shapes.or(TOP, Block.box(15, 13, 0, 16, 14, 16), Block.box(0, 13, 15, 15, 14, 16), Block.box(0, 0, 0, 14, 14, 14));

    // Inner Corner Shapes
    private static final VoxelShape INNER_NORTH_LEFT  = Shapes.or(TOP, Block.box(0, 13, 0, 1, 14, 1), Block.box(0, 0, 2, 16, 14, 16), Block.box(2, 0, 0, 16, 14, 2));
    private static final VoxelShape INNER_NORTH_RIGHT = Shapes.or(TOP, Block.box(15, 13, 0, 16, 14, 1), Block.box(0, 0, 2, 16, 14, 16), Block.box(0, 0, 0, 14, 14, 2));

    private static final VoxelShape INNER_SOUTH_LEFT  = Shapes.or(TOP, Block.box(15, 13, 15, 16, 14, 16), Block.box(0, 0, 0, 16, 14, 14), Block.box(0, 0, 14, 14, 14, 16));
    private static final VoxelShape INNER_SOUTH_RIGHT = Shapes.or(TOP, Block.box(0, 13, 15, 1, 14, 16), Block.box(0, 0, 0, 16, 14, 14), Block.box(2, 0, 14, 16, 14, 16));

    private static final VoxelShape INNER_WEST_LEFT   = Shapes.or(TOP, Block.box(0, 13, 15, 1, 14, 16), Block.box(2, 0, 0, 16, 14, 16), Block.box(0, 0, 0, 2, 14, 14));
    private static final VoxelShape INNER_WEST_RIGHT  = Shapes.or(TOP, Block.box(0, 13, 0, 1, 14, 1), Block.box(2, 0, 0, 16, 14, 16), Block.box(0, 0, 2, 2, 14, 16));

    private static final VoxelShape INNER_EAST_LEFT   = Shapes.or(TOP, Block.box(15, 13, 0, 16, 14, 1), Block.box(0, 0, 0, 14, 14, 16), Block.box(14, 0, 2, 16, 14, 16));
    private static final VoxelShape INNER_EAST_RIGHT  = Shapes.or(TOP, Block.box(15, 13, 15, 16, 14, 16), Block.box(0, 0, 0, 14, 14, 16), Block.box(14, 0, 0, 16, 14, 14));

    public CounterBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SHAPE, StairsShape.STRAIGHT));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = this.defaultBlockState().setValue(FACING, facing);
        return state.setValue(SHAPE, getCounterShape(state, context.getLevel(), pos));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis().isHorizontal()) {
            return state.setValue(SHAPE, getCounterShape(state, level, currentPos));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private static StairsShape getCounterShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        // Check front neighbor (outer corners)
        BlockState frontState = level.getBlockState(pos.relative(facing));
        if (isCounter(frontState)) {
            Direction frontFacing = frontState.getValue(FACING);
            if (frontFacing.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, level, pos, frontFacing.getOpposite())) {
                if (frontFacing == facing.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }
                return StairsShape.INNER_RIGHT;
            }
        }

        // Check back neighbor (inner corners)
        BlockState backState = level.getBlockState(pos.relative(facing.getOpposite()));
        if (isCounter(backState)) {
            Direction backFacing = backState.getValue(FACING);
            if (backFacing.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, level, pos, backFacing)) {
                if (backFacing == facing.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }
                return StairsShape.OUTER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        BlockState neighborState = level.getBlockState(pos.relative(face));
        return !isCounter(neighborState) || neighborState.getValue(FACING) != state.getValue(FACING);
    }

    private static boolean isCounter(BlockState state) {
        return state.getBlock() instanceof CounterBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        StairsShape shape = state.getValue(SHAPE);

        return switch (shape) {
            case OUTER_LEFT -> switch (facing) {
                case SOUTH -> OUTER_SOUTH_LEFT;
                case WEST  -> OUTER_WEST_LEFT;
                case EAST  -> OUTER_EAST_LEFT;
                default    -> OUTER_NORTH_LEFT;
            };
            case OUTER_RIGHT -> switch (facing) {
                case SOUTH -> OUTER_SOUTH_RIGHT;
                case WEST  -> OUTER_WEST_RIGHT;
                case EAST  -> OUTER_EAST_RIGHT;
                default    -> OUTER_NORTH_RIGHT;
            };
            case INNER_LEFT -> switch (facing) {
                case SOUTH -> INNER_SOUTH_LEFT;
                case WEST  -> INNER_WEST_LEFT;
                case EAST  -> INNER_EAST_LEFT;
                default    -> INNER_NORTH_LEFT;
            };
            case INNER_RIGHT -> switch (facing) {
                case SOUTH -> INNER_SOUTH_RIGHT;
                case WEST  -> INNER_WEST_RIGHT;
                case EAST  -> INNER_EAST_RIGHT;
                default    -> INNER_NORTH_RIGHT;
            };
            default -> switch (facing) {
                case SOUTH -> STRAIGHT_SOUTH;
                case WEST  -> STRAIGHT_WEST;
                case EAST  -> STRAIGHT_EAST;
                default    -> STRAIGHT_NORTH;
            };
        };
    }

}
