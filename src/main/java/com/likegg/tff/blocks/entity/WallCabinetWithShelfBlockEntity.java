package com.likegg.tff.blocks.entity;

import com.likegg.tff.menu.DynamicInventoryMenu;
import com.likegg.tff.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class WallCabinetWithShelfBlockEntity extends BlockEntity implements MenuProvider, DynamicInventoryHolder {
    private final int COLUMNS = 6;

    // Top drawer GUI inventory
    private final ItemStackHandler inventory = new ItemStackHandler(12) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    // Bottom shelf display inventory (4 slots rendered in fixed visual positions)
    private final ItemStackHandler shelfInventory = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()){
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    public WallCabinetWithShelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALL_CABINET_WITH_SHELF.get(), pos, state);
    }

    @Override
    public IItemHandler getDynamicInventory() {
        return getInventory();
    }

    public ItemStackHandler getInventory(){
        return inventory;
    }

    public ItemStackHandler getShelfInventory() {
        return shelfInventory;
    }

    public int getColumns() {
        return COLUMNS;
    }

    /**
     * Handles LIFO (Stack) interactions for the shelf.
     */
    public boolean interactWithShelf(Player player, ItemStack handStack) {
        // 1. IF HOLDING AN ITEM: Try to PUSH into the first empty slot (0 -> 3)
        if (!handStack.isEmpty() && ItemValidator.isItemValid(handStack)) {
            for (int i = 0; i < shelfInventory.getSlots(); i++) {
                if (shelfInventory.getStackInSlot(i).isEmpty()) {
                    ItemStack toInsert = player.isCreative() ? handStack.copy() : handStack.split(1);
                    toInsert.setCount(1);
                    shelfInventory.setStackInSlot(i, toInsert);
                    return true;
                }
            }
            return false; // Shelf is full
        }

        // 2. IF HAND IS EMPTY: Try to POP from the last filled slot (3 -> 0)
        else {
            for (int i = shelfInventory.getSlots() - 1; i >= 0; i--) {
                ItemStack stackInSlot = shelfInventory.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(player, stackInSlot.copy());
                    shelfInventory.setStackInSlot(i, ItemStack.EMPTY);
                    return true;
                }
            }
            return false; // Shelf is empty
        }
    }

    public void dropContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(i));
        }
        for (int i = 0; i < shelfInventory.getSlots(); i++){
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), shelfInventory.getStackInSlot(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.tff.wall_cabinet_with_shelf");
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
        tag.put("Shelf", shelfInventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
        if (tag.contains("Shelf")) {
            shelfInventory.deserializeNBT(registries, tag.getCompound("Shelf"));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, registries);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }
}