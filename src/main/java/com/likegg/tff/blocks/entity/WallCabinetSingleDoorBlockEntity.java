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

public class WallCabinetSingleDoorBlockEntity extends BlockEntity implements MenuProvider, DynamicInventoryHolder {
    private static final int COLUMNS = 6;

    private final ItemStackHandler inventory = new ItemStackHandler(18) {
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
        return getInventory();
    }

    public WallCabinetSingleDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALL_CABINET_SINGLE_DOOR.get(), pos, state);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public void dropContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(i));
        }
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tff.wall_cabinet_double_door");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DynamicInventoryMenu(containerId, playerInventory, this.worldPosition, this.getInventory().getSlots(), COLUMNS);
    }
}