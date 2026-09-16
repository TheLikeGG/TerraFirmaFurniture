package com.likegg.tff.blocks.entity;

import com.likegg.tff.blocks.shelf.BasicShelfBlock;
import com.likegg.tff.registry.ModBlockEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class BasicShelfBlockEntity extends BlockEntity implements DynamicInventoryHolder {

    // 8 slots per block entity: Slots 0-3 (Top shelf), Slots 4-7 (Bottom shelf)
    private final ItemStackHandler inventory = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ItemValidator.isItemValid(stack) && super.isItemValid(slot, stack);
        }
    };

    public BasicShelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BASIC_SHELF.get(), pos, state);
    }

    @Override
    public IItemHandler getDynamicInventory() {
        return getInventory();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemInteractionResult interactWithShelf(Player player, InteractionHand hand, BlockHitResult hit) {
        if (level == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        //Logger LOGGER = LogUtils.getLogger();

        Direction facing = getBlockState().getValue(BasicShelfBlock.FACING);
        Vec3 hitVec = hit.getLocation();

        // Calculate click coordinates local to this block (0.0 to 1.0)
        double relX;
        switch (facing) {
            case NORTH -> {
                relX = 1 - (hitVec.x -worldPosition.getX());
            }
            case SOUTH -> {
                relX = hitVec.x -worldPosition.getX();
            }
            case EAST -> {
                relX = 1 - (hitVec.z -worldPosition.getZ());
            }
            default -> {
                relX = hitVec.z -worldPosition.getZ();
            }
        }
        double relY = hitVec.y - worldPosition.getY();

        boolean topShelf = relY >= 0.43;
        // Map local hit coordinates to shelf-space
        int target_slot = Math.clamp((int)(relX * 4), 0, 3);
        if (!topShelf) target_slot += 4;

        //LOGGER.debug("Target X: " + relX + ", Target Y: " + relY);
        //LOGGER.debug("Top shelf check: " + (topShelf ? "top" : "bottom"));
        //LOGGER.debug("Slot number check: " + target_slot);


        ItemStack slotStack = inventory.getStackInSlot(target_slot);
        ItemStack heldStack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        if (slotStack.isEmpty() && !heldStack.isEmpty()) {
            // Insert item logic
            ItemStack insert = heldStack.copyWithCount(1);
            inventory.setStackInSlot(target_slot, insert);
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
            level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 0.5f, 1.0f);
            return ItemInteractionResult.SUCCESS;
        }
        else if (heldStack.isEmpty() && !slotStack.isEmpty()){
            // Extract item logic (only bare handed)
            ItemStack extract = slotStack.copy(); // it's always 1 item anyway no need to copy with count
            inventory.setStackInSlot(target_slot, ItemStack.EMPTY);
            ItemHandlerHelper.giveItemToPlayer(player, extract);
            level.playSound(null, worldPosition, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5f, 0.8f);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void dropContents() {
        if (level == null) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
            }
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