package com.likegg.tff.registry;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.blocks.entity.*;
import com.likegg.tff.compat.ModBlocksAFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TerraFirmaFurniture.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CandleHolderBlockEntity>> CANDLE_HOLDER =
            BLOCK_ENTITIES.register("candle_holder",
                    () -> BlockEntityType.Builder.of(
                            CandleHolderBlockEntity::new,
                            ModBlocks.CANDLE_HOLDERS.values().stream()
                                    .map(DeferredHolder::get)
                                    .toArray(Block[]::new)
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallCabinetSingleDoorBlockEntity>> WALL_CABINET_SINGLE_DOOR =
            BLOCK_ENTITIES.register("wall_cabinet_single_door", () ->
                    BlockEntityType.Builder.of(
                            WallCabinetSingleDoorBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR,
                                            ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR,
                                            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR
                                    )
                            )
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallCabinetDoubleDoorBlockEntity>> WALL_CABINET_DOUBLE_DOOR =
            BLOCK_ENTITIES.register("wall_cabinet_double_door", () ->
                    BlockEntityType.Builder.of(
                            WallCabinetDoubleDoorBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR,
                                            ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR,
                                            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR
                                    )
                            )
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallCabinetWithShelfBlockEntity>> WALL_CABINET_WITH_SHELF =
            BLOCK_ENTITIES.register("wall_cabinet_with_shelf", () ->
                    BlockEntityType.Builder.of(
                            WallCabinetWithShelfBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.LOG_WALL_CABINETS_WITH_SHELF,
                                            ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF,
                                            ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF
                                    )
                            )
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasicShelfBlockEntity>> BASIC_SHELF =
            BLOCK_ENTITIES.register("basic_shelf", () ->
                    BlockEntityType.Builder.of(
                            BasicShelfBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.BASIC_WOODEN_SHELVES,
                                            ModBlocks.BASIC_LOG_SHELVES,
                                            ModBlocks.BASIC_STRIPPED_LOG_SHELVES
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.BASIC_WOODEN_SHELVES,
                                            ModBlocksAFC.BASIC_LOG_SHELVES,
                                            ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES
                                    )
                            )
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NightStandSingleDrawerBlockEntity>> NIGHT_STAND_SINGLE_DRAWER =
            BLOCK_ENTITIES.register("night_stand_single_drawer", () ->
                    BlockEntityType.Builder.of(
                            NightStandSingleDrawerBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.NIGHT_STANDS_SINGLE_DRAWER,
                                            ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER,
                                            ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER
                                    )
                            )
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NightStandDoubleDrawerBlockEntity>> NIGHT_STAND_DOUBLE_DRAWER =
            BLOCK_ENTITIES.register("night_stand_double_drawer", () ->
                    BlockEntityType.Builder.of(
                            NightStandDoubleDrawerBlockEntity::new,
                            getBlocks(
                                    List.of(
                                            ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER,
                                            ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER
                                    ),
                                    () -> List.of(
                                            ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER,
                                            ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER
                                    )
                            )
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    /**
     * Combines base blocks with AFC blocks conditionally if AFC is loaded.
     * Uses a Supplier wrapper around AFC maps to prevent classloading issues when AFC is missing.
     */
    private static Block[] getBlocks(
            List<Map<?, ? extends Supplier<Block>>> baseMaps,
            Supplier<List<Map<?, ? extends Supplier<Block>>>> afcMapsSupplier
    ) {
        Stream<Map<?, ? extends Supplier<Block>>> mapStream = baseMaps.stream();

        if (ModList.get().isLoaded("afc")) {
            mapStream = Stream.concat(mapStream, afcMapsSupplier.get().stream());
        }

        return mapStream
                .flatMap(map -> map.values().stream())
                .map(Supplier::get)
                .toArray(Block[]::new);
    }
}