package com.likegg.tff.compat;

import com.likegg.tff.blocks.cabinet.WallCabinetDoubleDoorBlock;
import com.likegg.tff.blocks.cabinet.WallCabinetSingleDoorBlock;
import com.likegg.tff.blocks.cabinet.WallCabinetWithShelfBlock;
import com.likegg.tff.blocks.counter.*;
import com.likegg.tff.blocks.miscellaneous.NightStandDoubleDrawerBlock;
import com.likegg.tff.blocks.miscellaneous.NightStandSingleDrawerBlock;
import com.likegg.tff.blocks.seat.ChairBlock;
import com.likegg.tff.blocks.seat.LogStoolBlock;
import com.likegg.tff.blocks.seat.ShortStoolBlock;
import com.likegg.tff.blocks.seat.TallStoolBlock;
import com.likegg.tff.blocks.shelf.BasicShelfBlock;
import com.likegg.tff.blocks.table.LogRoundTable;
import com.likegg.tff.blocks.table.WoodenTableBlock;
import com.likegg.tff.registry.ModBlocks;
import com.mojang.datafixers.util.Pair;
import com.therighthon.afc.common.blocks.AFCWood;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ModBlocksAFC {
    // Seats
    public static final Map<AFCWood, DeferredBlock<Block>> WOODEN_CHAIRS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> WOODEN_SHORT_STOOLS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> WOODEN_TALL_STOOLS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_STOOLS = new HashMap<>();
    // Tables
    public static final Map<AFCWood, DeferredBlock<Block>> WOODEN_TABLES = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_ROUND_TABLES = new HashMap<>();
    // Counters
    public static final Map<AFCWood, DeferredBlock<Block>> BASIC_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_BASIC_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<Pair<AFCWood, Rock>, DeferredBlock<Block>> LOG_STONE_COUNTERS = new HashMap<>();
    public static final Map<Pair<AFCWood, Rock>, DeferredBlock<Block>> STRIPPED_LOG_STONE_COUNTERS = new HashMap<>();
    // Miscellaneous
    public static final Map<AFCWood, DeferredBlock<Block>> NIGHT_STANDS_SINGLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> NIGHT_STANDS_DOUBLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_NIGHT_STANDS_SINGLE_DRAWER = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER = new HashMap<>();
    // Cabinets
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> LOG_WALL_CABINETS_WITH_SHELF = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_WITH_SHELF = new HashMap<>();
    // Shelves
    public static final Map<AFCWood, DeferredBlock<Block>> BASIC_WOODEN_SHELVES = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> BASIC_LOG_SHELVES = new HashMap<>();
    public static final Map<AFCWood, DeferredBlock<Block>> BASIC_STRIPPED_LOG_SHELVES = new HashMap<>();


    public static void registerBlocks() {
        registerAFCWoodBlock(WOODEN_CHAIRS, ChairBlock::new, "_chair");
        registerAFCWoodBlock(WOODEN_SHORT_STOOLS, ShortStoolBlock::new, "_short_stool");
        registerAFCWoodBlock(WOODEN_TALL_STOOLS, TallStoolBlock::new, "_tall_stool");
        registerAFCWoodBlock(LOG_STOOLS, LogStoolBlock::new, "_log_stool");

        registerAFCWoodBlock(WOODEN_TABLES, WoodenTableBlock::new, "_table");
        registerAFCWoodBlock(LOG_ROUND_TABLES, LogRoundTable::new, "_log_round_table");

        registerAFCWoodBlock(BASIC_WOODEN_COUNTERS, BasicWoodenCounterBlock::new, "_basic_wooden_counter");
        registerAFCWoodBlock(LOG_WOODEN_COUNTERS, LogWoodenCounterBlock::new, "_log_wooden_counter");
        registerAFCWoodBlock(STRIPPED_LOG_WOODEN_COUNTERS, StrippedLogWoodenCounterBlock::new, "_stripped_log_wooden_counter");
        registerAFCWoodBlock(STRIPPED_BASIC_WOODEN_COUNTERS, StrippedBasicWoodenCounterBlock::new, "_stripped_basic_wooden_counter");

        registerAFCWoodBlock(NIGHT_STANDS_SINGLE_DRAWER, NightStandSingleDrawerBlock::new, "_night_stand_single_drawer");
        registerAFCWoodBlock(NIGHT_STANDS_DOUBLE_DRAWER, NightStandDoubleDrawerBlock::new, "_night_stand_double_drawer");
        registerAFCWoodBlock(STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, NightStandSingleDrawerBlock::new, "_stripped_night_stand_single_drawer");
        registerAFCWoodBlock(STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, NightStandDoubleDrawerBlock::new, "_stripped_night_stand_double_drawer");

        registerAFCWoodBlock(LOG_WALL_CABINETS_SINGLE_DOOR, WallCabinetSingleDoorBlock::new, "_log_wall_cabinet_single_door");
        registerAFCWoodBlock(STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, WallCabinetSingleDoorBlock::new, "_stripped_log_wall_cabinet_single_door");
        registerAFCWoodBlock(LOG_WALL_CABINETS_DOUBLE_DOOR, WallCabinetDoubleDoorBlock::new, "_log_wall_cabinet_double_door");
        registerAFCWoodBlock(STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, WallCabinetDoubleDoorBlock::new, "_stripped_log_wall_cabinet_double_door");
        registerAFCWoodBlock(LOG_WALL_CABINETS_WITH_SHELF, WallCabinetWithShelfBlock::new, "_log_wall_cabinet_with_shelf");
        registerAFCWoodBlock(STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, WallCabinetWithShelfBlock::new, "_stripped_log_wall_cabinet_with_shelf");

        registerAFCWoodBlock(BASIC_WOODEN_SHELVES, BasicShelfBlock::new, "_basic_wooden_shelf");
        registerAFCWoodBlock(BASIC_LOG_SHELVES, BasicShelfBlock::new, "_basic_log_shelf");
        registerAFCWoodBlock(BASIC_STRIPPED_LOG_SHELVES, BasicShelfBlock::new, "_basic_stripped_log_shelf");

        for (AFCWood wood : AFCWood.values()){
            for (Rock rock: Rock.VALUES) {
                String woodName = wood.getSerializedName();
                String rockName = rock.getSerializedName();

                LOG_STONE_COUNTERS.put(new Pair<AFCWood, Rock>(wood, rock), ModBlocks.BLOCKS.registerBlock(woodName + "_log_" + rockName + "_counter",
                        properties -> new LogStoneCounterBlock(BlockBehaviour.Properties.of()
                                .strength(3.5F).noOcclusion().sound(SoundType.STONE).requiresCorrectToolForDrops())));
                STRIPPED_LOG_STONE_COUNTERS.put(new Pair<AFCWood, Rock>(wood, rock), ModBlocks.BLOCKS.registerBlock(woodName + "_stripped_log_" + rockName + "_counter",
                        properties -> new StrippedLogStoneCounterBlock(BlockBehaviour.Properties.of()
                                .strength(3.5F).noOcclusion().sound(SoundType.STONE).requiresCorrectToolForDrops())));
            }
        }
    }

    private static <T extends Block> void registerAFCWoodBlock(Map<AFCWood, DeferredBlock<Block>> map, Function<BlockBehaviour.Properties, T> blockFactory, String nameSuffix
    ) {
        for (AFCWood wood : AFCWood.values()) {
            String woodName = wood.getSerializedName();
            map.put(wood, ModBlocks.BLOCKS.registerBlock(woodName + nameSuffix,
                    props -> blockFactory.apply(
                            BlockBehaviour.Properties.of()
                                    .strength(2.0F)
                                    .noOcclusion()
                                    .dynamicShape()
                                    .sound(SoundType.WOOD)
                    )
            ));
        }
    }

}