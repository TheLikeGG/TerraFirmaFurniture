package com.likegg.tff;

import com.likegg.tff.datagen.*;
import com.likegg.tff.registry.ModEntities;
//import com.likegg.tff.registry.ModBlockEntities;
import com.likegg.tff.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(TerraFirmaFurniture.MODID)
public class TerraFirmaFurniture {
    public static final String MODID = "tff";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TerraFirmaFurniture(IEventBus modEventBus, ModContainer modContainer) {
        // Register Deferred Registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);


        // Register Datagen event on the mod bus
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                true,
                new ModBlockStateProvider(packOutput, MODID, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ModItemModelProvider(generator.getPackOutput(), event.getExistingFileHelper())
        );
        // Block Tags (Server Data)
        generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookupProvider, helper));

        // Loot Tables (Server Data)
        generator.addProvider(event.includeServer(), new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider
        ));

        // Language datagen
        generator.addProvider(event.includeServer(), new ModLanguageProvider(output));

        // Recipe datagen
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
    }
}