package com.likegg.tff.client.renderer;

import com.likegg.tff.blocks.entity.CandleHolderBlockEntity;
import com.likegg.tff.blocks.miscellaneous.CandleHolderBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CandleHolderRenderer implements BlockEntityRenderer<CandleHolderBlockEntity> {

    public CandleHolderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CandleHolderBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack candleStack = entity.getHeldCandle();
        if (candleStack.isEmpty()) return;

        BlockState holderState = entity.getBlockState();
        if (!holderState.hasProperty(CandleHolderBlock.FACING)) return;

        Direction facing = holderState.getValue(CandleHolderBlock.FACING);
        boolean isLit = holderState.hasProperty(CandleHolderBlock.LIT) && holderState.getValue(CandleHolderBlock.LIT);

        if (candleStack.getItem() instanceof BlockItem blockItem) {
            BlockState candleState = blockItem.getBlock().defaultBlockState();
            if (candleState.hasProperty(BlockStateProperties.LIT)) {
                candleState = candleState.setValue(BlockStateProperties.LIT, isLit);
            }

            poseStack.pushPose();

            switch (facing) {
                case SOUTH -> poseStack.translate(0.0, 0.5, -0.25);
                case EAST  -> poseStack.translate(-0.25, 0.5, 0.0);
                case WEST  -> poseStack.translate(0.25, 0.5, 0.0);
                default    -> poseStack.translate(0.0, 0.5, 0.25); // NORTH
            }

            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                    candleState,
                    poseStack,
                    bufferSource,
                    packedLight,
                    packedOverlay
            );

            poseStack.popPose();
        }
    }
}