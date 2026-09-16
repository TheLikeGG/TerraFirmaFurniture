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

public class NightStandDoubleDrawerBlockEntity extends BlockEntity implements MenuProvider, DynamicInventoryHolder {
    public static final int COLUMNS = 6;
    private boolean selected_top = true;

    private final ItemStackHandler inventory_top = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };
    private final ItemStackHandler inventory_bottom = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    public NightStandDoubleDrawerBlockEntity(BlockPos pos, BlockState state){
        super (ModBlockEntities.NIGHT_STAND_DOUBLE_DRAWER.get(), pos, state);
    }

    @Override
    public IItemHandler getDynamicInventory() {
        return getInventory(this.selected_top);
    }

    public void selectDrawer(boolean top){
        selected_top = top;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public ItemStackHandler getInventory( boolean top ) { return top ? inventory_top : inventory_bottom; }

    public void dropContents() {
        for (int i = 0; i < inventory_top.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory_top.getStackInSlot(i));
        }
        for (int i = 0; i < inventory_bottom.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory_bottom.getStackInSlot(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tff.night_stand_double_drawer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DynamicInventoryMenu(containerId, playerInventory, this.worldPosition, getInventory(selected_top).getSlots(), COLUMNS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("TopInventory", inventory_top.serializeNBT(registries));
        tag.put("BottomInventory", inventory_bottom.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("TopInventory")) {
            inventory_top.deserializeNBT(registries, tag.getCompound("TopInventory"));
        }
        if (tag.contains("BottomInventory")) {
            inventory_bottom.deserializeNBT(registries, tag.getCompound("BottomInventory"));
        }
    }

}
