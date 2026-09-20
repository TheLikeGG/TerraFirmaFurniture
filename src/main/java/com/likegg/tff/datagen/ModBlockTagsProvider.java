package com.likegg.tff.datagen;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.compat.AFC;
import com.likegg.tff.compat.ModBlocksAFC;
import com.likegg.tff.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TerraFirmaFurniture.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var axeMineable = tag(BlockTags.MINEABLE_WITH_AXE);
        var pickaxeMineable = tag(BlockTags.MINEABLE_WITH_PICKAXE);

        ModBlocks.WOODEN_CHAIRS.values().forEach(chair -> {
            axeMineable.add(chair.get());
        });
        ModBlocks.WOODEN_SHORT_STOOLS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.WOODEN_TALL_STOOLS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.WOODEN_TABLES.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_STOOLS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.BASIC_WOODEN_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_WOODEN_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_LOG_WOODEN_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_BASIC_WOODEN_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_ROUND_TABLES.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_STONE_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
            pickaxeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_LOG_STONE_COUNTERS.values().forEach(block -> {
            axeMineable.add(block.get());
            pickaxeMineable.add(block.get());
        });
        ModBlocks.CANDLE_HOLDERS.values().forEach(block -> {
            pickaxeMineable.add(block.get());
        });
        ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.LOG_WALL_CABINETS_WITH_SHELF.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.BASIC_WOODEN_SHELVES.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.BASIC_LOG_SHELVES.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.BASIC_STRIPPED_LOG_SHELVES.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.NIGHT_STANDS_SINGLE_DRAWER.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.values().forEach(block -> {
            axeMineable.add(block.get());
        });
        ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(block -> {
            axeMineable.add(block.get());
        });

        if (AFC.isLoaded()){
            ModBlocksAFC.WOODEN_CHAIRS.values().forEach(chair -> {
                axeMineable.add(chair.get());
            });
            ModBlocksAFC.WOODEN_SHORT_STOOLS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.WOODEN_TALL_STOOLS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.WOODEN_TABLES.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_STOOLS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.BASIC_WOODEN_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_WOODEN_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_LOG_WOODEN_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_BASIC_WOODEN_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_ROUND_TABLES.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_STONE_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
                pickaxeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_LOG_STONE_COUNTERS.values().forEach(block -> {
                axeMineable.add(block.get());
                pickaxeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.BASIC_WOODEN_SHELVES.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.BASIC_LOG_SHELVES.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.values().forEach(block -> {
                axeMineable.add(block.get());
            });
            ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(block -> {
                axeMineable.add(block.get());
            });
        }
    }
}