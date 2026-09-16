package com.likegg.tff.blocks.entity;

import net.dries007.tfc.common.component.size.ItemSizeManager;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public interface DynamicInventoryHolder {
    IItemHandler getDynamicInventory();
}