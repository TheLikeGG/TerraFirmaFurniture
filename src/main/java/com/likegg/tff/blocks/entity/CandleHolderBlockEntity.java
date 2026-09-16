package com.likegg.tff.blocks.entity;

import com.likegg.tff.registry.ModBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import com.likegg.tff.blocks.miscellaneous.CandleHolderBlock;

public class CandleHolderBlockEntity extends TickCounterBlockEntity {
    private ItemStack heldCandle = ItemStack.EMPTY;

    public CandleHolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CANDLE_HOLDER.get(), pos, state);
    }

    public ItemStack getHeldCandle() {
        return this.heldCandle;
    }

    public void setHeldCandle(ItemStack candle) {
        this.heldCandle = candle.copy();
        this.setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean hasCandle() {
        return !this.heldCandle.isEmpty();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CandleHolderBlockEntity entity) {
        if (!state.getValue(CandleHolderBlock.LIT)) return;

        // Fetches TFC's candle duration config (in ticks)
        final long candleTicks = TFCConfig.SERVER.candleTicks.get();
        // Checks how many calendar ticks have passed since the candle was lit
        if (entity.getTicksSinceUpdate() > candleTicks && candleTicks > 0) {
            // Candle burned out
            level.setBlockAndUpdate(pos, state.setValue(CandleHolderBlock.LIT, false));
            level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries); // Saves TFC's "tick" tag automatically
        if (!heldCandle.isEmpty()) {
            tag.put("HeldCandle", heldCandle.save(registries));
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries); // Loads TFC's "tick" tag automatically
        if (tag.contains("HeldCandle")) {
            this.heldCandle = ItemStack.parse(registries, tag.getCompound("HeldCandle")).orElse(ItemStack.EMPTY);
        } else {
            this.heldCandle = ItemStack.EMPTY;
        }
    }
}