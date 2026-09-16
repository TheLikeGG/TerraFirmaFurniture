package com.likegg.tff.blocks.miscellaneous;

import com.likegg.tff.registry.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCCandleCakeBlock;
import net.dries007.tfc.common.items.CandleBlockItem;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.likegg.tff.blocks.entity.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import javax.annotation.Nullable;
import java.util.List;

public class CandleHolderBlock extends AbstractCandleBlock implements EntityBlock, SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CANDLE = BooleanProperty.create("candle");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // Base shapes (no candle)
    private static final VoxelShape HITBOX_N = Block.box(6, 6, 10, 10, 10, 16);
    private static final VoxelShape HITBOX_S = Block.box(6, 6, 0, 10, 10, 6);
    private static final VoxelShape HITBOX_E = Block.box(0, 6, 6, 6, 10, 10);
    private static final VoxelShape HITBOX_W = Block.box(10, 6, 6, 16, 10, 10);
    // Candle shapes (Extended height when candle is placed)
    private static final VoxelShape HITBOX_N_CANDLE = Block.box(7, 10, 11, 9, 14, 13);
    private static final VoxelShape HITBOX_S_CANDLE = Block.box(7, 10, 3, 9, 14, 5);
    private static final VoxelShape HITBOX_W_CANDLE = Block.box(11, 10, 7, 13, 14, 9);
    private static final VoxelShape HITBOX_E_CANDLE = Block.box(3, 10, 7, 5, 14, 9);

    // Tag to handle lighting logic
    public static final TagKey<Item> IGN_ITERS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("c", "tools/igniters")
    );

    public static final MapCodec<CandleHolderBlock> CODEC = simpleCodec(CandleHolderBlock::new);
    @Override
    public MapCodec<CandleHolderBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }

        return blockEntityType == ModBlockEntities.CANDLE_HOLDER.get()
                ? (lvl, pos, st, be) -> CandleHolderBlockEntity.serverTick(lvl, pos, st, (CandleHolderBlockEntity) be)
                : null;
    }

    public CandleHolderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(CANDLE, false)
                .setValue(LIT, false)
                .setValue(WATERLOGGED, false)
        );
    }

    // Pass this method to Block.Properties.of().lightLevel(CandleHolderBlock::getLightEmission)
    public static int getLightEmission(BlockState state) {
        return (state.getValue(CANDLE) && state.getValue(LIT)) ? 12 : 0;
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        if (!state.getValue(CANDLE)) {
            return List.of();
        }

        // Dynamic flame particle offsets based on wall placement direction
        return switch (state.getValue(FACING)) {
            case SOUTH -> List.of(new Vec3(0.5D, 1.0D, 0.25D));
            case EAST  -> List.of(new Vec3(0.25D, 1.0D, 0.5D));
            case WEST  -> List.of(new Vec3(0.75D, 1.0D, 0.5D));
            default    -> List.of(new Vec3(0.5D, 1.0D, 0.75)); // NORTH
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CANDLE, LIT, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case NORTH -> {
                return level.getBlockState(pos.south()).isFaceSturdy(level, pos, Direction.SOUTH);
            }
            case SOUTH -> {
                return level.getBlockState(pos.north()).isFaceSturdy(level, pos, Direction.NORTH);
            }
            case EAST -> {
                return level.getBlockState(pos.west()).isFaceSturdy(level, pos, Direction.WEST);
            }
            case WEST -> {
                return level.getBlockState(pos.east()).isFaceSturdy(level, pos, Direction.EAST);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean hasCandle = state.getValue(CANDLE);

        return switch (facing) {
            case SOUTH -> hasCandle ? Shapes.or(HITBOX_S_CANDLE, HITBOX_S) : HITBOX_S;
            case EAST  -> hasCandle ? Shapes.or(HITBOX_E_CANDLE, HITBOX_E) : HITBOX_E;
            case WEST  -> hasCandle ? Shapes.or(HITBOX_W_CANDLE, HITBOX_W) : HITBOX_W;
            default    -> hasCandle ? Shapes.or(HITBOX_N_CANDLE, HITBOX_N) : HITBOX_N;
        };
    }

    // --------------------
    // BLOCK ENTITIES THING
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CandleHolderBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof CandleHolderBlockEntity holder)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        boolean hasCandle = holder.hasCandle();
        boolean isLit = state.getValue(LIT);

        // 1. Insert ANY candle
        if (!hasCandle && (stack.is(TFCTags.Items.COLORED_CANDLES)) || stack.is(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("tfc", "candle")))) {
            if (!level.isClientSide) {
                ItemStack inserted = stack.copyWithCount(1);
                holder.setHeldCandle(inserted);

                level.setBlock(pos, state.setValue(CANDLE, true).setValue(LIT, false), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) stack.shrink(1);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        // 2. Extinguish lit candle
        if (hasCandle && isLit && stack.isEmpty()) {
            extinguish(player, state, level, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        // 3. Remove candle (Shift + Right Click with empty hand)
        if (hasCandle && !isLit && stack.isEmpty() && player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                ItemStack candleToDrop = holder.getHeldCandle().copy();
                holder.setHeldCandle(ItemStack.EMPTY);

                level.setBlock(pos, state.setValue(CANDLE, false).setValue(LIT, false), Block.UPDATE_ALL);

                if (!candleToDrop.isEmpty()) {
                    popResource(level, pos, candleToDrop);
                }
                level.playSound(null, pos, SoundEvents.CANDLE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        // Check if the block itself is actually being removed/replaced, not just a state change (like LIT)
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof CandleHolderBlockEntity holder) {
                ItemStack candleStack = holder.getHeldCandle();
                if (!candleStack.isEmpty()) {
                    popResource(level, pos, candleStack.copy());
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected boolean canBeLit(BlockState state) {
        // Can only light if waterlogged state allows it and a candle is present
        return !state.getValue(WATERLOGGED) && super.canBeLit(state);
    }

}