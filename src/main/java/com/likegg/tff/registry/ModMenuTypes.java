package com.likegg.tff.registry;

import com.likegg.tff.menu.DynamicInventoryMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, "tff");

    public static final DeferredHolder<MenuType<?>, MenuType<DynamicInventoryMenu>> DYNAMIC_INVENTORY_MENU =
            MENUS.register("dynamic_inventory_menu", () -> IMenuTypeExtension.create(DynamicInventoryMenu::new));
}