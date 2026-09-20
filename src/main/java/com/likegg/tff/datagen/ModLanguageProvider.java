package com.likegg.tff.datagen;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.compat.ModItemsAFC;
import com.likegg.tff.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;
import java.util.function.Supplier;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, TerraFirmaFurniture.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        buildVanillaTFCTranslations();

        // Check if AFC is loaded in the workspace environment before generating its translations
        if (ModList.get().isLoaded("afc")) {
            buildAFCTranslations();
        }
    }

    private void buildVanillaTFCTranslations() {
        // --- Single Wood Maps ---
        addWoodMap(ModItems.WOODEN_CHAIR_ITEMS, "Chair");
        addWoodMap(ModItems.WOODEN_SHORT_STOOLS, "Short Stool");
        addWoodMap(ModItems.WOODEN_TALL_STOOLS, "Tall Stool");
        addWoodMap(ModItems.WOODEN_TABLES, "Table");
        addWoodMap(ModItems.LOG_STOOLS, "Log Stool");
        addWoodMap(ModItems.LOG_ROUND_TABLES, "Log Round Table");

        addWoodMap(ModItems.BASIC_WOODEN_COUNTERS, "Basic Counter");
        addWoodMap(ModItems.LOG_WOODEN_COUNTERS, "Log Counter");
        addWoodMap(ModItems.STRIPPED_LOG_WOODEN_COUNTERS, "Stripped Log Counter");
        addWoodMap(ModItems.STRIPPED_BASIC_WOODEN_COUNTERS, "Stripped Basic Counter");

        addWoodMap(ModItems.NIGHT_STANDS_SINGLE_DRAWER, "Night Stand (Single Drawer)");
        addWoodMap(ModItems.NIGHT_STANDS_DOUBLE_DRAWER, "Night Stand (Double Drawer)");
        addWoodMap(ModItems.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "Stripped Night Stand (Single Drawer)");
        addWoodMap(ModItems.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "Stripped Night Stand (Double Drawer)");

        addWoodMap(ModItems.LOG_WALL_CABINETS_SINGLE_DOOR, "Log Wall Cabinet (Single Door)");
        addWoodMap(ModItems.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "Stripped Log Wall Cabinet (Single Door)");
        addWoodMap(ModItems.LOG_WALL_CABINETS_DOUBLE_DOOR, "Log Wall Cabinet (Double Door)");
        addWoodMap(ModItems.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "Stripped Log Wall Cabinet (Double Door)");
        addWoodMap(ModItems.LOG_WALL_CABINETS_WITH_SHELF, "Log Wall Cabinet with Shelf");
        addWoodMap(ModItems.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "Stripped Log Wall Cabinet with Shelf");

        addWoodMap(ModItems.BASIC_WOODEN_SHELVES, "Basic Wooden Shelf");
        addWoodMap(ModItems.BASIC_LOG_SHELVES, "Basic Log Shelf");
        addWoodMap(ModItems.BASIC_STRIPPED_LOG_SHELVES, "Basic Stripped Log Shelf");

        // --- Wood + Rock Counter Maps ---
        addWoodRockMap(ModItems.LOG_STONE_COUNTERS, "Log Counter");
        addWoodRockMap(ModItems.STRIPPED_LOG_STONE_COUNTERS, "Stripped Log Counter");

        // --- Table Cloths ---
        ModItems.TABLE_CLOTHS.forEach((color, item) -> {
            String colorName = cleanName(color.getName());
            add(item.get(), colorName + " Small Table Cloth");
        });

        // --- Candle Holders ---
        ModItems.CANDLE_HOLDERS.forEach((metal, item) -> {
            String metalName = cleanName(metal.getSerializedName());
            add(item.get(), metalName + " Candle Holder");
        });

        // GUI container titles, advancement titles, creative tab titles, etc.
        add("container.tff.night_stand_double_drawer", "Night Stand");
        add("container.tff.night_stand_single_drawer", "Night Stand");
        add("container.tff.wall_cabinet_double_door", "Wall Cabinet");
        add("container.tff.wall_cabinet_single_door", "Wall Cabinet");
        add("container.tff.wall_cabinet_with_shelf", "Wall Cabinet");
        add("itemGroup.tff_seats", "TFF Seats");
        add("itemGroup.tff_tables", "TFF Tables");
        add("itemGroup.tff_counters", "TFF Counters");
        add("itemGroup.tff_misc", "TFF Miscellaneous");
        add("itemGroup.tff_cabinets", "TFF Wall Cabinets");
        add("itemGroup.tff_shelves", "TFF Shelves");
    }

    private void buildAFCTranslations() {
        // --- Single AFC Wood Maps ---
        addWoodMap(ModItemsAFC.WOODEN_CHAIR_ITEMS, "Chair");
        addWoodMap(ModItemsAFC.WOODEN_SHORT_STOOLS, "Short Stool");
        addWoodMap(ModItemsAFC.WOODEN_TALL_STOOLS, "Tall Stool");
        addWoodMap(ModItemsAFC.WOODEN_TABLES, "Table");
        addWoodMap(ModItemsAFC.LOG_STOOLS, "Log Stool");
        addWoodMap(ModItemsAFC.LOG_ROUND_TABLES, "Log Round Table");

        addWoodMap(ModItemsAFC.BASIC_WOODEN_COUNTERS, "Basic Counter");
        addWoodMap(ModItemsAFC.LOG_WOODEN_COUNTERS, "Log Counter");
        addWoodMap(ModItemsAFC.STRIPPED_LOG_WOODEN_COUNTERS, "Stripped Log Counter");
        addWoodMap(ModItemsAFC.STRIPPED_BASIC_WOODEN_COUNTERS, "Stripped Basic Counter");

        addWoodMap(ModItemsAFC.NIGHT_STANDS_SINGLE_DRAWER, "Single Drawer Night Stand");
        addWoodMap(ModItemsAFC.NIGHT_STANDS_DOUBLE_DRAWER, "Double Drawer Night Stand");
        addWoodMap(ModItemsAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "Single Drawer Stripped Night Stand");
        addWoodMap(ModItemsAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "Double Drawer Stripped Night Stand");

        addWoodMap(ModItemsAFC.LOG_WALL_CABINETS_SINGLE_DOOR, "Log Wall Cabinet (Single Door)");
        addWoodMap(ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "Stripped Log Wall Cabinet (Single Door)");
        addWoodMap(ModItemsAFC.LOG_WALL_CABINETS_DOUBLE_DOOR, "Log Wall Cabinet (Double Door)");
        addWoodMap(ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "Stripped Log Wall Cabinet (Double Door)");
        addWoodMap(ModItemsAFC.LOG_WALL_CABINETS_WITH_SHELF, "Log Wall Cabinet with Shelf");
        addWoodMap(ModItemsAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "Stripped Log Wall Cabinet with Shelf");

        addWoodMap(ModItemsAFC.BASIC_WOODEN_SHELVES, "Basic Wooden Shelf");
        addWoodMap(ModItemsAFC.BASIC_LOG_SHELVES, "Basic Log Shelf");
        addWoodMap(ModItemsAFC.BASIC_STRIPPED_LOG_SHELVES, "Basic Stripped Log Shelf");

        // --- AFC Wood + Rock Counter Maps ---
        addWoodRockMap(ModItemsAFC.LOG_STONE_COUNTERS, "Log Counter");
        addWoodRockMap(ModItemsAFC.STRIPPED_LOG_STONE_COUNTERS, "Stripped Log Counter");
    }

    /** Generic Helper for Wood / AFCWood -> Item maps */
    private <K extends StringRepresentable> void addWoodMap(Map<K, ? extends Supplier<? extends ItemLike>> map, String suffix) {
        map.forEach((wood, item) -> {
            String woodName = cleanName(wood.getSerializedName());
            add(item.get().asItem(), woodName + " " + suffix);
        });
    }

    /** Generic Helper for Pair<Wood/AFCWood, Rock> -> Item maps */
    private <K1 extends StringRepresentable, K2 extends StringRepresentable> void addWoodRockMap(
            Map<Pair<K1, K2>, ? extends Supplier<? extends ItemLike>> map, String suffix) {
        map.forEach((pair, item) -> {
            String woodName = cleanName(pair.getFirst().getSerializedName());
            String rockName = cleanName(pair.getSecond().getSerializedName());
            add(item.get().asItem(), woodName + " " + rockName + " " + suffix);
        });
    }

    /** Converts snake_case strings to Title Case (e.g. "douglas_fir" -> "Douglas Fir") */
    private String cleanName(String name) {
        return WordUtils.capitalizeFully(name.replace("_", " "));
    }
}