package com.likegg.tff.blocks.cabinet;

import com.likegg.tff.blocks.entity.WallCabinetDoubleDoorBlockEntity;
import com.likegg.tff.blocks.entity.WallCabinetWithShelfBlockEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.slf4j.Logger;


public class WallCabinetWithShelfBlock extends WallCabinetBlock implements EntityBlock {
    private static final VoxelShape SHAPE_TOP = Block.box(0, 8, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_BOTTOM = Block.box(0, 0, 0, 16, 2, 16);
    private static final VoxelShape SHAPE_SHELF = Block.box(2, 2, 2, 14, 8, 14);
    private static final VoxelShape WALL_N = Block.box(0, 2, 0, 16, 8, 1);
    private static final VoxelShape WALL_S = Block.box(0, 2, 15, 16, 8, 16);
    private static final VoxelShape WALL_E = Block.box(15, 2, 0, 16, 8, 16);
    private static final VoxelShape WALL_W = Block.box(0, 2, 0, 1, 8, 16);
    private static final VoxelShape CONSTANT_SHAPE = Shapes.or(SHAPE_TOP, SHAPE_BOTTOM, SHAPE_SHELF);


    public WallCabinetWithShelfBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WallCabinetWithShelfBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        // If not clicking the face with the doors then do not open inventory
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof WallCabinetWithShelfBlockEntity cabinet) {
                Vec3 hitPos = hitResult.getLocation().subtract(pos.getCenter());
                //Logger LOGGER = LogUtils.getLogger();
                //LOGGER.debug("Hit position: " + hitPos.toString());
                boolean openMenu = hitPos.y >= 0;

                //LOGGER.debug("This corresponds to " + (openMenu ? "top" : "bottom") + " half");

                if (openMenu){
                    ItemStackHandler inventory = cabinet.getInventory();

                    player.openMenu(cabinet, buf -> {
                        buf.writeBlockPos(pos);
                        buf.writeInt(inventory.getSlots());
                        buf.writeInt(cabinet.getColumns());
                    });
                }
                else {
                    ItemStack mainHandStack = player.getMainHandItem();
                    cabinet.interactWithShelf(player, mainHandStack);
                }
            }

        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        // Only process the main hand to avoid duplicate triggers
        if (hand == InteractionHand.MAIN_HAND) {
            InteractionResult result = useWithoutItem(state, level, pos, player, hitResult);

            // If the interaction succeeded, notify the engine so it stops processing
            if (result.consumesAction()) {
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        // Fall back to default behavior (e.g., allow offhand or standard item placement)
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WallCabinetWithShelfBlockEntity cabinet) {
                cabinet.dropContents();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST, WEST -> {
                return Shapes.or(CONSTANT_SHAPE, WALL_N, WALL_S);
            }
            default -> { // case NORTH, SOUTH
                return Shapes.or(CONSTANT_SHAPE, WALL_E, WALL_W);
            }
        }
    }
}
