package com.rerit.powergridtweaks.registry.item;

import com.rerit.powergridtweaks.item.HighVoltageLightBulbItem;
import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class ModSpecialItems {
    public static final DeferredItem<Item> HIGH_VOLTAGE_LIGHT_BULB =
            ModItems.ITEMS.register(
                    "high_voltage_light_bulb",
                    () -> new HighVoltageLightBulbItem(new Item.Properties())
            );

    public static final List<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS =
            List.of(HIGH_VOLTAGE_LIGHT_BULB);

    private ModSpecialItems() {
    }
}
