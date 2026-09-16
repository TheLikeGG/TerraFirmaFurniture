package com.likegg.tff.client.gui;

import com.likegg.tff.menu.DynamicInventoryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class DynamicInventoryScreen extends AbstractContainerScreen<DynamicInventoryMenu> {
    private static final ResourceLocation SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/slot");

    public DynamicInventoryScreen(DynamicInventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        // Dynamically calculate screen size based on menu slots
        int maxX = 0;
        int maxY = 0;
        for (Slot slot : menu.slots) {
            if (slot.x > maxX) maxX = slot.x;
            if (slot.y > maxY) maxY = slot.y;
        }

        this.imageWidth = Math.max(176, maxX + 18 + 7);
        this.imageHeight = maxY + 18 + 7;

        // Position "Inventory" label right above player inventory
        this.inventoryLabelY = menu.getPlayerInvStartY() - 11;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Base vanilla GUI panel background color
        guiGraphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, 0xFFC6C6C6);

        // Vanilla GUI bevel borders
        guiGraphics.fill(x, y, x + this.imageWidth, y + 1, 0xFFFFFFFF);
        guiGraphics.fill(x, y, x + 1, y + this.imageHeight, 0xFFFFFFFF);
        guiGraphics.fill(x, y + this.imageHeight - 1, x + this.imageWidth, y + this.imageHeight, 0xFF555555);
        guiGraphics.fill(x + this.imageWidth - 1, y, x + this.imageWidth, y + this.imageHeight, 0xFF555555);

        // Blit standard vanilla slot sprite for every registered slot
        for (Slot slot : this.menu.slots) {
            guiGraphics.blitSprite(SLOT_SPRITE, x + slot.x - 1, y + slot.y - 1, 18, 18);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}