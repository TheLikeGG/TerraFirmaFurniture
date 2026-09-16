package com.likegg.tff.blocks.entity;

import net.dries007.tfc.common.component.size.ItemSizeManager;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.world.item.ItemStack;

public class ItemValidator {
    public static boolean isItemValid(ItemStack stack){
        return ItemSizeManager.get(stack).getSize(stack).isEqualOrSmallerThan(TFCConfig.SERVER.chestMaximumItemSize.get());
    }
}
