package com.likegg.tff.items;

import com.likegg.tff.entity.TableClothEntity;
import com.likegg.tff.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TableClothItem extends Item {

    private final DyeColor color;

    public TableClothItem(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction face = context.getClickedFace();

        if (face != Direction.UP) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            double x = clickedPos.getX() + 0.5;
            double y = clickedPos.getY() + 1.0;
            double z = clickedPos.getZ() + 0.5;

            TableClothEntity entity = new TableClothEntity(ModEntities.TABLE_CLOTH.get(), level);
            entity.setPos(x, y, z);
            entity.setYRot(context.getRotation());

            // Set the entity's color to match this item
            entity.setColor(this.color);

            level.addFreshEntity(entity);
            level.playSound(null, clickedPos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}