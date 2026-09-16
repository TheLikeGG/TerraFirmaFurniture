package com.likegg.tff.client.renderer;

import com.likegg.tff.blocks.cabinet.WallCabinetBlock;
import com.likegg.tff.blocks.entity.WallCabinetWithShelfBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.render.blockentity.PlacedItemBlockEntityRenderer;
import net.dries007.tfc.common.items.JavelinItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TridentItem;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.items.ItemStackHandler;

public class WallCabinetWithShelfRenderer implements BlockEntityRenderer<WallCabinetWithShelfBlockEntity> {

    // 2x2 Grid Offsets inside cabinet space (X, Z)
    private static final float[][] SLOT_POSITIONS = {
            {0.30f, 0.35f}, // Slot 0: Back-Left
            {0.70f, 0.35f}, // Slot 1: Back-Right
            {0.30f, 0.68f}, // Slot 2: Front-Left
            {0.70f, 0.68f}  // Slot 3: Front-Right
    };

    public WallCabinetWithShelfRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(WallCabinetWithShelfBlockEntity entity, float partialTick, PoseStack pose,
                       MultiBufferSource buffers, int packedLight, int packedOverlay) {
        if (!entity.hasLevel()) return;

        ItemStackHandler shelf = entity.getShelfInventory();
        Direction facing = entity.getBlockState().getValue(WallCabinetBlock.FACING);
        Minecraft mc = Minecraft.getInstance();
        RandomSource random = RandomSource.create();

        for (int slot = 0; slot < shelf.getSlots(); slot++) {
            ItemStack stack = shelf.getStackInSlot(slot);
            if (stack.isEmpty()) continue;

            pose.pushPose();

            // 1. Orient matrix according to cabinet facing direction
            pose.translate(0.5D, 0.0D, 0.5D);
            pose.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
            pose.translate(-0.5D, 0.0D, -0.5D);

            float slotX = SLOT_POSITIONS[slot][0];
            float slotZ = SLOT_POSITIONS[slot][1];

            // 2. Check TFC's custom registered models map (Pots, Jugs, Vessels, Jars, etc.)
            PlacedItemBlockEntityRenderer.Provider custom = PlacedItemBlockEntityRenderer.MODELS.get(stack.getItem());

            if (custom != null) {
                BakedModel baked = mc.getModelManager().getModel(custom.model());
                VertexConsumer buffer = buffers.getBuffer(custom.renderType());
                ModelBlockRenderer blockRenderer = mc.getBlockRenderer().getModelRenderer();

                // Translate and scale for cabinet bounds
                pose.scale(0.95F, 0.95F, 0.95F);
                pose.translate(slotX - 0.2, 0.125, slotZ - 0.2);
                pose.translate(0, -0.0001, 0);

                baked.applyTransform(RenderHelpers.PLACED_ITEM_CONTEXT, pose, false);
                blockRenderer.tesselateWithAO(
                        entity.getLevel(),
                        baked,
                        entity.getBlockState(),
                        entity.getBlockPos(),
                        pose,
                        buffer,
                        true,
                        random,
                        packedLight,
                        packedOverlay,
                        ModelData.EMPTY,
                        RenderType.translucent()
                );
            } else {
                // 3. Standard TFC Item rendering logic
                BakedModel model = mc.getItemRenderer().getModel(stack, entity.getLevel(), null, 0);
                Item item = stack.getItem();
                boolean renderAsBlock = model.isGui3d() && !(item instanceof TridentItem || item instanceof JavelinItem || item instanceof ShieldItem);

                // Position on shelf floor with scaling
                pose.translate(slotX, renderAsBlock ? 0.18 : 0.135, slotZ);
                pose.scale(0.4f, 0.4f, 0.4f);
                pose.translate(0, slot * -0.0001f, 0); // Z-fighting offset

                if (!renderAsBlock) {
                    pose.mulPose(Axis.XP.rotationDegrees(90f));
                }

                // Apply TFC's custom transform context
                model.applyTransform(RenderHelpers.PLACED_ITEM_CONTEXT, pose, false);

                // Render item using Vanilla ItemRenderer
                mc.getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, pose, buffers, packedLight, packedOverlay, model);
            }

            pose.popPose();
        }
    }

    @Override
    public int getViewDistance() {
        return 24;
    }
}