package com.likegg.tff.datagen;

import com.likegg.tff.compat.ModBlocksAFC;
import com.likegg.tff.registry.ModBlocks;
import com.likegg.tff.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    private static final TagKey<Item> CHESTS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("c:chests")
    );
    private static final TagKey<Item> STICKS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("c:rods/wooden")
    );

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        buildVanillaTFCRecipes(output);
        buildAFCRecipes(output);
    }

    private void buildVanillaTFCRecipes(RecipeOutput output) {
        ModBlocks.WOODEN_TABLES.forEach((wood, table) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, table, 2)
                    .pattern("TTT")
                    .pattern("L L")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .unlockedBy(getHasName(table), has(table))
                    .save(output);
        });

        ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TCT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TCT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.NIGHT_STANDS_SINGLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TTT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TTT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.BASIC_STRIPPED_LOG_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.BASIC_LOG_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.BASIC_WOODEN_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LCL")
                    .pattern("L L")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_WALL_CABINETS_WITH_SHELF.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LCL")
                    .pattern("L L")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("C C")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("C C")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("LCL")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("LCL")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.CANDLE_HOLDERS.forEach((metal, block) -> {
            ResourceLocation rod = ResourceLocation.fromNamespaceAndPath("tfc", "metal/rod/" + metal.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("R ")
                    .pattern(" R")
                    .define('R', BuiltInRegistries.ITEM.get(rod))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern(" R")
                    .pattern("R ")
                    .define('R', BuiltInRegistries.ITEM.get(rod))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output, ResourceLocation.fromNamespaceAndPath(block.getId().getNamespace(), block.getId().getPath() + "_alt"));
        });

        ModBlocks.LOG_STONE_COUNTERS.forEach((pair, block) -> {
            ResourceLocation rock = ResourceLocation.fromNamespaceAndPath("tfc", "rock/raw/" + pair.getSecond().getSerializedName() + "_slab");
            ResourceLocation wood = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + pair.getFirst().getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("RR")
                    .pattern("LL")
                    .define('R', BuiltInRegistries.ITEM.get(rock))
                    .define('L', BuiltInRegistries.ITEM.get(wood))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_LOG_STONE_COUNTERS.forEach((pair, block) -> {
            ResourceLocation rock = ResourceLocation.fromNamespaceAndPath("tfc", "rock/raw/" + pair.getSecond().getSerializedName() + "_slab");
            ResourceLocation wood = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + pair.getFirst().getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("RR")
                    .pattern("LL")
                    .define('R', BuiltInRegistries.ITEM.get(rock))
                    .define('L', BuiltInRegistries.ITEM.get(wood))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_ROUND_TABLES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 2)
                    .pattern("LLL")
                    .pattern(" R ")
                    .define('R', BuiltInRegistries.ITEM.get(lumber))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.BASIC_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation planks = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("PP")
                    .pattern("LL")
                    .define('P', BuiltInRegistries.ITEM.get(planks))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_BASIC_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation planks = ResourceLocation.fromNamespaceAndPath("tfc", "wood/planks/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("PP")
                    .pattern("LL")
                    .define('P', BuiltInRegistries.ITEM.get(planks))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_STOOLS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 2)
                    .pattern("LL")
                    .pattern("RR")
                    .define('R', STICKS)
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.STRIPPED_LOG_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LL")
                    .pattern("TT")
                    .define('T', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.LOG_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("tfc", "wood/log/" + wood.getSerializedName());
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("tfc", "wood/stripped_log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LL")
                    .pattern("TT")
                    .define('T', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.WOODEN_TALL_STOOLS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TT")
                    .pattern("LL")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.WOODEN_SHORT_STOOLS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TT")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModBlocks.WOODEN_CHAIRS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("tfc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("T ")
                    .pattern("TT")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });

        ModItems.TABLE_CLOTHS.forEach((color, block) -> {
            ResourceLocation carpet = ResourceLocation.fromNamespaceAndPath("minecraft", color.getSerializedName() + "_carpet");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("CC")
                    .define('C', BuiltInRegistries.ITEM.get(carpet))
                    .unlockedBy(getHasName(block), has(block))
                    .save(output);
        });
    }

    private void buildAFCRecipes(RecipeOutput output) {
        // Output conditional on AFC being loaded
        RecipeOutput afcOutput = output.withConditions(new ModLoadedCondition("afc"));

        ModBlocksAFC.WOODEN_TABLES.forEach((wood, blockSupplier) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, blockSupplier.get(), 2)
                    .pattern("TTT")
                    .pattern("L L")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .unlockedBy(getHasName(blockSupplier.get()), has(blockSupplier.get()))
                    .save(afcOutput);
        });


        ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TCT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TCT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TTT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER.forEach((wood, block) -> {
            ResourceLocation leg = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName() + "_slab");

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TTT")
                    .pattern("LCL")
                    .define('L', BuiltInRegistries.ITEM.get(leg))
                    .define('T', BuiltInRegistries.ITEM.get(top))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.BASIC_LOG_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.BASIC_WOODEN_SHELVES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LBL")
                    .pattern("LBL")
                    .pattern("LBL")
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('B', BuiltInRegistries.ITEM.get(lumber))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LCL")
                    .pattern("L L")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LCL")
                    .pattern("L L")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("C C")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("C C")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("LCL")
                    .pattern("LGL")
                    .define('L', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('G', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR.forEach((wood, block) -> {
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LGL")
                    .pattern("LCL")
                    .pattern("LGL")
                    .define('G', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .define('C', CHESTS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_STONE_COUNTERS.forEach((pair, block) -> {
            ResourceLocation rock = ResourceLocation.fromNamespaceAndPath("tfc", "rock/raw/" + pair.getSecond().getSerializedName() + "_slab");
            ResourceLocation wood = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + pair.getFirst().getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("RR")
                    .pattern("LL")
                    .define('R', BuiltInRegistries.ITEM.get(rock))
                    .define('L', BuiltInRegistries.ITEM.get(wood))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_LOG_STONE_COUNTERS.forEach((pair, block) -> {
            ResourceLocation rock = ResourceLocation.fromNamespaceAndPath("tfc", "rock/raw/" + pair.getSecond().getSerializedName() + "_slab");
            ResourceLocation wood = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + pair.getFirst().getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("RR")
                    .pattern("LL")
                    .define('R', BuiltInRegistries.ITEM.get(rock))
                    .define('L', BuiltInRegistries.ITEM.get(wood))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_ROUND_TABLES.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation lumber = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 2)
                    .pattern("LLL")
                    .pattern(" R ")
                    .define('R', BuiltInRegistries.ITEM.get(lumber))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.BASIC_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation planks = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("PP")
                    .pattern("LL")
                    .define('P', BuiltInRegistries.ITEM.get(planks))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_BASIC_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation planks = ResourceLocation.fromNamespaceAndPath("afc", "wood/planks/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("PP")
                    .pattern("LL")
                    .define('P', BuiltInRegistries.ITEM.get(planks))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_STOOLS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 2)
                    .pattern("LL")
                    .pattern("RR")
                    .define('R', STICKS)
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.STRIPPED_LOG_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LL")
                    .pattern("TT")
                    .define('T', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.LOG_WOODEN_COUNTERS.forEach((wood, block) -> {
            ResourceLocation log = ResourceLocation.fromNamespaceAndPath("afc", "wood/log/" + wood.getSerializedName());
            ResourceLocation strippedLog = ResourceLocation.fromNamespaceAndPath("afc", "wood/stripped_log/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("LL")
                    .pattern("TT")
                    .define('T', BuiltInRegistries.ITEM.get(strippedLog))
                    .define('L', BuiltInRegistries.ITEM.get(log))
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.WOODEN_TALL_STOOLS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TT")
                    .pattern("LL")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.WOODEN_SHORT_STOOLS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("TT")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });

        ModBlocksAFC.WOODEN_CHAIRS.forEach((wood, block) -> {
            ResourceLocation seat = ResourceLocation.fromNamespaceAndPath("afc", "wood/lumber/" + wood.getSerializedName());

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block, 1)
                    .pattern("T ")
                    .pattern("TT")
                    .pattern("LL")
                    .define('T', BuiltInRegistries.ITEM.get(seat))
                    .define('L', STICKS)
                    .unlockedBy(getHasName(block), has(block))
                    .save(afcOutput);
        });
    }
}