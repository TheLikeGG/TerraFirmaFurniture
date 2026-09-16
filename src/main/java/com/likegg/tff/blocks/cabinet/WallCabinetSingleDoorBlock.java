package com.likegg.tff.blocks.cabinet;

import com.likegg.tff.blocks.entity.WallCabinetSingleDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WallCabinetSingleDoorBlock extends WallCabinetBlock implements EntityBlock {
    public WallCabinetSingleDoorBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WallCabinetSingleDoorBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof WallCabinetSingleDoorBlockEntity cabinet) {
                player.openMenu(cabinet, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeInt(cabinet.getInventory().getSlots());
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
            if (blockEntity instanceof WallCabinetSingleDoorBlockEntity cabinet) {
                cabinet.dropContents();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
