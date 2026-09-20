package com.likegg.tff.registry;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.compat.ModItemsAFC;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerraFirmaFurniture.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_SEATS =
            CREATIVE_MODE_TABS.register("tff_seats", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_seats"))
                    .icon(() -> new ItemStack(ModItems.WOODEN_CHAIR_ITEMS.get(Wood.MAPLE).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.WOODEN_CHAIR_ITEMS.values().forEach(item -> output.accept(item.get()));
                        ModItems.WOODEN_SHORT_STOOLS.values().forEach(item -> output.accept(item.get()));
                        ModItems.WOODEN_TALL_STOOLS.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_STOOLS.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.WOODEN_CHAIR_ITEMS,
                                ModItemsAFC.WOODEN_SHORT_STOOLS,
                                ModItemsAFC.WOODEN_TALL_STOOLS,
                                ModItemsAFC.LOG_STOOLS
                        ));
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_TABLES =
            CREATIVE_MODE_TABS.register("tff_tables", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_tables"))
                    .icon(() -> new ItemStack(ModItems.WOODEN_TABLES.get(Wood.MAPLE).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.WOODEN_TABLES.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_ROUND_TABLES.values().forEach(item -> output.accept(item.get()));
                        ModItems.TABLE_CLOTHS.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.WOODEN_TABLES,
                                ModItemsAFC.LOG_ROUND_TABLES
                        ));
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_COUNTERS =
            CREATIVE_MODE_TABS.register("tff_counters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_counters"))
                    .icon(() -> new ItemStack(ModItems.BASIC_WOODEN_COUNTERS.get(Wood.MAPLE).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.STRIPPED_BASIC_WOODEN_COUNTERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_LOG_WOODEN_COUNTERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_WOODEN_COUNTERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.BASIC_WOODEN_COUNTERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_STONE_COUNTERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_LOG_STONE_COUNTERS.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.STRIPPED_BASIC_WOODEN_COUNTERS,
                                ModItemsAFC.STRIPPED_LOG_WOODEN_COUNTERS,
                                ModItemsAFC.LOG_WOODEN_COUNTERS,
                                ModItemsAFC.BASIC_WOODEN_COUNTERS,
                                ModItemsAFC.LOG_STONE_COUNTERS,
                                ModItemsAFC.STRIPPED_LOG_STONE_COUNTERS
                        ));
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_MISC =
            CREATIVE_MODE_TABS.register("tff_misc", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_misc"))
                    .icon(() -> new ItemStack(ModItems.CANDLE_HOLDERS.get(Metal.STEEL).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.CANDLE_HOLDERS.values().forEach(item -> output.accept(item.get()));
                        ModItems.NIGHT_STANDS_SINGLE_DRAWER.values().forEach(item -> output.accept(item.get()));
                        ModItems.NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.NIGHT_STANDS_SINGLE_DRAWER,
                                ModItemsAFC.NIGHT_STANDS_DOUBLE_DRAWER,
                                ModItemsAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER,
                                ModItemsAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER
                        ));
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_CABINETS =
            CREATIVE_MODE_TABS.register("tff_cabinets", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_cabinets"))
                    .icon(() -> new ItemStack(ModItems.LOG_WALL_CABINETS_SINGLE_DOOR.get(Wood.MAPLE).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.values().forEach(item -> output.accept(item.get()));
                        ModItems.LOG_WALL_CABINETS_WITH_SHELF.values().forEach(item -> output.accept(item.get()));
                        ModItems.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.LOG_WALL_CABINETS_SINGLE_DOOR,
                                ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR,
                                ModItemsAFC.LOG_WALL_CABINETS_DOUBLE_DOOR,
                                ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR,
                                ModItemsAFC.LOG_WALL_CABINETS_WITH_SHELF,
                                ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF
                        ));
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TFF_SHELVES =
            CREATIVE_MODE_TABS.register("tff_shelves", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tff_shelves"))
                    .icon(() -> new ItemStack(ModItems.BASIC_WOODEN_SHELVES.get(Wood.MAPLE).get()))
                    .displayItems((parameters, output) -> {
                        ModItems.BASIC_WOODEN_SHELVES.values().forEach(item -> output.accept(item.get()));
                        ModItems.BASIC_LOG_SHELVES.values().forEach(item -> output.accept(item.get()));
                        ModItems.BASIC_STRIPPED_LOG_SHELVES.values().forEach(item -> output.accept(item.get()));

                        acceptAfcItems(output, () -> List.of(
                                ModItemsAFC.BASIC_WOODEN_SHELVES,
                                ModItemsAFC.BASIC_LOG_SHELVES,
                                ModItemsAFC.BASIC_STRIPPED_LOG_SHELVES
                        ));
                    })
                    .build());

    /**
     * Safely accepts AFC item maps into the tab output if AFC is loaded.
     * Uses a Supplier wrapper to delay classloading of ModItemsAFC until runtime execution.
     */
    private static void acceptAfcItems(
            CreativeModeTab.Output output,
            Supplier<List<Map<?, ? extends Supplier<Item>>>> afcMapsSupplier
    ) {
        if (ModList.get().isLoaded("afc")) {
            afcMapsSupplier.get().forEach(map ->
                    map.values().forEach(itemSupplier -> output.accept(itemSupplier.get()))
            );
        }
    }
}