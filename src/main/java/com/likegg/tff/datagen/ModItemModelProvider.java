package com.likegg.tff.datagen;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.items.TableClothItem;
import com.likegg.tff.registry.ModBlocks;
import com.likegg.tff.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.dries007.tfc.common.blocks.rock.Rock;

import java.util.Map;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TerraFirmaFurniture.MODID, existingFileHelper);
    }

    private void registerWoodenBlockItemModels(Map<Wood, DeferredBlock<Block>> blockMap, String subfolder) {
        blockMap.forEach((wood, block) -> {
            String blockPath = block.getId().getPath();

            withExistingParent(blockPath, modLoc("block/" + subfolder + "/" + blockPath));
        });
    }

    private void registerWoodenBlockItemModelsWithPath(Map<Wood, DeferredBlock<Block>> blockMap, String subfolder, String suffix) {
        blockMap.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            withExistingParent(block.getId().getPath(), modLoc("block/" + subfolder + "/" + woodName + suffix));
        });

    }

    private void registerMetalItemModels(Map<Metal, DeferredItem<Item>> blockMap, String subfolder, String suffix){
        blockMap.forEach((metal, block) ->{
            String metalName = metal.getSerializedName();
            withExistingParent(block.getId().getPath(), modLoc("block/" + subfolder + "/" + metalName + suffix));
        });
    }

    private void registerTableClothModels(Map<DyeColor, DeferredItem<TableClothItem>> itemMap){
        itemMap.forEach((colour, item) ->{
            ResourceLocation woolTexture = ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + colour.getSerializedName() + "_wool");

            existingFileHelper.trackGenerated(woolTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            String colourName = colour.getSerializedName();
            withExistingParent(item.getId().getPath(), modLoc("block/table/small_table_cloth"))
                    .texture("cloth", woolTexture)
                    .texture("particle", woolTexture);
        });
    }

    private void registerWoodRockCounterBlockItemModels(Map<Pair<Wood, Rock>, DeferredBlock<Block>> blockMap, String infix) {
        blockMap.forEach((pair, block) -> {
            String woodName = pair.getFirst().getSerializedName();
            String rockName = pair.getSecond().getSerializedName();
            withExistingParent(block.getId().getPath(), modLoc("block/counter/" + woodName + infix + rockName + "_counter"));
        });
    }

    private void registerDrawerItemModels(Map<Wood, DeferredItem<Item>> drawerMap, String baseModelName) {
        drawerMap.forEach((wood, item) -> {
            String woodName = wood.getSerializedName();
            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation boxTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation handleTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/metal/block/brass");

            withExistingParent(item.getId().getPath(), modLoc("block/drawer/" + baseModelName))
                    .texture("drawer", woodTexture)
                    .texture("handle", handleTexture)
                    .texture("box", boxTexture)
                    .texture("particle", woodTexture);
        });
    }

    private void registerShelfItemModels(Map<Wood, DeferredItem<Item>> map, String name, String woodTex, String shelfTex, String logTex){
        map.forEach((wood, item) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", woodTex + woodName);
            ResourceLocation shelfTexture = ResourceLocation.fromNamespaceAndPath("tfc", shelfTex + woodName);
            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", logTex + woodName);

            withExistingParent(item.getId().getPath(), modLoc("item/shelf/basic_shelf"))
                    .texture("wood", woodTexture)
                    .texture("shelf", shelfTexture)
                    .texture("log", logTexture)
                    .texture("particle", woodTexture);

        });
    }

    @Override
    protected void registerModels() {
        // Seats and Tables
        registerWoodenBlockItemModels(ModBlocks.WOODEN_CHAIRS, "seat");
        registerWoodenBlockItemModels(ModBlocks.WOODEN_SHORT_STOOLS, "seat");
        registerWoodenBlockItemModels(ModBlocks.WOODEN_TALL_STOOLS, "seat");
        registerWoodenBlockItemModels(ModBlocks.LOG_STOOLS, "seat");
        registerWoodenBlockItemModelsWithPath(ModBlocks.WOODEN_TABLES, "table", "_table_4_leg");
        registerWoodenBlockItemModels(ModBlocks.LOG_ROUND_TABLES, "table");

        // Counters
        registerWoodenBlockItemModels(ModBlocks.BASIC_WOODEN_COUNTERS, "counter");
        registerWoodenBlockItemModels(ModBlocks.LOG_WOODEN_COUNTERS, "counter");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_LOG_WOODEN_COUNTERS, "counter");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_BASIC_WOODEN_COUNTERS, "counter");
        registerWoodRockCounterBlockItemModels(ModBlocks.LOG_STONE_COUNTERS, "_log_");
        registerWoodRockCounterBlockItemModels(ModBlocks.STRIPPED_LOG_STONE_COUNTERS, "_stripped_log_");

        // Drawers
        //registerDrawerItemModels(ModItems.SMALL_DRAWERS, "small_drawer");
        //registerDrawerItemModels(ModItems.MEDIUM_DRAWERS, "medium_drawer");
        //registerDrawerItemModels(ModItems.LARGE_DRAWERS, "large_drawer");

        // Table cloths
        registerTableClothModels(ModItems.TABLE_CLOTHS);

        // Miscellaneous
        registerMetalItemModels(ModItems.CANDLE_HOLDERS, "miscellaneous", "_candle_holder");
        registerWoodenBlockItemModels(ModBlocks.NIGHT_STANDS_SINGLE_DRAWER, "miscellaneous");
        registerWoodenBlockItemModels(ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER, "miscellaneous");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "miscellaneous");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "miscellaneous");

        // Wall Cabinets
        registerWoodenBlockItemModels(ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR, "wall_cabinet");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "wall_cabinet");
        registerWoodenBlockItemModels(ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR, "wall_cabinet");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "wall_cabinet");
        registerWoodenBlockItemModels(ModBlocks.LOG_WALL_CABINETS_WITH_SHELF, "wall_cabinet");
        registerWoodenBlockItemModels(ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "wall_cabinet");

        // Shelves
        registerShelfItemModels(ModItems.BASIC_WOODEN_SHELVES, "shelf", "block/wood/planks/", "block/wood/stripped_log/", "block/wood/stripped_log_top/");
        registerShelfItemModels(ModItems.BASIC_LOG_SHELVES, "shelf", "block/wood/log/", "block/wood/planks/", "block/wood/log_top/");
        registerShelfItemModels(ModItems.BASIC_STRIPPED_LOG_SHELVES, "shelf", "block/wood/stripped_log/", "block/wood/planks/", "block/wood/stripped_log_top/");

    }
}