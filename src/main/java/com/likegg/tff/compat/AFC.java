package com.likegg.tff.compat;

import net.neoforged.fml.ModList;

public class AFC {
    public static boolean isLoaded(){
        return ModList.get().isLoaded("afc");
    }

    public static void registerBlocks(){
        ModBlocksAFC.registerBlocks();
    }
}
