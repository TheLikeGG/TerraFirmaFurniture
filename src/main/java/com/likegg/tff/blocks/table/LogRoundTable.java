package com.likegg.tff.blocks.table;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LogRoundTable extends Block {

    // Base shapes created using coordinates from Blockbench (X1, Y1, Z1, X2, Y2, Z2)
    private static final VoxelShape TABLE_BODY = Block.box(0, 12, 0, 16, 16, 16);
    private static final VoxelShape TRUNK = Block.box(6, 3, 6, 10, 12, 10);
    private static final VoxelShape BASE = Block.box(4, 0, 4, 12, 3, 12);
    private static final VoxelShape HITBOX = Shapes.or(TABLE_BODY, TRUNK, BASE);

    public LogRoundTable(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Facing opposite of player so the chair faces towards the player on placement
        return this.defaultBlockState();
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return HITBOX;
    }
}