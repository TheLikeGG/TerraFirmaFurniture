package com.likegg.tff.registry;

import com.likegg.tff.blocks.cabinet.WallCabinetDoubleDoorBlock;
import com.likegg.tff.blocks.cabinet.WallCabinetSingleDoorBlock;
import com.likegg.tff.blocks.cabinet.WallCabinetWithShelfBlock;
import com.likegg.tff.blocks.counter.*;
import com.likegg.tff.blocks.miscellaneous.CandleHolderBlock;
import com.likegg.tff.blocks.miscellaneous.NightStandDoubleDrawerBlock;
import com.likegg.tff.blocks.miscellaneous.NightStandSingleDrawerBlock;
import com.likegg.tff.blocks.seat.ChairBlock;
import com.likegg.tff.blocks.seat.LogStoolBlock;
import com.likegg.tff.blocks.seat.ShortStoolBlock;
import com.likegg.tff.blocks.seat.TallStoolBlock;
import com.likegg.tff.blocks.shelf.BasicShelfBlock;
import com.likegg.tff.blocks.table.LogRoundTable;
import com.likegg.tff.blocks.table.WoodenTableBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.common.blocks.rock.Rock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("tff");

    // Seats
    public static final Map<Wood, DeferredBlock<Block>> WOODEN_CHAIRS = registerWoodBlock(ChairBlock::new, "_chair");
    public static final Map<Wood, DeferredBlock<Block>> WOODEN_SHORT_STOOLS = registerWoodBlock(ShortStoolBlock::new, "_short_stool");
    public static final Map<Wood, DeferredBlock<Block>> WOODEN_TALL_STOOLS = registerWoodBlock(TallStoolBlock::new, "_tall_stool");
    public static final Map<Wood, DeferredBlock<Block>> LOG_STOOLS = registerWoodBlock(LogStoolBlock::new, "_log_stool");
    // Tables
    public static final Map<Wood, DeferredBlock<Block>> WOODEN_TABLES = registerWoodBlock(WoodenTableBlock::new, "_table");
    public static final Map<Wood, DeferredBlock<Block>> LOG_ROUND_TABLES = registerWoodBlock(LogRoundTable::new, "_log_round_table");
    // Counters
    public static final Map<Wood, DeferredBlock<Block>> BASIC_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_LOG_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_BASIC_WOODEN_COUNTERS = new HashMap<>();
    public static final Map<Pair<Wood, Rock>, DeferredBlock<Block>> LOG_STONE_COUNTERS = new HashMap<>();
    public static final Map<Pair<Wood, Rock>, DeferredBlock<Block>> STRIPPED_LOG_STONE_COUNTERS = new HashMap<>();
    // Miscellaneous
    public static final Map<Metal, DeferredBlock<CandleHolderBlock>> CANDLE_HOLDERS = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> NIGHT_STANDS_SINGLE_DRAWER = registerWoodBlock(NightStandSingleDrawerBlock::new, "_night_stand_single_drawer");
    public static final Map<Wood, DeferredBlock<Block>> NIGHT_STANDS_DOUBLE_DRAWER = registerWoodBlock(NightStandDoubleDrawerBlock::new, "_night_stand_double_drawer");
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_NIGHT_STANDS_SINGLE_DRAWER = registerWoodBlock(NightStandSingleDrawerBlock::new, "_stripped_night_stand_single_drawer");
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER = registerWoodBlock(NightStandDoubleDrawerBlock::new, "_stripped_night_stand_double_drawer");
    // Cabinets
    public static final Map<Wood, DeferredBlock<Block>> LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR = new HashMap<>();
    public static final Map<Wood, DeferredBlock<Block>> LOG_WALL_CABINETS_WITH_SHELF = registerWoodBlock(WallCabinetWithShelfBlock::new, "_log_wall_cabinet_with_shelf");
    public static final Map<Wood, DeferredBlock<Block>> STRIPPED_LOG_WALL_CABINETS_WITH_SHELF = registerWoodBlock(WallCabinetWithShelfBlock::new, "_stripped_log_wall_cabinet_with_shelf");
    // Shelves
    public static final Map<Wood, DeferredBlock<Block>> BASIC_WOODEN_SHELVES = registerWoodBlock(BasicShelfBlock::new, "_basic_wooden_shelf");
    public static final Map<Wood, DeferredBlock<Block>> BASIC_LOG_SHELVES = registerWoodBlock(BasicShelfBlock::new, "_basic_log_shelf");
    public static final Map<Wood, DeferredBlock<Block>> BASIC_STRIPPED_LOG_SHELVES = registerWoodBlock(BasicShelfBlock::new, "_basic_stripped_log_shelf");

    private static <T extends Block> Map<Wood, DeferredBlock<Block>> registerWoodBlock(Function<BlockBehaviour.Properties, T> blockFactory, String name) {
        Map<Wood, DeferredBlock<Block>> map = new HashMap<>();
        for (Wood wood : Wood.VALUES) {
            String woodName = wood.getSerializedName();
            map.put(wood, BLOCKS.registerBlock(woodName + name,
                    props -> blockFactory.apply(BlockBehaviour.Properties.of().strength(2.0F).noOcclusion().dynamicShape().sound(SoundType.WOOD))
                    ));
        }
        return map;
    }

    static {
        for (Wood wood : Wood.VALUES) {
            String woodName = wood.getSerializedName();

            BASIC_WOODEN_COUNTERS.put(wood, BLOCKS.registerBlock(woodName + "_basic_wooden_counter",
                    properties -> new BasicWoodenCounterBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            LOG_WOODEN_COUNTERS.put(wood, BLOCKS.registerBlock(woodName + "_log_wooden_counter",
                    properties -> new LogWoodenCounterBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            STRIPPED_LOG_WOODEN_COUNTERS.put(wood, BLOCKS.registerBlock(woodName + "_stripped_log_wooden_counter",
                    properties -> new StrippedLogWoodenCounterBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            STRIPPED_BASIC_WOODEN_COUNTERS.put(wood, BLOCKS.registerBlock(woodName + "_stripped_basic_wooden_counter",
                    properties -> new StrippedBasicWoodenCounterBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            LOG_WALL_CABINETS_SINGLE_DOOR.put(wood, BLOCKS.registerBlock(woodName + "_log_wall_cabinet_single_door",
                    properties -> new WallCabinetSingleDoorBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.put(wood, BLOCKS.registerBlock(woodName + "_stripped_log_wall_cabinet_single_door",
                    properties -> new WallCabinetSingleDoorBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            LOG_WALL_CABINETS_DOUBLE_DOOR.put(wood, BLOCKS.registerBlock(woodName + "_log_wall_cabinet_double_door",
                    properties -> new WallCabinetDoubleDoorBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.put(wood, BLOCKS.registerBlock(woodName + "_stripped_log_wall_cabinet_double_door",
                    properties -> new WallCabinetDoubleDoorBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F).noOcclusion().sound(SoundType.WOOD)
                    )
            ));
            for (Rock rock: Rock.VALUES) {
                String rockName = rock.getSerializedName();

                LOG_STONE_COUNTERS.put(new Pair<Wood, Rock>(wood, rock), BLOCKS.registerBlock(woodName + "_log_" + rockName + "_counter",
                        properties -> new LogStoneCounterBlock(BlockBehaviour.Properties.of()
                                .strength(3.5F).noOcclusion().sound(SoundType.STONE).requiresCorrectToolForDrops())));
                STRIPPED_LOG_STONE_COUNTERS.put(new Pair<Wood, Rock>(wood, rock), BLOCKS.registerBlock(woodName + "_stripped_log_" + rockName + "_counter",
                        properties -> new StrippedLogStoneCounterBlock(BlockBehaviour.Properties.of()
                                .strength(3.5F).noOcclusion().sound(SoundType.STONE).requiresCorrectToolForDrops())));
            }

        }

        for (Metal metal : Metal.values()){
            String metalName = metal.getSerializedName();

            if (metalName.contains("weak") || metalName.contains("high_carbon") || metalName.contains("unknown") || metalName.contains("pig")) continue;

            CANDLE_HOLDERS.put(metal, BLOCKS.registerBlock(metalName + "_candle_holder",
                    properties -> new CandleHolderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE)
                            .strength(4F)
                            .noOcclusion()
                            .sound(SoundType.COPPER)
                            .requiresCorrectToolForDrops()
                            .lightLevel(CandleHolderBlock::getLightEmission)
                    )));
        }
    }
}