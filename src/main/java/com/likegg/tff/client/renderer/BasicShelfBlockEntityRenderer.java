package com.likegg.tff.client.renderer;

import com.likegg.tff.blocks.entity.BasicShelfBlockEntity;
import com.likegg.tff.blocks.shelf.BasicShelfBlock;
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
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.data.ModelData;

public class BasicShelfBlockEntityRenderer implements BlockEntityRenderer<BasicShelfBlockEntity> {
    private final ItemRenderer itemRenderer;

    public BasicShelfBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BasicShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!blockEntity.hasLevel()) return;
        BlockState state = blockEntity.getBlockState();
        if (!(state.getBlock() instanceof BasicShelfBlock)) return;

        Direction facing = state.getValue(BasicShelfBlock.FACING);
        DoubleBlockHalf half = state.getValue(BasicShelfBlock.SHELF_HALF);
        Minecraft mc = Minecraft.getInstance();
        RandomSource random = RandomSource.create();

        int slotCount = blockEntity.getInventory().getSlots();
        for (int slot = 0; slot < slotCount; slot++) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(slot);
            if (stack.isEmpty()) continue;

            poseStack.pushPose();

            // Get pose aligned with block orientation
            poseStack.translate(0.5D, 0.0D, 0.5D);
            poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
            poseStack.translate(-0.5D, 0.0D, -0.5D);

            // Check for custom models
            PlacedItemBlockEntityRenderer.Provider customModel = PlacedItemBlockEntityRenderer.MODELS.get(stack.getItem());
            if (customModel != null){
                BakedModel baked = mc.getModelManager().getModel(customModel.model());
                VertexConsumer buf = buffer.getBuffer(customModel.renderType());
                ModelBlockRenderer blockRenderer = mc.getBlockRenderer().getModelRenderer();

                // Translate and scale for shelf bounds
                poseStack.scale(0.7F, 0.7F, 0.7F);
                poseStack.translate((slot * 0.31) - ((slot < 4) ? -0.025 : 1.2), ((slot >= 4) ? (half.equals(DoubleBlockHalf.LOWER) ? 0.175 : 0) : 0.7), 0.125F);
                poseStack.translate(0, -0.0001, 0);

                baked.applyTransform(RenderHelpers.PLACED_ITEM_CONTEXT, poseStack, false);
                blockRenderer.tesselateWithAO(
                        blockEntity.getLevel(),
                        baked,
                        blockEntity.getBlockState(),
                        blockEntity.getBlockPos(),
                        poseStack,
                        buf,
                        true,
                        random,
                        packedLight,
                        packedOverlay,
                        ModelData.EMPTY,
                        RenderType.translucent()
                );
            } else {
                // Standard Item Rendering
                BakedModel model = mc.getItemRenderer().getModel(stack, blockEntity.getLevel(), null, 0);
                Item item = stack.getItem();
                boolean renderAsBlock = model.isGui3d() && !(item instanceof TridentItem || item instanceof JavelinItem || item instanceof ShieldItem);

                // 1. Calculate unscaled block position
                double xPos = 0.16D + ((slot % 4) * 0.22D);
                double yPos = (slot >= 4) ? (half == DoubleBlockHalf.LOWER ? 0.2D : 0.08D) : 0.575D;
                double zPos = 0.25;

                // 2. Translate to position
                poseStack.translate(xPos, yPos, zPos);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));

                // 3. Lay 2D flat items down after translating
                if (!renderAsBlock) {
                    poseStack.translate(0, -0.05, 0);
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                }

                // 4. Scale down item size
                poseStack.scale(0.375F, 0.375F, 0.375F);

                model.applyTransform(RenderHelpers.PLACED_ITEM_CONTEXT, poseStack, false);
                mc.getItemRenderer().render(stack, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, packedOverlay, model);
            }

            poseStack.popPose();
        }
    }
}