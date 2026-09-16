package com.likegg.tff.blocks.shelf;


import com.likegg.tff.blocks.entity.BasicShelfBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;

public class BasicShelfBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<BasicShelfBlock> CODEC = simpleCodec(BasicShelfBlock::new);
    @Override
    public MapCodec<BasicShelfBlock> codec() {
        return CODEC;
    }

    public static final EnumProperty<DoubleBlockHalf> SHELF_HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

// --- VOXEL SHAPES ---

    // NORTH (Back at Z=16, front at Z=6)
    public static final VoxelShape SHAPE_NORTH_LOWER = Shapes.or(
            Block.box(0, 0, 6, 16, 2, 16),   // Base
            Block.box(15, 2, 7, 16, 16, 16), // Side Right
            Block.box(0, 2, 7, 1, 16, 16),   // Side Left
            Block.box(1, 7, 8, 15, 8, 16),   // Shelf 1
            Block.box(1, 15, 8, 15, 16, 16)  // Shelf 2
    );
    public static final VoxelShape SHAPE_NORTH_UPPER = Shapes.or(
            Block.box(0, 14, 6, 16, 16, 16), // Top
            Block.box(0, 0, 6, 16, 1, 16),   // Bottom
            Block.box(15, 0, 7, 16, 14, 16), // Side Right
            Block.box(0, 0, 7, 1, 14, 16),   // Side Left
            Block.box(1, 7, 8, 15, 8, 16)    // Shelf 3
    );

    // SOUTH (Back at Z=0, front at Z=10)
    public static final VoxelShape SHAPE_SOUTH_LOWER = Shapes.or(
            Block.box(0, 0, 0, 16, 2, 10),   // Base
            Block.box(15, 2, 0, 16, 16, 9),  // Side Right
            Block.box(0, 2, 0, 1, 16, 9),    // Side Left
            Block.box(1, 7, 0, 15, 8, 8),    // Shelf 1
            Block.box(1, 15, 0, 15, 16, 8)   // Shelf 2
    );
    public static final VoxelShape SHAPE_SOUTH_UPPER = Shapes.or(
            Block.box(0, 14, 0, 16, 16, 10), // Top
            Block.box(1, 0, 0, 15, 1, 8),    // Bottom
            Block.box(15, 0, 0, 16, 14, 9),  // Side Right
            Block.box(0, 0, 0, 1, 14, 9),    // Side Left
            Block.box(1, 7, 0, 15, 8, 8)     // Shelf 3
    );

    // WEST (Back at X=16, front at X=6)
    public static final VoxelShape SHAPE_WEST_LOWER = Shapes.or(
            Block.box(6, 0, 0, 16, 2, 16),   // Base
            Block.box(7, 2, 0, 16, 16, 1),   // Side 1
            Block.box(7, 2, 15, 16, 16, 16), // Side 2
            Block.box(8, 7, 1, 16, 8, 15),   // Shelf 1
            Block.box(8, 15, 1, 16, 16, 15)  // Shelf 2
    );
    public static final VoxelShape SHAPE_WEST_UPPER = Shapes.or(
            Block.box(6, 14, 0, 16, 16, 16), // Top
            Block.box(8, 0, 1, 16, 1, 15),   // Bottom
            Block.box(7, 0, 0, 16, 14, 1),   // Side 1
            Block.box(7, 0, 15, 16, 14, 16), // Side 2
            Block.box(8, 7, 1, 16, 8, 15)    // Shelf 3
    );

    // EAST (Back at X=0, front at X=10)
    public static final VoxelShape SHAPE_EAST_LOWER = Shapes.or(
            Block.box(0, 0, 0, 10, 2, 16),   // Base
            Block.box(0, 2, 0, 9, 16, 1),    // Side 1
            Block.box(0, 2, 15, 9, 16, 16),  // Side 2
            Block.box(0, 7, 1, 8, 8, 15),    // Shelf 1
            Block.box(0, 15, 1, 8, 16, 15)   // Shelf 2
    );
    public static final VoxelShape SHAPE_EAST_UPPER = Shapes.or(
            Block.box(0, 14, 0, 10, 16, 16), // Top
            Block.box(0, 0, 1, 8, 1, 15),    // Bottom
            Block.box(0, 0, 0, 9, 14, 1),    // Side 1
            Block.box(0, 0, 15, 9, 14, 16),  // Side 2
            Block.box(0, 7, 1, 8, 8, 15)     // Shelf 3
    );


    public BasicShelfBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHELF_HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(SHELF_HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    // Place the top half automatically when placed
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(SHELF_HALF, DoubleBlockHalf.UPPER), 3);
    }

    // Ensure the block can't survive floating
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(SHELF_HALF) == DoubleBlockHalf.UPPER) {
            BlockState lower = level.getBlockState(pos.below());
            return lower.is(this) && lower.getValue(SHELF_HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(state, level, pos);
    }

    // Break the other half if one half is broken
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(SHELF_HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(this) && neighborState.getValue(SHELF_HALF) != half
                    ? state
                    : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // Register the FACING property to the block state definition
        builder.add(FACING);
        builder.add(SHELF_HALF);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        DoubleBlockHalf half = state.getValue(SHELF_HALF);
        Direction facing = state.getValue(FACING);

        if (half == DoubleBlockHalf.LOWER) {
            return switch (facing) {
                case NORTH -> SHAPE_NORTH_LOWER;
                case SOUTH -> SHAPE_SOUTH_LOWER;
                case EAST -> SHAPE_EAST_LOWER;
                default -> SHAPE_WEST_LOWER;
            };
        } else {
            return switch (facing) {
                case NORTH -> SHAPE_NORTH_UPPER;
                case SOUTH -> SHAPE_SOUTH_UPPER;
                case EAST -> SHAPE_EAST_UPPER;
                default -> SHAPE_WEST_UPPER;
            };
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            DoubleBlockHalf half = state.getValue(SHELF_HALF);
            if (half == DoubleBlockHalf.UPPER) {
                BlockPos posBelow = pos.below();
                BlockState stateBelow = level.getBlockState(posBelow);
                if (stateBelow.is(state.getBlock()) && stateBelow.getValue(SHELF_HALF) == DoubleBlockHalf.LOWER) {
                    BlockState replacement = stateBelow.hasProperty(BlockStateProperties.WATERLOGGED) && stateBelow.getValue(BlockStateProperties.WATERLOGGED)
                            ? Blocks.WATER.defaultBlockState()
                            : Blocks.AIR.defaultBlockState();

                    // Flag 35 = Block.UPDATE_ALL (3) | Block.UPDATE_SUPPRESS_DROPS (32)
                    level.setBlock(posBelow, replacement, 35);
                    level.levelEvent(player, 2001, posBelow, Block.getId(stateBelow));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }


    // --- Block Entity Logic ---
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BasicShelfBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof BasicShelfBlockEntity shelfBE) {
            return shelfBE.interactWithShelf(player, hand, hit);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof BasicShelfBlockEntity shelfBE) {
            ItemInteractionResult result = shelfBE.interactWithShelf(player, InteractionHand.MAIN_HAND, hit);
            if (result.consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BasicShelfBlockEntity shelfBE) {
                shelfBE.dropContents();
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }


}
