package com.likegg.tff.registry;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.blocks.miscellaneous.CandleHolderBlock;
import com.likegg.tff.compat.AFC;
import com.likegg.tff.compat.ModItemsAFC;
import com.likegg.tff.items.TableClothItem;
import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TerraFirmaFurniture.MODID);

    // Seats and tables
    public static final Map<Wood, DeferredItem<Item>> WOODEN_CHAIR_ITEMS = registerBlockItems(ModBlocks.WOODEN_CHAIRS, "_chair");
    public static final Map<Wood, DeferredItem<Item>> WOODEN_SHORT_STOOLS = registerBlockItems(ModBlocks.WOODEN_SHORT_STOOLS, "_short_stool");
    public static final Map<Wood, DeferredItem<Item>> WOODEN_TALL_STOOLS = registerBlockItems(ModBlocks.WOODEN_TALL_STOOLS, "_tall_stool");
    public static final Map<Wood, DeferredItem<Item>> WOODEN_TABLES = registerBlockItems(ModBlocks.WOODEN_TABLES, "_table");
    public static final Map<Wood, DeferredItem<Item>> LOG_STOOLS = registerBlockItems(ModBlocks.LOG_STOOLS, "_log_stool");
    public static final Map<Wood, DeferredItem<Item>> LOG_ROUND_TABLES = registerBlockItems(ModBlocks.LOG_ROUND_TABLES, "_log_round_table");

    // Basic counters
    public static final Map<Wood, DeferredItem<Item>> BASIC_WOODEN_COUNTERS = registerBlockItems(ModBlocks.BASIC_WOODEN_COUNTERS, "_basic_wooden_counter");
    public static final Map<Wood, DeferredItem<Item>> LOG_WOODEN_COUNTERS = registerBlockItems(ModBlocks.LOG_WOODEN_COUNTERS, "_log_wooden_counter");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_LOG_WOODEN_COUNTERS = registerBlockItems(ModBlocks.STRIPPED_LOG_WOODEN_COUNTERS, "_stripped_log_wooden_counter");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_BASIC_WOODEN_COUNTERS = registerBlockItems(ModBlocks.STRIPPED_BASIC_WOODEN_COUNTERS, "_stripped_basic_wooden_counter");

    // Log-Stone counters
    public static final Map<Pair<Wood, Rock>, DeferredItem<Item>> LOG_STONE_COUNTERS = registerWoodRockCounters(ModBlocks.LOG_STONE_COUNTERS, "_log_");
    public static final Map<Pair<Wood, Rock>, DeferredItem<Item>> STRIPPED_LOG_STONE_COUNTERS = registerWoodRockCounters(ModBlocks.STRIPPED_LOG_STONE_COUNTERS, "_stripped_log_");


    // Counter Drawers (Add your new drawer-counter block maps here)
    // public static final Map<Wood, DeferredItem<Item>> BASIC_LARGE_DRAWER_COUNTERS = registerBlockItems(ModBlocks.BASIC_LARGE_DRAWER_COUNTERS, "_basic_counter_large_drawer");

    // Drawer items
    //public static final Map<Wood, DeferredItem<Item>> LARGE_DRAWERS = registerDrawers("_large_drawer");
    //public static final Map<Wood, DeferredItem<Item>> MEDIUM_DRAWERS = registerDrawers("_medium_drawer");
    //public static final Map<Wood, DeferredItem<Item>> SMALL_DRAWERS = registerDrawers("_small_drawer");

    // Table cloths
    public static final Map<DyeColor, DeferredItem<TableClothItem>> TABLE_CLOTHS = registerTableCloths();

    // Miscellaneous
    public static final Map<Metal, DeferredItem<Item>> CANDLE_HOLDERS = registerCandleHolders(ModBlocks.CANDLE_HOLDERS);
    public static final Map<Wood, DeferredItem<Item>> NIGHT_STANDS_SINGLE_DRAWER = registerBlockItems(ModBlocks.NIGHT_STANDS_SINGLE_DRAWER, "_night_stand_single_drawer");
    public static final Map<Wood, DeferredItem<Item>> NIGHT_STANDS_DOUBLE_DRAWER = registerBlockItems(ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER, "_night_stand_double_drawer");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_NIGHT_STANDS_SINGLE_DRAWER = registerBlockItems(ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "_stripped_night_stand_single_drawer");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER = registerBlockItems(ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "_stripped_night_stand_double_drawer");

    // Wall Cabinets
    public static final Map<Wood, DeferredItem<Item>> LOG_WALL_CABINETS_SINGLE_DOOR = registerBlockItems(ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR, "_log_wall_cabinet_single_door");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR = registerBlockItems(ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "_stripped_log_wall_cabinet_single_door");
    public static final Map<Wood, DeferredItem<Item>> LOG_WALL_CABINETS_DOUBLE_DOOR = registerBlockItems(ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR, "_log_wall_cabinet_double_door");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR = registerBlockItems(ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "_stripped_log_wall_cabinet_double_door");
    public static final Map<Wood, DeferredItem<Item>> LOG_WALL_CABINETS_WITH_SHELF = registerBlockItems(ModBlocks.LOG_WALL_CABINETS_WITH_SHELF, "_log_wall_cabinet_with_shelf");
    public static final Map<Wood, DeferredItem<Item>> STRIPPED_LOG_WALL_CABINETS_WITH_SHELF = registerBlockItems(ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "_stripped_log_wall_cabinet_with_shelf");

    // Shelves
    public static final Map<Wood, DeferredItem<Item>> BASIC_WOODEN_SHELVES = registerBlockItems(ModBlocks.BASIC_WOODEN_SHELVES, "_basic_wooden_shelf");
    public static final Map<Wood, DeferredItem<Item>> BASIC_LOG_SHELVES = registerBlockItems(ModBlocks.BASIC_LOG_SHELVES, "_basic_log_shelf");
    public static final Map<Wood, DeferredItem<Item>> BASIC_STRIPPED_LOG_SHELVES = registerBlockItems(ModBlocks.BASIC_STRIPPED_LOG_SHELVES, "_basic_stripped_log_shelf");



    private static Map<Wood, DeferredItem<Item>> registerBlockItems(Map<Wood, DeferredBlock<Block>> blockMap, String itemSuffix) {
        Map<Wood, DeferredItem<Item>> map = new HashMap<>();
        blockMap.forEach((wood, block) -> {
            DeferredItem<Item> item = ITEMS.register(
                    wood.getSerializedName() + itemSuffix,
                    () -> new BlockItem(block.get(), new Item.Properties())
            );
            map.put(wood, item);
        });
        return map;
    }

    private static Map<Pair<Wood, Rock>, DeferredItem<Item>> registerWoodRockCounters(Map<Pair<Wood, Rock>, DeferredBlock<Block>> blockMap, String itemInfix) {
        Map<Pair<Wood, Rock>, DeferredItem<Item>> map = new HashMap<>();
        blockMap.forEach((pair, block) -> {
            DeferredItem<Item> item = ITEMS.register(
                    pair.getFirst().getSerializedName() + itemInfix + pair.getSecond().getSerializedName() + "_counter",
                    () -> new BlockItem(block.get(), new Item.Properties())
            );
            map.put(pair, item);
        });
        return map;
    }

    private static Map<DyeColor, DeferredItem<TableClothItem>> registerTableCloths(){
        Map<DyeColor, DeferredItem<TableClothItem>> map = new HashMap<>();
        for (DyeColor colour : DyeColor.values()){
            map.put(colour, ITEMS.register(
                    colour.getName() + "_small_table_cloth",
                    () -> new TableClothItem(colour, new Item.Properties())
            ));
        }
        return map;
    }

    private static Map<Metal, DeferredItem<Item>> registerCandleHolders(Map<Metal, DeferredBlock<CandleHolderBlock>> blockMap) {
        Map<Metal, DeferredItem<Item>> map = new HashMap<>();
        blockMap.forEach((metal, block) -> {
            DeferredItem<Item> item = ITEMS.register(
                    metal.getSerializedName() + "_candle_holder",
                    () -> new BlockItem(block.get(), new Item.Properties())
            );
            map.put(metal, item);
        });
        return map;
    }


    // AFC
    static {
        if (AFC.isLoaded()) {
            ModItemsAFC.registerAFCItems();
        }
    }

}
