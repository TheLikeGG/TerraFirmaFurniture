package com.likegg.tff.blocks.entity;

import com.likegg.tff.menu.DynamicInventoryMenu;
import com.likegg.tff.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class NightStandSingleDrawerBlockEntity extends BlockEntity implements MenuProvider, DynamicInventoryHolder {
    public static final int COLUMNS = 6;

    private final ItemStackHandler inventory = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    public NightStandSingleDrawerBlockEntity(BlockPos pos, BlockState state){
        super (ModBlockEntities.NIGHT_STAND_SINGLE_DRAWER.get(), pos, state);
    }

    @Override
    public IItemHandler getDynamicInventory() {
        return getInventory();
    }

    public int getColumns() {
        return COLUMNS;
    }

    public ItemStackHandler getInventory() { return inventory; }

    public void dropContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tff.night_stand_single_drawer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DynamicInventoryMenu(containerId, playerInventory, this.worldPosition, this.getInventory().getSlots(), COLUMNS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
    }

}
