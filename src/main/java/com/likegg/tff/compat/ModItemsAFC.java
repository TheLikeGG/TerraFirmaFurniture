package com.likegg.tff.compat;

import com.likegg.tff.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import com.therighthon.afc.common.blocks.AFCWood;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;

public class ModItemsAFC {

    // Seats and tables
    public static final Map<AFCWood, DeferredItem<Item>> WOODEN_CHAIR_ITEMS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> WOODEN_SHORT_STOOLS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> WOODEN_TALL_STOOLS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> WOODEN_TABLES = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> LOG_STOOLS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> LOG_ROUND_TABLES = new HashMap<>();

    // Basic counters
    public static final Map<AFCWood, DeferredItem<Item>> BASIC_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_BASIC_WOODEN_COUNTERS = new HashMap<>();

    // Log-Stone counters
    public static final Map<Pair<AFCWood, Rock>, DeferredItem<Item>> LOG_STONE_COUNTERS = new HashMap<>();
    public static final Map<Pair<AFCWood, Rock>, DeferredItem<Item>> STRIPPED_LOG_STONE_COUNTERS = new HashMap<>();

    // Miscellaneous
    public static final Map<AFCWood, DeferredItem<Item>> NIGHT_STANDS_SINGLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> NIGHT_STANDS_DOUBLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_NIGHT_STANDS_SINGLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER = new HashMap<>();

    // Wall Cabinets
    public static final Map<AFCWood, DeferredItem<Item>> LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> LOG_WALL_CABINETS_WITH_SHELF = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_WITH_SHELF = new HashMap<>();

    // Shelves
    public static final Map<AFCWood, DeferredItem<Item>> BASIC_WOODEN_SHELVES = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> BASIC_LOG_SHELVES = new HashMap<>();
    public static final Map<AFCWood, DeferredItem<Item>> BASIC_STRIPPED_LOG_SHELVES = new HashMap<>();


    public static void registerAFCItems() {

        // Seats and tables
        registerBlockItems(ModBlocksAFC.WOODEN_CHAIRS, WOODEN_CHAIR_ITEMS, "_chair");
        registerBlockItems(ModBlocksAFC.WOODEN_SHORT_STOOLS, WOODEN_SHORT_STOOLS, "_short_stool");
        registerBlockItems(ModBlocksAFC.WOODEN_TALL_STOOLS, WOODEN_TALL_STOOLS, "_tall_stool");
        registerBlockItems(ModBlocksAFC.WOODEN_TABLES, WOODEN_TABLES, "_table");
        registerBlockItems(ModBlocksAFC.LOG_STOOLS, LOG_STOOLS, "_log_stool");
        registerBlockItems(ModBlocksAFC.LOG_ROUND_TABLES, LOG_ROUND_TABLES, "_log_round_table");

        // Basic counters
        registerBlockItems(ModBlocksAFC.BASIC_WOODEN_COUNTERS, BASIC_WOODEN_COUNTERS, "_basic_wooden_counter");
        registerBlockItems(ModBlocksAFC.LOG_WOODEN_COUNTERS, LOG_WOODEN_COUNTERS, "_log_wooden_counter");
        registerBlockItems(ModBlocksAFC.STRIPPED_LOG_WOODEN_COUNTERS, STRIPPED_LOG_WOODEN_COUNTERS, "_stripped_log_wooden_counter");
        registerBlockItems(ModBlocksAFC.STRIPPED_BASIC_WOODEN_COUNTERS, STRIPPED_BASIC_WOODEN_COUNTERS, "_stripped_basic_wooden_counter");

        // Log-Stone counters
        registerAFCWoodRockCounters(ModBlocksAFC.LOG_STONE_COUNTERS, LOG_STONE_COUNTERS, "_log_");
        registerAFCWoodRockCounters(ModBlocksAFC.STRIPPED_LOG_STONE_COUNTERS, STRIPPED_LOG_STONE_COUNTERS, "_stripped_log_");

        // Miscellaneous
        registerBlockItems(ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER, NIGHT_STANDS_SINGLE_DRAWER, "_night_stand_single_drawer");
        registerBlockItems(ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER, NIGHT_STANDS_DOUBLE_DRAWER, "_night_stand_double_drawer");
        registerBlockItems(ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "_stripped_night_stand_single_drawer");
        registerBlockItems(ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "_stripped_night_stand_double_drawer");

        // Wall Cabinets
        registerBlockItems(ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR, LOG_WALL_CABINETS_SINGLE_DOOR, "_log_wall_cabinet_single_door");
        registerBlockItems(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "_stripped_log_wall_cabinet_single_door");
        registerBlockItems(ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR, LOG_WALL_CABINETS_DOUBLE_DOOR, "_log_wall_cabinet_double_door");
        registerBlockItems(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "_stripped_log_wall_cabinet_double_door");
        registerBlockItems(ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF, LOG_WALL_CABINETS_WITH_SHELF, "_log_wall_cabinet_with_shelf");
        registerBlockItems(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "_stripped_log_wall_cabinet_with_shelf");

        // Shelves
        registerBlockItems(ModBlocksAFC.BASIC_WOODEN_SHELVES, BASIC_WOODEN_SHELVES, "_basic_wooden_shelf");
        registerBlockItems(ModBlocksAFC.BASIC_LOG_SHELVES, BASIC_LOG_SHELVES, "_basic_log_shelf");
        registerBlockItems(ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES, BASIC_STRIPPED_LOG_SHELVES, "_basic_stripped_log_shelf");

    }

    private static void registerBlockItems(Map<AFCWood, DeferredBlock<Block>> blockMap, Map<AFCWood, DeferredItem<Item>> targetMap, String itemSuffix) {
        blockMap.forEach((wood, block) -> {
            DeferredItem<Item> item = ModItems.ITEMS.register(
                    wood.getSerializedName() + itemSuffix,
                    () -> new BlockItem(block.get(), new Item.Properties())
            );
            targetMap.put(wood, item);
        });
    }

    private static void registerAFCWoodRockCounters(Map<Pair<AFCWood, Rock>, DeferredBlock<Block>> blockMap, Map<Pair<AFCWood, Rock>, DeferredItem<Item>> targetMap, String itemInfix) {
        blockMap.forEach((pair, block) -> {
            DeferredItem<Item> item = ModItems.ITEMS.register(
                    pair.getFirst().getSerializedName() + itemInfix + pair.getSecond().getSerializedName() + "_counter",
                    () -> new BlockItem(block.get(), new Item.Properties())
            );
            targetMap.put(pair, item);
        });
    }

}