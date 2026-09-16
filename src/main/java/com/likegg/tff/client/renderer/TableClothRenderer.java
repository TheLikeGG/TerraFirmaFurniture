package com.likegg.tff.client.renderer;

import com.likegg.tff.entity.TableClothEntity;
import com.likegg.tff.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TableClothRenderer extends EntityRenderer<TableClothEntity> {

    public TableClothRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TableClothEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        // Align model center with the entity
        poseStack.translate(0.0D, 0.3333D, 0.0D);
        poseStack.scale(1.001F, 1.0F, 1.001F);

        // Get the item stack corresponding to the entity's current color
        ItemStack itemStack = new ItemStack(ModItems.TABLE_CLOTHS.get(entity.getColor()).get());

        // Render the item's baked model in world space
        Minecraft.getInstance().getItemRenderer().renderStatic(
                itemStack,
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                0
        );

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(TableClothEntity entity) {
        // Unused when delegating rendering to ItemRenderer
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/atlas/blocks.png");
    }
}