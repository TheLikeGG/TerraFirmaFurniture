package com.likegg.tff.registry;

import com.likegg.tff.TerraFirmaFurniture;
import com.likegg.tff.entity.SeatEntity;
import com.likegg.tff.entity.TableClothEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, TerraFirmaFurniture.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT =
            ENTITY_TYPES.register("seat", () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .sized(0.0F, 0.0F)
                    .build("seat"));

    public static final DeferredHolder<EntityType<?>, EntityType<TableClothEntity>> TABLE_CLOTH =
            ENTITY_TYPES.register("table_cloth", () ->
                    EntityType.Builder.<TableClothEntity>of(TableClothEntity::new, MobCategory.MISC)
                            .sized(1.0F, 0.05F) // Width x Height
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("table_cloth")
            );


}