package com.likegg.tff.registry;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.blocks.cabinet.WallCabinetWithShelfBlock;
import com.likegg.tff.blocks.entity.*;
import com.likegg.tff.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

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
                    BlockEntityType.Builder.of(WallCabinetSingleDoorBlockEntity::new,
                            Stream.concat(
                                    ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR.values().stream(),
                                    ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.values().stream()
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallCabinetDoubleDoorBlockEntity>> WALL_CABINET_DOUBLE_DOOR =
            BLOCK_ENTITIES.register("wall_cabinet_double_door", () ->
                    BlockEntityType.Builder.of(WallCabinetDoubleDoorBlockEntity::new,
                            Stream.concat(
                                    ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR.values().stream(),
                                    ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.values().stream()
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallCabinetWithShelfBlockEntity>> WALL_CABINET_WITH_SHELF =
            BLOCK_ENTITIES.register("wall_cabinet_with_shelf", () ->
                    BlockEntityType.Builder.of(WallCabinetWithShelfBlockEntity::new,
                            Stream.concat(
                                    ModBlocks.LOG_WALL_CABINETS_WITH_SHELF.values().stream(),
                                    ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.values().stream()
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasicShelfBlockEntity>> BASIC_SHELF =
            BLOCK_ENTITIES.register("basic_shelf", () ->
                    BlockEntityType.Builder.of(BasicShelfBlockEntity::new,
                            Stream.concat(
                                    ModBlocks.BASIC_WOODEN_SHELVES.values().stream(),
                                    Stream.concat(ModBlocks.BASIC_LOG_SHELVES.values().stream(),
                                    ModBlocks.BASIC_STRIPPED_LOG_SHELVES.values().stream())
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NightStandSingleDrawerBlockEntity>> NIGHT_STAND_SINGLE_DRAWER =
            BLOCK_ENTITIES.register("night_stand_single_drawer", () ->
                    BlockEntityType.Builder.of(NightStandSingleDrawerBlockEntity::new,
                            Stream.concat(
                                ModBlocks.NIGHT_STANDS_SINGLE_DRAWER.values().stream(),
                                ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.values().stream()
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NightStandDoubleDrawerBlockEntity>> NIGHT_STAND_DOUBLE_DRAWER =
            BLOCK_ENTITIES.register("night_stand_double_drawer", () ->
                    BlockEntityType.Builder.of(NightStandDoubleDrawerBlockEntity::new,
                            Stream.concat(
                                ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER.values().stream(),
                                ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.values().stream()
                            ).map(DeferredBlock::get).toArray(Block[]::new)
                    ).build(null));




    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}