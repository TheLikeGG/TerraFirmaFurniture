package com.likegg.tff.datagen;

import com.likegg.tff.blocks.counter.BasicWoodenCounterBlock;
import com.likegg.tff.blocks.table.WoodenTableBlock;
import com.likegg.tff.compat.AFC;
import com.likegg.tff.compat.ModBlocksAFC;
import com.likegg.tff.registry.ModBlocks;
import com.therighthon.afc.common.blocks.AFCWood;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class ModBlockStateProvider extends BlockStateProvider {

    // Generic texture because a lot of things use this
    ResourceLocation brassTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/metal/block/brass");

    private final ExistingFileHelper helper;

    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.helper = exFileHelper;
    }

    public void registerCounterModel(Block block, String name, ResourceLocation topTexture, ResourceLocation bodyTexture) {
        ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
        ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
        ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

        this.helper.trackGenerated(topTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
        this.helper.trackGenerated(bodyTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

        ModelFile modelStraight = models().withExistingParent("block/counter/" + name, baseStraight)
                .texture("top", topTexture).texture("body", bodyTexture).texture("particle", topTexture);

        ModelFile modelCornerOut = models().withExistingParent("block/counter/" + name + "_corner_out", baseCornerOut)
                .texture("top", topTexture).texture("body", bodyTexture).texture("particle", topTexture);

        ModelFile modelCornerIn = models().withExistingParent("block/counter/" + name + "_corner_in", baseCornerIn)
                .texture("top", topTexture).texture("body", bodyTexture).texture("particle", topTexture);

        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
            StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

            ModelFile selectedModel = modelStraight;

            // Model base facing is NORTH (Z=0).
            // Map NORTH to 0° rotation instead of facing.toYRot() which assumes SOUTH base models.
            int yRot = switch (facing) {
                case NORTH -> 0;
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 0;
            };

            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                selectedModel = modelCornerIn;
                if (shape == StairsShape.INNER_LEFT) {
                    yRot = (yRot + 270) % 360;
                }
            } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                selectedModel = modelCornerOut;
                if (shape == StairsShape.OUTER_LEFT) {
                    yRot = (yRot + 270) % 360;
                }
            }

            return ConfiguredModel.builder()
                    .modelFile(selectedModel)
                    .rotationY(yRot)
                    .uvLock(true)
                    .build();
        });
    }

    public void registerCounterVariants(Map<Wood, DeferredBlock<Block>> map, String name, String topTexture, String bodyTexture){
        // Basic counters
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("tfc", topTexture + woodName);
            ResourceLocation body = ResourceLocation.fromNamespaceAndPath("tfc", bodyTexture + woodName);

            registerCounterModel(block.get(), woodName + name, top, body);
        });
    }
    public void registerCounterVariantsAFC(Map<AFCWood, DeferredBlock<Block>> map, String name, String topTexture, String bodyTexture){
        // Basic counters
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation top = ResourceLocation.fromNamespaceAndPath("afc", topTexture + woodName);
            ResourceLocation body = ResourceLocation.fromNamespaceAndPath("afc", bodyTexture + woodName);

            registerCounterModel(block.get(), woodName + name, top, body);
        });
    }

    public void registerCabinets(Map<Wood, DeferredBlock<Block>> map, String name, String baseTexture, String bodyTexture, String modelName){
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", baseTexture + woodName);
            ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("tfc", bodyTexture + woodName);
            ResourceLocation baseModel = ResourceLocation.fromNamespaceAndPath("tff", "block/wall_cabinet/" + modelName);

            this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(strippedTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/wall_cabinet/" + woodName + name, baseModel)
                    .texture("bark", woodTexture)
                    .texture("wood", strippedTexture)
                    .texture("particle", woodTexture)
                    .texture("handle", brassTexture);

            horizontalBlock(block.get(), model);
        });
    }
    public void registerCabinetsAFC(Map<AFCWood, DeferredBlock<Block>> map, String name, String baseTexture, String bodyTexture, String modelName){
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", baseTexture + woodName);
            ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("afc", bodyTexture + woodName);
            ResourceLocation baseModel = ResourceLocation.fromNamespaceAndPath("tff", "block/wall_cabinet/" + modelName);

            this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(strippedTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/wall_cabinet/" + woodName + name, baseModel)
                    .texture("bark", woodTexture)
                    .texture("wood", strippedTexture)
                    .texture("particle", woodTexture)
                    .texture("handle", brassTexture);

            horizontalBlock(block.get(), model);
        });
    }

    public void registerShelf(Map<Wood, DeferredBlock<Block>> map, String name, String baseTexturePath, String shelfTexturePath, String logTexturePath) {
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", baseTexturePath + woodName);
            ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("tfc", shelfTexturePath + woodName);
            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", logTexturePath + woodName);

            // Parent base models for bottom and top halves
            ResourceLocation baseBottomModel = ResourceLocation.fromNamespaceAndPath("tff", "block/shelf/basic_shelf_bottom");
            ResourceLocation baseTopModel = ResourceLocation.fromNamespaceAndPath("tff", "block/shelf/basic_shelf_top");

            this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(strippedTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            // 1. Generate lower half model
            ModelFile modelBottom = models().withExistingParent("block/shelf/" + woodName + name + "_bottom", baseBottomModel)
                    .texture("wood", woodTexture)
                    .texture("shelf", strippedTexture)
                    .texture("log", logTexture)
                    .texture("particle", woodTexture);

            // 2. Generate upper half model
            ModelFile modelTop = models().withExistingParent("block/shelf/" + woodName + name + "_top", baseTopModel)
                    .texture("wood", woodTexture)
                    .texture("shelf", strippedTexture)
                    .texture("log", logTexture)
                    .texture("particle", woodTexture);

            // 3. Register blockstate variants for both HALF and FACING
            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);

                ModelFile selectedModel = (half == DoubleBlockHalf.LOWER) ? modelBottom : modelTop;

                int yRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(selectedModel)
                        .rotationY(yRot)
                        .build();
            });
        });
    }
    public void registerShelfAFC(Map<AFCWood, DeferredBlock<Block>> map, String name, String baseTexturePath, String shelfTexturePath, String logTexturePath) {
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", baseTexturePath + woodName);
            ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("afc", shelfTexturePath + woodName);
            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", logTexturePath + woodName);

            // Parent base models for bottom and top halves
            ResourceLocation baseBottomModel = ResourceLocation.fromNamespaceAndPath("tff", "block/shelf/basic_shelf_bottom");
            ResourceLocation baseTopModel = ResourceLocation.fromNamespaceAndPath("tff", "block/shelf/basic_shelf_top");

            models().existingFileHelper.trackGenerated(woodTexture, ModelProvider.TEXTURE);
            models().existingFileHelper.trackGenerated(strippedTexture, ModelProvider.TEXTURE);
            models().existingFileHelper.trackGenerated(logTexture, ModelProvider.TEXTURE);

            // 1. Generate lower half model
            ModelFile modelBottom = models().withExistingParent("block/shelf/" + woodName + name + "_bottom", baseBottomModel)
                    .texture("wood", woodTexture)
                    .texture("shelf", strippedTexture)
                    .texture("log", logTexture)
                    .texture("particle", woodTexture);

            // 2. Generate upper half model
            ModelFile modelTop = models().withExistingParent("block/shelf/" + woodName + name + "_top", baseTopModel)
                    .texture("wood", woodTexture)
                    .texture("shelf", strippedTexture)
                    .texture("log", logTexture)
                    .texture("particle", woodTexture);

            // 3. Register blockstate variants for both HALF and FACING
            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);

                ModelFile selectedModel = (half == DoubleBlockHalf.LOWER) ? modelBottom : modelTop;

                int yRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(selectedModel)
                        .rotationY(yRot)
                        .build();
            });
        });
    }

    public void registerNightStands(Map<Wood, DeferredBlock<Block>> map, String name, String baseTexturePath, String topTexturePath, String modelPath){
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath("tfc", baseTexturePath + woodName);
            ResourceLocation topTexture = ResourceLocation.fromNamespaceAndPath("tfc", topTexturePath + woodName);
            ResourceLocation baseModel = ResourceLocation.fromNamespaceAndPath("tff", "block/miscellaneous/" + modelPath);

            this.helper.trackGenerated(baseTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(topTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/miscellaneous/" + woodName + name, baseModel)
                    .texture("top", baseTexture)
                    .texture("body", topTexture)
                    .texture("handle", brassTexture)
                    .texture("particle", baseTexture);

            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);


                int yRot = switch (facing) {
                    case NORTH -> 180;
                    case EAST -> 270;
                    case SOUTH -> 0;
                    case WEST -> 90;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY(yRot)
                        .build();
            });
        });
    }
    public void registerNightStandsAFC(Map<AFCWood, DeferredBlock<Block>> map, String name, String baseTexturePath, String topTexturePath, String modelPath){
        map.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath("afc", baseTexturePath + woodName);
            ResourceLocation topTexture = ResourceLocation.fromNamespaceAndPath("afc", topTexturePath + woodName);
            ResourceLocation baseModel = ResourceLocation.fromNamespaceAndPath("tff", "block/miscellaneous/" + modelPath);

            this.helper.trackGenerated(baseTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(topTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/miscellaneous/" + woodName + name, baseModel)
                    .texture("top", baseTexture)
                    .texture("body", topTexture)
                    .texture("handle", brassTexture)
                    .texture("particle", baseTexture);

            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);


                int yRot = switch (facing) {
                    case NORTH -> 180;
                    case EAST -> 270;
                    case SOUTH -> 0;
                    case WEST -> 90;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationY(yRot)
                        .build();
            });
        });
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.WOODEN_CHAIRS.forEach((wood, chairBlock) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/chair");

            this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile chairModel = models().withExistingParent("block/seat/" + woodName + "_chair", baseChairModel)
                    .texture("legs", logTexture)
                    .texture("seat", plankTexture)
                    .texture("particle", plankTexture);

            horizontalBlock(chairBlock.get(), chairModel);
        });

        ModBlocks.WOODEN_SHORT_STOOLS.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/short_stool");

            this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/seat/" + woodName + "_short_stool", baseChairModel)
                    .texture("legs", logTexture)
                    .texture("seat", plankTexture)
                    .texture("particle", plankTexture);

            simpleBlock(block.get(), model);
        });

        ModBlocks.WOODEN_TALL_STOOLS.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/tall_stool");

            this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/seat/" + woodName + "_tall_stool", baseChairModel)
                    .texture("legs", logTexture)
                    .texture("seat", plankTexture)
                    .texture("particle", plankTexture);

            simpleBlock(block.get(), model);
        });

        ModBlocks.WOODEN_TABLES.forEach((wood, tableBlock) -> {
            String woodName = wood.getSerializedName();
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);

            ModelFile model4Legs = models().withExistingParent("block/table/" + woodName + "_table_4_leg", modLoc("block/table/table_4_leg"))
                    .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
            ModelFile model2Legs = models().withExistingParent("block/table/" + woodName + "_table_2_leg", modLoc("block/table/table_2_leg"))
                    .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
            ModelFile model1Leg  = models().withExistingParent("block/table/" + woodName + "_table_1_leg",  modLoc("block/table/table_1_leg"))
                    .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
            ModelFile model0Legs = models().withExistingParent("block/table/" + woodName + "_table_0_leg", modLoc("block/table/table_0_leg"))
                    .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);

            getVariantBuilder(tableBlock.get()).forAllStates(state -> {
                boolean n = state.getValue(WoodenTableBlock.NORTH);
                boolean e = state.getValue(WoodenTableBlock.EAST);
                boolean s = state.getValue(WoodenTableBlock.SOUTH);
                boolean w = state.getValue(WoodenTableBlock.WEST);

                int connectionCount = (n ? 1 : 0) + (e ? 1 : 0) + (s ? 1 : 0) + (w ? 1 : 0);

                if (connectionCount == 0) {
                    return ConfiguredModel.builder().modelFile(model4Legs).uvLock(true).build();
                }

                if (connectionCount == 1) {
                    int yRot = w ? 0 : (n ? 90 : (e ? 180 : 270));
                    return ConfiguredModel.builder().modelFile(model2Legs).rotationY(yRot).uvLock(true).build();
                }

                if (connectionCount == 2 && !(n && s) && !(e && w)) {
                    int yRot = (n && w) ? 0 : ((w && s) ? 270 : ((s && e) ? 180 : 90));
                    return ConfiguredModel.builder().modelFile(model1Leg).rotationY(yRot).uvLock(true).build();
                }

                return ConfiguredModel.builder().modelFile(model0Legs).uvLock(true).build();
            });
        });

        ModBlocks.LOG_STOOLS.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log_top/" + woodName);
            ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/log_stool");

            this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/seat/" + woodName + "_log_stool", baseChairModel)
                    .texture("seat", logTexture)
                    .texture("legs", plankTexture)
                    .texture("log", logTopTexture)
                    .texture("particle", logTexture);

            simpleBlock(block.get(), model);
        });

        registerCounterVariants(ModBlocks.BASIC_WOODEN_COUNTERS, "_basic_wooden_counter", "block/wood/planks/", "block/wood/log/");
        registerCounterVariants(ModBlocks.LOG_WOODEN_COUNTERS, "_log_wooden_counter", "block/wood/log/", "block/wood/stripped_log/");
        registerCounterVariants(ModBlocks.STRIPPED_BASIC_WOODEN_COUNTERS, "_stripped_basic_wooden_counter", "block/wood/planks/", "block/wood/stripped_log/");
        registerCounterVariants(ModBlocks.STRIPPED_LOG_WOODEN_COUNTERS, "_stripped_log_wooden_counter", "block/wood/stripped_log/", "block/wood/log/");

        ModBlocks.LOG_ROUND_TABLES.forEach((wood, block) -> {
            String woodName = wood.getSerializedName();

            ResourceLocation barkTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log_top/" + woodName);
            ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
            ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/stripped_log/" + woodName);
            ResourceLocation tableModel = ResourceLocation.fromNamespaceAndPath("tff", "block/table/round_table");

            this.helper.trackGenerated(barkTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(logTopTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/table/" + woodName + "_log_round_table", tableModel)
                    .texture("base", plankTexture)
                    .texture("trunk", strippedTexture)
                    .texture("body", logTopTexture)
                    .texture("edges", barkTexture)
                    .texture("particle", barkTexture);

            simpleBlock(block.get(), model);
        });

        // Log-Stone counters
        ModBlocks.LOG_STONE_COUNTERS.forEach((key, block) -> {
            String woodName = key.getFirst().getSerializedName();
            String rockName = key.getSecond().getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
            ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/rock/raw/" + rockName);
            ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
            ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
            ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

            this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter", baseStraight)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);
            ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_in", baseCornerIn)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);
            ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_out", baseCornerOut)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);


            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                ModelFile selectedModel = modelStraight;

                // Model base facing is NORTH (Z=0).
                // Map NORTH to 0° rotation instead of facing.toYRot() which assumes SOUTH base models.
                int yRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                    selectedModel = modelCornerIn;
                    if (shape == StairsShape.INNER_LEFT) {
                        yRot = (yRot + 270) % 360;
                    }
                } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                    selectedModel = modelCornerOut;
                    if (shape == StairsShape.OUTER_LEFT) {
                        yRot = (yRot + 270) % 360;
                    }
                }

                return ConfiguredModel.builder()
                        .modelFile(selectedModel)
                        .rotationY(yRot)
                        .uvLock(true)
                        .build();
            });
        });

        ModBlocks.STRIPPED_LOG_STONE_COUNTERS.forEach((key, block) -> {
            String woodName = key.getFirst().getSerializedName();
            String rockName = key.getSecond().getSerializedName();

            ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/stripped_log/" + woodName);
            ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/rock/raw/" + rockName);
            ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
            ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
            ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

            this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
            this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter", baseStraight)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);
            ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_in", baseCornerIn)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);
            ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_out", baseCornerOut)
                    .texture("top", rockTexture)
                    .texture("body", woodTexture)
                    .texture("particle", rockTexture);


            getVariantBuilder(block.get()).forAllStates(state -> {
                Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                ModelFile selectedModel = modelStraight;

                // Model base facing is NORTH (Z=0).
                // Map NORTH to 0° rotation instead of facing.toYRot() which assumes SOUTH base models.
                int yRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                    selectedModel = modelCornerIn;
                    if (shape == StairsShape.INNER_LEFT) {
                        yRot = (yRot + 270) % 360;
                    }
                } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                    selectedModel = modelCornerOut;
                    if (shape == StairsShape.OUTER_LEFT) {
                        yRot = (yRot + 270) % 360;
                    }
                }

                return ConfiguredModel.builder()
                        .modelFile(selectedModel)
                        .rotationY(yRot)
                        .uvLock(true)
                        .build();
            });
        });

        ModBlocks.CANDLE_HOLDERS.forEach((metal, block) -> {
            String metalName = metal.getSerializedName();

            ResourceLocation metalTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/metal/block/" + metalName);
            ResourceLocation baseModel = ResourceLocation.fromNamespaceAndPath("tff", "block/miscellaneous/candle_holder");

            this.helper.trackGenerated(metalTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            ModelFile model = models().withExistingParent("block/miscellaneous/" + metalName + "_candle_holder", baseModel)
                    .texture("metal", metalTexture)
                    .texture("particle", metalTexture);

            horizontalBlock(block.get(), model);
        });

        registerCabinets(ModBlocks.LOG_WALL_CABINETS_SINGLE_DOOR, "_log_wall_cabinet_single_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_single_door");
        registerCabinets(ModBlocks.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "_stripped_log_wall_cabinet_single_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_single_door");

        registerCabinets(ModBlocks.LOG_WALL_CABINETS_DOUBLE_DOOR, "_log_wall_cabinet_double_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_double_door");
        registerCabinets(ModBlocks.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "_stripped_log_wall_cabinet_double_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_double_door");

        registerCabinets(ModBlocks.LOG_WALL_CABINETS_WITH_SHELF, "_log_wall_cabinet_with_shelf", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_with_shelf");
        registerCabinets(ModBlocks.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "_stripped_log_wall_cabinet_with_shelf", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_with_shelf");

        registerShelf(ModBlocks.BASIC_WOODEN_SHELVES, "_basic_wooden_shelf", "block/wood/planks/", "block/wood/stripped_log/", "block/wood/stripped_log_top/");
        registerShelf(ModBlocks.BASIC_LOG_SHELVES, "_basic_log_shelf", "block/wood/log/", "block/wood/planks/", "block/wood/log_top/");
        registerShelf(ModBlocks.BASIC_STRIPPED_LOG_SHELVES, "_basic_stripped_log_shelf", "block/wood/stripped_log/", "block/wood/planks/", "block/wood/stripped_log_top/");

        registerNightStands(ModBlocks.NIGHT_STANDS_SINGLE_DRAWER, "_night_stand_single_drawer", "block/wood/log/", "block/wood/stripped_log/", "night_stand_single_drawer");
        registerNightStands(ModBlocks.NIGHT_STANDS_DOUBLE_DRAWER, "_night_stand_double_drawer", "block/wood/log/", "block/wood/stripped_log/" , "night_stand_double_drawer");
        registerNightStands(ModBlocks.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "_stripped_night_stand_single_drawer", "block/wood/stripped_log/", "block/wood/log/", "night_stand_single_drawer");
        registerNightStands(ModBlocks.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "_stripped_night_stand_double_drawer", "block/wood/stripped_log/", "block/wood/log/" , "night_stand_double_drawer");



        // ------------------------------
        // --- ArborFirmaCraft Compat ---
        /*
        if (AFC.isLoaded()) {
            ModBlocksAFC.WOODEN_CHAIRS.forEach((wood, chairBlock) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/chair");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile chairModel = models().withExistingParent("block/seat/" + woodName + "_chair", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                horizontalBlock(chairBlock.get(), chairModel);
            });

            ModBlocksAFC.WOODEN_SHORT_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/short_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_short_stool", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                simpleBlock(block.get(), model);
            });

            ModBlocksAFC.WOODEN_TALL_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/tall_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_tall_stool", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                simpleBlock(block.get(), model);
            });

            ModBlocksAFC.WOODEN_TABLES.forEach((wood, tableBlock) -> {
                String woodName = wood.getSerializedName();
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);

                ModelFile model4Legs = models().withExistingParent("block/table/" + woodName + "_table_4_leg", modLoc("block/table/table_4_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model2Legs = models().withExistingParent("block/table/" + woodName + "_table_2_leg", modLoc("block/table/table_2_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model1Leg  = models().withExistingParent("block/table/" + woodName + "_table_1_leg",  modLoc("block/table/table_1_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model0Legs = models().withExistingParent("block/table/" + woodName + "_table_0_leg", modLoc("block/table/table_0_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);

                getVariantBuilder(tableBlock.get()).forAllStates(state -> {
                    boolean n = state.getValue(WoodenTableBlock.NORTH);
                    boolean e = state.getValue(WoodenTableBlock.EAST);
                    boolean s = state.getValue(WoodenTableBlock.SOUTH);
                    boolean w = state.getValue(WoodenTableBlock.WEST);

                    int connectionCount = (n ? 1 : 0) + (e ? 1 : 0) + (s ? 1 : 0) + (w ? 1 : 0);

                    if (connectionCount == 0) {
                        return ConfiguredModel.builder().modelFile(model4Legs).uvLock(true).build();
                    }

                    if (connectionCount == 1) {
                        int yRot = w ? 0 : (n ? 90 : (e ? 180 : 270));
                        return ConfiguredModel.builder().modelFile(model2Legs).rotationY(yRot).uvLock(true).build();
                    }

                    if (connectionCount == 2 && !(n && s) && !(e && w)) {
                        int yRot = (n && w) ? 0 : ((w && s) ? 270 : ((s && e) ? 180 : 90));
                        return ConfiguredModel.builder().modelFile(model1Leg).rotationY(yRot).uvLock(true).build();
                    }

                    return ConfiguredModel.builder().modelFile(model0Legs).uvLock(true).build();
                });
            });

            ModBlocksAFC.LOG_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log_top/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/log_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_log_stool", baseChairModel)
                        .texture("seat", logTexture)
                        .texture("legs", plankTexture)
                        .texture("log", logTopTexture)
                        .texture("particle", logTexture);

                simpleBlock(block.get(), model);
            });

            registerCounterVariantsAFC(ModBlocksAFC.BASIC_WOODEN_COUNTERS, "_basic_wooden_counter", "block/wood/planks/", "block/wood/log/");
            registerCounterVariantsAFC(ModBlocksAFC.LOG_WOODEN_COUNTERS, "_log_wooden_counter", "block/wood/log/", "block/wood/stripped_log/");
            registerCounterVariantsAFC(ModBlocksAFC.STRIPPED_BASIC_WOODEN_COUNTERS, "_stripped_basic_wooden_counter", "block/wood/planks/", "block/wood/stripped_log/");
            registerCounterVariantsAFC(ModBlocksAFC.STRIPPED_LOG_WOODEN_COUNTERS, "_stripped_log_wooden_counter", "block/wood/stripped_log/", "block/wood/log/");

            ModBlocksAFC.LOG_ROUND_TABLES.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation barkTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log_top/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/stripped_log/" + woodName);
                ResourceLocation tableModel = ResourceLocation.fromNamespaceAndPath("tff", "block/table/round_table");

                this.helper.trackGenerated(barkTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(logTopTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/table/" + woodName + "_log_round_table", tableModel)
                        .texture("base", plankTexture)
                        .texture("trunk", strippedTexture)
                        .texture("body", logTopTexture)
                        .texture("edges", barkTexture)
                        .texture("particle", barkTexture);

                simpleBlock(block.get(), model);
            });

            // Log-Stone counters
            ModBlocksAFC.LOG_STONE_COUNTERS.forEach((key, block) -> {
                String woodName = key.getFirst().getSerializedName();
                String rockName = key.getSecond().getSerializedName();

                ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/rock/raw/" + rockName);
                ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
                ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
                ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

                this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter", baseStraight)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_in", baseCornerIn)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_out", baseCornerOut)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);


                getVariantBuilder(block.get()).forAllStates(state -> {
                    Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                    StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                    ModelFile selectedModel = modelStraight;

                    // Model base facing is NORTH (Z=0).
                    // Map NORTH to 0° rotation instead of facing.toYRot() which assumes SOUTH base models.
                    int yRot = switch (facing) {
                        case NORTH -> 0;
                        case EAST -> 90;
                        case SOUTH -> 180;
                        case WEST -> 270;
                        default -> 0;
                    };

                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                        selectedModel = modelCornerIn;
                        if (shape == StairsShape.INNER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                        selectedModel = modelCornerOut;
                        if (shape == StairsShape.OUTER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(selectedModel)
                            .rotationY(yRot)
                            .uvLock(true)
                            .build();
                });
            });

            ModBlocksAFC.STRIPPED_LOG_STONE_COUNTERS.forEach((key, block) -> {
                String woodName = key.getFirst().getSerializedName();
                String rockName = key.getSecond().getSerializedName();

                ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/stripped_log/" + woodName);
                ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/rock/raw/" + rockName);
                ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
                ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
                ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

                this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter", baseStraight)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_in", baseCornerIn)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_out", baseCornerOut)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);


                getVariantBuilder(block.get()).forAllStates(state -> {
                    Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                    StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                    ModelFile selectedModel = modelStraight;

                    // Model base facing is NORTH (Z=0).
                    // Map NORTH to 0° rotation instead of facing.toYRot() which assumes SOUTH base models.
                    int yRot = switch (facing) {
                        case NORTH -> 0;
                        case EAST -> 90;
                        case SOUTH -> 180;
                        case WEST -> 270;
                        default -> 0;
                    };

                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                        selectedModel = modelCornerIn;
                        if (shape == StairsShape.INNER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                        selectedModel = modelCornerOut;
                        if (shape == StairsShape.OUTER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(selectedModel)
                            .rotationY(yRot)
                            .uvLock(true)
                            .build();
                });
            });

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR, "_log_wall_cabinet_single_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_single_door");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "_stripped_log_wall_cabinet_single_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_single_door");

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR, "_log_wall_cabinet_double_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_double_door");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "_stripped_log_wall_cabinet_double_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_double_door");

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF, "_log_wall_cabinet_with_shelf", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_with_shelf");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "_stripped_log_wall_cabinet_with_shelf", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_with_shelf");

            registerShelfAFC(ModBlocksAFC.BASIC_WOODEN_SHELVES, "_basic_wooden_shelf", "block/wood/planks/", "block/wood/stripped_log/", "block/wood/stripped_log_top/");
            registerShelfAFC(ModBlocksAFC.BASIC_LOG_SHELVES, "_basic_log_shelf", "block/wood/log/", "block/wood/planks/", "block/wood/log_top/");
            registerShelfAFC(ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES, "_basic_stripped_log_shelf", "block/wood/stripped_log/", "block/wood/planks/", "block/wood/stripped_log_top/");

            registerNightStandsAFC(ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER, "_night_stand_single_drawer", "block/wood/log/", "block/wood/stripped_log/", "night_stand_single_drawer");
            registerNightStandsAFC(ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER, "_night_stand_double_drawer", "block/wood/log/", "block/wood/stripped_log/" , "night_stand_double_drawer");
            registerNightStandsAFC(ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "_stripped_night_stand_single_drawer", "block/wood/stripped_log/", "block/wood/log/", "night_stand_single_drawer");
            registerNightStandsAFC(ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "_stripped_night_stand_double_drawer", "block/wood/stripped_log/", "block/wood/log/" , "night_stand_double_drawer");

        }
        */
        // ------------------------------
        // --- ArborFirmaCraft Compat ---
        if (AFC.isLoaded()) {
            ModBlocksAFC.WOODEN_CHAIRS.forEach((wood, chairBlock) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/chair");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile chairModel = models().withExistingParent("block/seat/" + woodName + "_chair", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                horizontalBlock(chairBlock.get(), chairModel);
            });

            ModBlocksAFC.WOODEN_SHORT_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/short_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_short_stool", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                simpleBlock(block.get(), model);
            });

            ModBlocksAFC.WOODEN_TALL_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                // Fixed namespace from "tfc" to "afc"
                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/tall_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_tall_stool", baseChairModel)
                        .texture("legs", logTexture)
                        .texture("seat", plankTexture)
                        .texture("particle", plankTexture);

                simpleBlock(block.get(), model);
            });

            ModBlocksAFC.WOODEN_TABLES.forEach((wood, tableBlock) -> {
                String woodName = wood.getSerializedName();
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);

                // Added missing tracking calls
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model4Legs = models().withExistingParent("block/table/" + woodName + "_table_4_leg", modLoc("block/table/table_4_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model2Legs = models().withExistingParent("block/table/" + woodName + "_table_2_leg", modLoc("block/table/table_2_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model1Leg  = models().withExistingParent("block/table/" + woodName + "_table_1_leg",  modLoc("block/table/table_1_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);
                ModelFile model0Legs = models().withExistingParent("block/table/" + woodName + "_table_0_leg", modLoc("block/table/table_0_leg"))
                        .texture("body", plankTexture).texture("legs", logTexture).texture("particle", plankTexture);

                getVariantBuilder(tableBlock.get()).forAllStates(state -> {
                    boolean n = state.getValue(WoodenTableBlock.NORTH);
                    boolean e = state.getValue(WoodenTableBlock.EAST);
                    boolean s = state.getValue(WoodenTableBlock.SOUTH);
                    boolean w = state.getValue(WoodenTableBlock.WEST);

                    int connectionCount = (n ? 1 : 0) + (e ? 1 : 0) + (s ? 1 : 0) + (w ? 1 : 0);

                    if (connectionCount == 0) {
                        return ConfiguredModel.builder().modelFile(model4Legs).uvLock(true).build();
                    }

                    if (connectionCount == 1) {
                        int yRot = w ? 0 : (n ? 90 : (e ? 180 : 270));
                        return ConfiguredModel.builder().modelFile(model2Legs).rotationY(yRot).uvLock(true).build();
                    }

                    if (connectionCount == 2 && !(n && s) && !(e && w)) {
                        int yRot = (n && w) ? 0 : ((w && s) ? 270 : ((s && e) ? 180 : 90));
                        return ConfiguredModel.builder().modelFile(model1Leg).rotationY(yRot).uvLock(true).build();
                    }

                    return ConfiguredModel.builder().modelFile(model0Legs).uvLock(true).build();
                });
            });

            ModBlocksAFC.LOG_STOOLS.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log_top/" + woodName);
                ResourceLocation baseChairModel = ResourceLocation.fromNamespaceAndPath("tff", "block/seat/log_stool");

                this.helper.trackGenerated(logTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                // Added missing tracking call for logTopTexture
                this.helper.trackGenerated(logTopTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/seat/" + woodName + "_log_stool", baseChairModel)
                        .texture("seat", logTexture)
                        .texture("legs", plankTexture)
                        .texture("log", logTopTexture)
                        .texture("particle", logTexture);

                simpleBlock(block.get(), model);
            });

            registerCounterVariantsAFC(ModBlocksAFC.BASIC_WOODEN_COUNTERS, "_basic_wooden_counter", "block/wood/planks/", "block/wood/log/");
            registerCounterVariantsAFC(ModBlocksAFC.LOG_WOODEN_COUNTERS, "_log_wooden_counter", "block/wood/log/", "block/wood/stripped_log/");
            registerCounterVariantsAFC(ModBlocksAFC.STRIPPED_BASIC_WOODEN_COUNTERS, "_stripped_basic_wooden_counter", "block/wood/planks/", "block/wood/stripped_log/");
            registerCounterVariantsAFC(ModBlocksAFC.STRIPPED_LOG_WOODEN_COUNTERS, "_stripped_log_wooden_counter", "block/wood/stripped_log/", "block/wood/log/");

            ModBlocksAFC.LOG_ROUND_TABLES.forEach((wood, block) -> {
                String woodName = wood.getSerializedName();

                ResourceLocation barkTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation logTopTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log_top/" + woodName);
                ResourceLocation plankTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/planks/" + woodName);
                ResourceLocation strippedTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/stripped_log/" + woodName);
                ResourceLocation tableModel = ResourceLocation.fromNamespaceAndPath("tff", "block/table/round_table");

                this.helper.trackGenerated(barkTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(logTopTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(plankTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                // Added missing tracking call for strippedTexture
                this.helper.trackGenerated(strippedTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile model = models().withExistingParent("block/table/" + woodName + "_log_round_table", tableModel)
                        .texture("base", plankTexture)
                        .texture("trunk", strippedTexture)
                        .texture("body", logTopTexture)
                        .texture("edges", barkTexture)
                        .texture("particle", barkTexture);

                simpleBlock(block.get(), model);
            });

            // Log-Stone counters
            ModBlocksAFC.LOG_STONE_COUNTERS.forEach((key, block) -> {
                String woodName = key.getFirst().getSerializedName();
                String rockName = key.getSecond().getSerializedName();

                ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/log/" + woodName);
                ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/rock/raw/" + rockName);
                ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
                ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
                ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

                this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter", baseStraight)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_in", baseCornerIn)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_log_" + rockName + "_counter_corner_out", baseCornerOut)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);

                getVariantBuilder(block.get()).forAllStates(state -> {
                    Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                    StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                    ModelFile selectedModel = modelStraight;

                    int yRot = switch (facing) {
                        case NORTH -> 0;
                        case EAST -> 90;
                        case SOUTH -> 180;
                        case WEST -> 270;
                        default -> 0;
                    };

                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                        selectedModel = modelCornerIn;
                        if (shape == StairsShape.INNER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                        selectedModel = modelCornerOut;
                        if (shape == StairsShape.OUTER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(selectedModel)
                            .rotationY(yRot)
                            .uvLock(true)
                            .build();
                });
            });

            ModBlocksAFC.STRIPPED_LOG_STONE_COUNTERS.forEach((key, block) -> {
                String woodName = key.getFirst().getSerializedName();
                String rockName = key.getSecond().getSerializedName();

                ResourceLocation woodTexture = ResourceLocation.fromNamespaceAndPath("afc", "block/wood/stripped_log/" + woodName);
                ResourceLocation rockTexture = ResourceLocation.fromNamespaceAndPath("tfc", "block/rock/raw/" + rockName);
                ResourceLocation baseStraight = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter");
                ResourceLocation baseCornerOut = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_out");
                ResourceLocation baseCornerIn = ResourceLocation.fromNamespaceAndPath("tff", "block/counter/basic_wooden_counter_corner_in");

                this.helper.trackGenerated(woodTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
                this.helper.trackGenerated(rockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                ModelFile modelStraight = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter", baseStraight)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerIn = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_in", baseCornerIn)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);
                ModelFile modelCornerOut = models().withExistingParent("block/counter/" + woodName + "_stripped_log_" + rockName + "_counter_corner_out", baseCornerOut)
                        .texture("top", rockTexture)
                        .texture("body", woodTexture)
                        .texture("particle", rockTexture);

                getVariantBuilder(block.get()).forAllStates(state -> {
                    Direction facing = state.getValue(BasicWoodenCounterBlock.FACING);
                    StairsShape shape = state.getValue(BasicWoodenCounterBlock.SHAPE);

                    ModelFile selectedModel = modelStraight;

                    int yRot = switch (facing) {
                        case NORTH -> 0;
                        case EAST -> 90;
                        case SOUTH -> 180;
                        case WEST -> 270;
                        default -> 0;
                    };

                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                        selectedModel = modelCornerIn;
                        if (shape == StairsShape.INNER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
                        selectedModel = modelCornerOut;
                        if (shape == StairsShape.OUTER_LEFT) {
                            yRot = (yRot + 270) % 360;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(selectedModel)
                            .rotationY(yRot)
                            .uvLock(true)
                            .build();
                });
            });

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_SINGLE_DOOR, "_log_wall_cabinet_single_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_single_door");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_SINGLE_DOOR, "_stripped_log_wall_cabinet_single_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_single_door");

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_DOUBLE_DOOR, "_log_wall_cabinet_double_door", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_double_door");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_DOUBLE_DOOR, "_stripped_log_wall_cabinet_double_door", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_double_door");

            registerCabinetsAFC(ModBlocksAFC.LOG_WALL_CABINETS_WITH_SHELF, "_log_wall_cabinet_with_shelf", "block/wood/stripped_log/", "block/wood/log/", "wall_cabinet_with_shelf");
            registerCabinetsAFC(ModBlocksAFC.STRIPPED_LOG_WALL_CABINETS_WITH_SHELF, "_stripped_log_wall_cabinet_with_shelf", "block/wood/log/", "block/wood/stripped_log/", "wall_cabinet_with_shelf");

            registerShelfAFC(ModBlocksAFC.BASIC_WOODEN_SHELVES, "_basic_wooden_shelf", "block/wood/planks/", "block/wood/stripped_log/", "block/wood/stripped_log_top/");
            registerShelfAFC(ModBlocksAFC.BASIC_LOG_SHELVES, "_basic_log_shelf", "block/wood/log/", "block/wood/planks/", "block/wood/log_top/");
            registerShelfAFC(ModBlocksAFC.BASIC_STRIPPED_LOG_SHELVES, "_basic_stripped_log_shelf", "block/wood/stripped_log/", "block/wood/planks/", "block/wood/stripped_log_top/");

            registerNightStandsAFC(ModBlocksAFC.NIGHT_STANDS_SINGLE_DRAWER, "_night_stand_single_drawer", "block/wood/log/", "block/wood/stripped_log/", "night_stand_single_drawer");
            registerNightStandsAFC(ModBlocksAFC.NIGHT_STANDS_DOUBLE_DRAWER, "_night_stand_double_drawer", "block/wood/log/", "block/wood/stripped_log/" , "night_stand_double_drawer");
            registerNightStandsAFC(ModBlocksAFC.STRIPPED_NIGHT_STANDS_SINGLE_DRAWER, "_stripped_night_stand_single_drawer", "block/wood/stripped_log/", "block/wood/log/", "night_stand_single_drawer");
            registerNightStandsAFC(ModBlocksAFC.STRIPPED_NIGHT_STANDS_DOUBLE_DRAWER, "_stripped_night_stand_double_drawer", "block/wood/stripped_log/", "block/wood/log/" , "night_stand_double_drawer");
        }
    }
}