package com.likegg.tff.datagen;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Map;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, TerraFirmaFurniture.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
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

        //addWoodMap(ModItems.LARGE_DRAWERS, "Large Drawer");
        //addWoodMap(ModItems.MEDIUM_DRAWERS, "Medium Drawer");
        //addWoodMap(ModItems.SMALL_DRAWERS, "Small Drawer");

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
        //add("", "");
        //add("", "");
    }

    /** Helper for Wood -> Item maps */
    private void addWoodMap(Map<Wood, ? extends DeferredItem<?>> map, String suffix) {
        map.forEach((wood, item) -> {
            String woodName = cleanName(wood.getSerializedName());
            add(item.get(), woodName + " " + suffix);
        });
    }

    /** Helper for Pair<Wood, Rock> -> Item maps */
    private void addWoodRockMap(Map<Pair<Wood, Rock>, ? extends DeferredItem<?>> map, String suffix) {
        map.forEach((pair, item) -> {
            String woodName = cleanName(pair.getFirst().getSerializedName());
            String rockName = cleanName(pair.getSecond().getSerializedName());
            // e.g. "Acacia Granite Log Counter"
            add(item.get(), woodName + " " + rockName + " " + suffix);
        });
    }

    /** Converts snake_case strings to Title Case (e.g. "douglas_fir" -> "Douglas Fir") */
    private String cleanName(String name) {
        return WordUtils.capitalizeFully(name.replace("_", " "));
    }
}