package com.likegg.tff.blocks.miscellaneous;

import com.likegg.tff.blocks.entity.NightStandSingleDrawerBlockEntity;
import com.likegg.tff.blocks.entity.WallCabinetSingleDoorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;


public class NightStandSingleDrawerBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final MapCodec<NightStandSingleDrawerBlock> CODEC = simpleCodec(NightStandSingleDrawerBlock::new);

    @Override
    public MapCodec<NightStandSingleDrawerBlock> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE_TOP  = Block.box(0, 14, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_BODY = Block.box(1,8,1,15,14,15);
    private static final VoxelShape SHAPE_LEG1 = Block.box(1,0,1,3,8,3);
    private static final VoxelShape SHAPE_LEG2 = Block.box(1,0,13,3,8,15);
    private static final VoxelShape SHAPE_LEG3 = Block.box(13,0,13,15,8,15);
    private static final VoxelShape SHAPE_LEG4 = Block.box(13,0,1,15,8,3);
    private static final VoxelShape GENERIC_SHAPE = Shapes.or(SHAPE_TOP, SHAPE_BODY, SHAPE_LEG1, SHAPE_LEG2, SHAPE_LEG3, SHAPE_LEG4);

    // --- NORTH ---
    private static final VoxelShape SHAPE_DRAWER_N = Block.box(2,9,0,14,13,1);
    private static final VoxelShape SHAPE_HANDLE_N = Block.box(5,11,-1,11,12,0);
    private static final VoxelShape SHAPE_N = Shapes.or(GENERIC_SHAPE, SHAPE_DRAWER_N, SHAPE_HANDLE_N);

    // --- SOUTH ---
    private static final VoxelShape SHAPE_DRAWER_S = Block.box(2,9,15,14,13,16);
    private static final VoxelShape SHAPE_HANDLE_S = Block.box(5,11,16,11,12,17);
    private static final VoxelShape SHAPE_S = Shapes.or(GENERIC_SHAPE, SHAPE_DRAWER_S, SHAPE_HANDLE_S);

    // --- EAST ---
    private static final VoxelShape SHAPE_DRAWER_E = Block.box(15,9,2,16,13,14);
    private static final VoxelShape SHAPE_HANDLE_E = Block.box(16,11,5,17,12,11);
    private static final VoxelShape SHAPE_E = Shapes.or(GENERIC_SHAPE, SHAPE_DRAWER_E, SHAPE_HANDLE_E);

    // --- WEST ---
    private static final VoxelShape SHAPE_DRAWER_W = Block.box(0,9,2,1,13,14);
    private static final VoxelShape SHAPE_HANDLE_W = Block.box(-1,11,5,0,12,11);
    private static final VoxelShape SHAPE_W = Shapes.or(GENERIC_SHAPE, SHAPE_DRAWER_W, SHAPE_HANDLE_W);

    public NightStandSingleDrawerBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NightStandSingleDrawerBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof NightStandSingleDrawerBlockEntity block) {
                player.openMenu(block, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeInt(block.getInventory().getSlots());
                    buf.writeInt(block.getColumns());
                });
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH -> {
                return SHAPE_N;
            }
            case SOUTH -> {
                return SHAPE_S;
            }
            case EAST -> {
                return SHAPE_E;
            }
            default -> {
                return SHAPE_W;
            }
        }
    }

        @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof NightStandSingleDrawerBlockEntity cabinet) {
                cabinet.dropContents();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
