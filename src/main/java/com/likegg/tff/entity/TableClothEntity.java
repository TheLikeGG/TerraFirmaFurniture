package com.likegg.tff.entity;

import com.likegg.tff.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class TableClothEntity extends Entity {

    // Synced entity data key for color
    private static final EntityDataAccessor<Integer> COLOR_ID =
            SynchedEntityData.defineId(TableClothEntity.class, EntityDataSerializers.INT);

    public TableClothEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        // Default to WHITE (id = 0)
        builder.define(COLOR_ID, DyeColor.WHITE.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(COLOR_ID));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(COLOR_ID, color.getId());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Color")) {
            this.setColor(DyeColor.byId(tag.getInt("Color")));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Color", this.getColor().getId());
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected AABB makeBoundingBox() {
        return new AABB(
                this.getX() - 0.5, this.getY(),     this.getZ() - 0.5,
                this.getX() + 0.5, this.getY() + 0.05, this.getZ() + 0.5
        );
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }

        if (!this.level().isClientSide && !this.isRemoved()) {
            this.discard();

            // Play break sound
            this.level().playSound(
                    null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F
            );

            // Drop item if not broken in creative mode
            boolean isCreative = source.getEntity() instanceof Player player && player.getAbilities().instabuild;
            if (!isCreative) {
                this.spawnAtLocation(ModItems.TABLE_CLOTHS.get(this.getColor()).get());
            }
        }
        return true;
    }
}