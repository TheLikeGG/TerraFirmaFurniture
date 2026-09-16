package com.likegg.tff.entity;

import com.likegg.tff.blocks.seat.SittableBlock;
import com.likegg.tff.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SeatEntity extends Entity {

    public SeatEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public SeatEntity(Level level, BlockPos pos, double yOffset) {
        this(ModEntities.SEAT.get(), level);
        this.setPos(pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            BlockPos pos = this.blockPosition();
            if (this.getPassengers().isEmpty() || !(this.level().getBlockState(pos).getBlock() instanceof SittableBlock)) {
                this.discard();
            }
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (this.getPassengers().isEmpty()) {
            this.discard();
        }
    }

    public static InteractionResult sitAt(Level level, BlockPos pos, double yOffset, Player player) {
        if (!level.isClientSide) {
            List<SeatEntity> seats = level.getEntitiesOfClass(SeatEntity.class, new AABB(pos));
            if (!seats.isEmpty()) {
                SeatEntity seat = seats.get(0);
                if (seat.getPassengers().isEmpty()) {
                    player.startRiding(seat);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }

            SeatEntity seat = new SeatEntity(level, pos, yOffset);
            level.addFreshEntity(seat);
            player.startRiding(seat);
        }
        return InteractionResult.SUCCESS;
    }
}