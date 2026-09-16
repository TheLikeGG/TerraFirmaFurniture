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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class WallCabinetDoubleDoorBlockEntity extends BlockEntity implements MenuProvider, DynamicInventoryHolder {
    private static final int COLUMNS = 2;
    boolean useLeftSide = true;

    private final ItemStackHandler inventory_left = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };
    private final ItemStackHandler inventory_right = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    @Override
    public IItemHandler getDynamicInventory() {
        return getInventory(this.useLeftSide);
    }

    public WallCabinetDoubleDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALL_CABINET_DOUBLE_DOOR.get(), pos, state);
    }

    public ItemStackHandler getInventory(boolean left) {
        return left ? inventory_left : inventory_right;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public void dropContents() {
        for (int i = 0; i < inventory_left.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory_left.getStackInSlot(i));
        }
        for (int i = 0; i < inventory_right.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory_right.getStackInSlot(i));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("InventoryLeft", inventory_left.serializeNBT(registries));
        tag.put("InventoryRight", inventory_right.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("InventoryLeft")) {
            inventory_left.deserializeNBT(registries, tag.getCompound("InventoryLeft"));
        }
        if (tag.contains("InventoryRight")) {
            inventory_right.deserializeNBT(registries, tag.getCompound("InventoryRight"));
        }
    }

    public void setSide(boolean newSide){
        useLeftSide = newSide;
    }
    public boolean useLeftSide(){
        return useLeftSide;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tff.wall_cabinet_double_door");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DynamicInventoryMenu(containerId, playerInventory, this.worldPosition, getInventory(useLeftSide).getSlots(), COLUMNS);
    }
}