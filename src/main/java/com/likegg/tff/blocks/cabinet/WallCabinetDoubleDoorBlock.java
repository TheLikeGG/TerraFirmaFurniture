package com.likegg.tff.blocks.cabinet;

import com.likegg.tff.blocks.entity.WallCabinetDoubleDoorBlockEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.slf4j.Logger;


public class WallCabinetDoubleDoorBlock extends WallCabinetBlock implements EntityBlock {
    public WallCabinetDoubleDoorBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WallCabinetDoubleDoorBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        // If not clicking the face with the doors then do not open inventory
        if (hitResult.getDirection() != state.getValue(FACING)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof WallCabinetDoubleDoorBlockEntity cabinet) {
                Vec3 hitPos = hitResult.getLocation().subtract(pos.getCenter());
                Logger LOGGER = LogUtils.getLogger();
                //LOGGER.debug("Hit position: " + hitPos.toString());
                boolean openLeftSide = false;
                switch (state.getValue(FACING)) {
                    case NORTH -> {
                        openLeftSide = hitPos.x > 0;
                    }
                    case SOUTH -> {
                        openLeftSide = hitPos.x < 0;
                    }
                    case EAST -> {
                        openLeftSide = hitPos.z > 0;
                    }
                    default -> { // Case west
                        openLeftSide = hitPos.z < 0;
                    }
                }
                //LOGGER.debug("This corresponds to " + (openLeftSide ? "left" : "right") + " side");

                ItemStackHandler inventory = cabinet.getInventory(openLeftSide);

                cabinet.setSide(openLeftSide);
                player.openMenu(cabinet, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeInt(inventory.getSlots());
                    buf.writeInt(cabinet.getColumns());
                });
            }

        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WallCabinetDoubleDoorBlockEntity cabinet) {
                cabinet.dropContents();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
