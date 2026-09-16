package com.likegg.tff.event;

import com.likegg.tff.blocks.entity.CandleHolderBlockEntity;
import com.likegg.tff.blocks.miscellaneous.CandleHolderBlock;
import net.dries007.tfc.util.events.StartFireEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
// If on Forge instead of NeoForge, use:
// import net.minecraftforge.eventbus.api.SubscribeEvent;
// import net.minecraftforge.fml.common.Mod;

@EventBusSubscriber(modid = "tff") // Replace "tff" with your MOD_ID
public class TFCEvents {

    @SubscribeEvent
    public static void onStartFire(StartFireEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Check if the targeted block is our Candle Holder
        if (state.getBlock() instanceof CandleHolderBlock) {
            boolean hasCandle = state.getValue(CandleHolderBlock.CANDLE);
            boolean isLit = state.getValue(CandleHolderBlock.LIT);
            boolean isWaterlogged = state.getValue(CandleHolderBlock.WATERLOGGED);

            // Only light if there is a candle, it's unlit, and not underwater
            if (hasCandle && !isLit && !isWaterlogged) {
                if (!level.isClientSide()) {
                    // Update blockstate to LIT
                    level.setBlock(pos, state.setValue(CandleHolderBlock.LIT, true), 3);
                    if (level.getBlockEntity(pos) instanceof CandleHolderBlockEntity){
                        ((CandleHolderBlockEntity) level.getBlockEntity(pos)).resetCounter();
                    }
                }

                // Tell TFC that the fire attempt was successful so it consumes durability/items
                event.setCanceled(true);
            }
        }
    }
}