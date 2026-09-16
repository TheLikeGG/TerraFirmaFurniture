package com.likegg.tff.menu;

import com.likegg.tff.blocks.entity.DynamicInventoryHolder;
import com.likegg.tff.blocks.entity.WallCabinetSingleDoorBlockEntity;
import com.likegg.tff.registry.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class DynamicInventoryMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final int containerSize;
    private final int playerInvStartY;

    // Client-side network constructor (reads data sent from server)
    public DynamicInventoryMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, extraData.readBlockPos(), extraData.readInt(), extraData.readInt());
    }

    // Main constructor used by both Client and Server
    public DynamicInventoryMenu(int containerId, Inventory playerInv, BlockPos pos, int slotCount, int columns) {
        super(ModMenuTypes.DYNAMIC_INVENTORY_MENU.get(), containerId);

        BlockEntity be = playerInv.player.level().getBlockEntity(pos);
        IItemHandler handler;

        // Check against the generic interface on the server side
        if (!playerInv.player.level().isClientSide && be instanceof DynamicInventoryHolder holder) {
            handler = holder.getDynamicInventory();
        } else {
            // Client-side fallback using synced slot count
            handler = new ItemStackHandler(slotCount);
        }

        this.access = ContainerLevelAccess.create(playerInv.player.level(), pos);

        this.containerSize = handler.getSlots();
        // -------------------

        int targetColumns = Math.max(1, columns);
        int rows = (int) Math.ceil((double) this.containerSize / targetColumns);

        int guiWidth = Math.max(176, targetColumns * 18 + 14);
        int startX = (guiWidth - (targetColumns * 18)) / 2;
        int startY = 18;

        // 1. Cabinet Slots
        for (int i = 0; i < this.containerSize; i++) {
            int col = i % targetColumns;
            int row = i / targetColumns;
            this.addSlot(new SlotItemHandler(handler, i, startX + col * 18, startY + row * 18));
        }

        // 2. Player Inventory (27 slots)
        this.playerInvStartY = startY + (rows * 18) + 14;
        int playerInvX = (guiWidth - (9 * 18)) / 2;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, playerInvX + col * 18, this.playerInvStartY + row * 18));
            }
        }

        // 3. Player Hotbar (9 slots)
        int hotbarY = this.playerInvStartY + 58;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, playerInvX + col * 18, hotbarY));
        }
    }

    public int getPlayerInvStartY() {
        return this.playerInvStartY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, this.access.evaluate((level, pos) -> level.getBlockState(pos).getBlock(), null));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < this.containerSize) {
                if (!this.moveItemStackTo(itemstack1, this.containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}