package com.likegg.tff.blocks.table;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodenTableBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    // Custom SINGLE property for tables that do not connect
    public static final BooleanProperty SINGLE = BooleanProperty.create("single");

    // Shape
    private static final VoxelShape TABLE_TOP = Block.box(0, 12, 0, 16, 16, 16);
    private static final VoxelShape LEG_NW    = Block.box(1, 0, 1, 4, 12, 4);
    private static final VoxelShape LEG_NE    = Block.box(12, 0, 1, 15, 12, 4);
    private static final VoxelShape LEG_SE    = Block.box(12, 0, 12, 15, 12, 15);
    private static final VoxelShape LEG_SW    = Block.box(1, 0, 12, 4, 12, 15);

    public WoodenTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, SINGLE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean isSneaking = context.getPlayer() != null && context.getPlayer().isSecondaryUseActive();
        BlockState initialState = this.defaultBlockState().setValue(SINGLE, isSneaking);

        return updateConnections(context.getLevel(), context.getClickedPos(), initialState);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis().isHorizontal()) {
            return updateConnections(level, currentPos, state);
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private BlockState updateConnections(LevelAccessor level, BlockPos pos, BlockState currentState) {
        // If this table is unconnectable, force all connections to false
        if (currentState.getValue(SINGLE)) {
            return currentState
                    .setValue(NORTH, false)
                    .setValue(EAST, false)
                    .setValue(SOUTH, false)
                    .setValue(WEST, false);
        }

        boolean connectNorth = canConnectTo(level, pos.north());
        boolean connectSouth = canConnectTo(level, pos.south());
        boolean connectEast  = canConnectTo(level, pos.east());
        boolean connectWest  = canConnectTo(level, pos.west());

        return currentState
                .setValue(NORTH, connectNorth)
                .setValue(SOUTH, connectSouth)
                .setValue(EAST, connectEast)
                .setValue(WEST, connectWest);
    }

    private boolean canConnectTo(LevelAccessor level, BlockPos pos) {
        BlockState neighbor = level.getBlockState(pos);
        if (neighbor.getBlock() instanceof WoodenTableBlock) {
            // Do not connect if the neighboring table is set to unconnectable
            return !neighbor.hasProperty(SINGLE) || !neighbor.getValue(SINGLE);
        }
        return false;
    }

    private boolean isTable(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof WoodenTableBlock;
    }

    private int getChainLength(LevelAccessor level, BlockPos pos, Direction dir) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof WoodenTableBlock)) {
            return 0;
        }
        BooleanProperty prop = getPropertyForDirection(dir);
        if (state.hasProperty(prop) && state.getValue(prop)) {
            return 1 + getChainLength(level, pos.relative(dir), dir);
        }
        return 1;
    }

    private BooleanProperty getPropertyForDirection(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> throw new IllegalArgumentException("Invalid direction: " + dir);
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = TABLE_TOP;

        boolean n = state.getValue(NORTH);
        boolean e = state.getValue(EAST);
        boolean s = state.getValue(SOUTH);
        boolean w = state.getValue(WEST);

        // Render legs only on unconnected outer corners
        if (!n && !w) shape = Shapes.or(shape, LEG_NW);
        if (!n && !e) shape = Shapes.or(shape, LEG_NE);
        if (!s && !e) shape = Shapes.or(shape, LEG_SE);
        if (!s && !w) shape = Shapes.or(shape, LEG_SW);

        return shape;
    }
}