package com.likegg.tff.datagen;

import com.likegg.tff.blocks.shelf.BasicShelfBlock;
import com.likegg.tff.registry.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public ModBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Automatically process every registered block in BLOCKS
        for (Holder<Block> entry : ModBlocks.BLOCKS.getEntries()) {
            Block block = entry.value();
            if (block instanceof BasicShelfBlock) {
                // Drop item only when the lower half breaks
                add(block, createDoorTable(block));
            } else {
                dropSelf(block);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(Holder::value)
                .collect(Collectors.toList());
    }
}