package com.likegg.tff.blocks.cabinet;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class WallCabinetBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<WallCabinetBlock> CODEC = simpleCodec(WallCabinetBlock::new);

    @Override
    public MapCodec<WallCabinetBlock> codec() {
        return CODEC;
    }

    public WallCabinetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // Set default facing state
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Sets facing direction opposite of the player when placed
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // Register the FACING property to the block state definition
        builder.add(FACING);
    }
}