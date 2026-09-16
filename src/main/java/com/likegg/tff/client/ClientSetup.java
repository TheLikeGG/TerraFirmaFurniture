package com.likegg.tff.client;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.client.gui.DynamicInventoryScreen;
import com.likegg.tff.client.renderer.BasicShelfBlockEntityRenderer;
import com.likegg.tff.client.renderer.CandleHolderRenderer;
import com.likegg.tff.client.renderer.TableClothRenderer;
import com.likegg.tff.client.renderer.WallCabinetWithShelfRenderer;
import com.likegg.tff.registry.ModBlockEntities;
import com.likegg.tff.registry.ModEntities;
import com.likegg.tff.registry.ModMenuTypes;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = TerraFirmaFurniture.MODID)
public class ClientSetup {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SEAT.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.TABLE_CLOTH.get(), TableClothRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CANDLE_HOLDER.get(), CandleHolderRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WALL_CABINET_WITH_SHELF.get(), WallCabinetWithShelfRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BASIC_SHELF.get(), BasicShelfBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.DYNAMIC_INVENTORY_MENU.get(), DynamicInventoryScreen::new);
    }
}