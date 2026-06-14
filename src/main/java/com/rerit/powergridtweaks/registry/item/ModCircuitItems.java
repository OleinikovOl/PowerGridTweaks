package com.rerit.powergridtweaks.registry.item;

import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class ModCircuitItems {
    public static final DeferredItem<Item> POWER_DIODE =
            ModItems.ITEMS.registerSimpleItem("power_diode", new Item.Properties());

    public static final DeferredItem<Item> POWER_RESISTOR =
            ModItems.ITEMS.registerSimpleItem("power_resistor", new Item.Properties());

    public static final DeferredItem<Item> POWER_SWITCH =
            ModItems.ITEMS.registerSimpleItem("power_switch", new Item.Properties());

    public static final DeferredItem<Item> POWER_REDSTONE_RELAY =
            ModItems.ITEMS.registerSimpleItem("power_redstone_relay", new Item.Properties());

    public static final DeferredItem<Item> POWER_RELAY =
            ModItems.ITEMS.registerSimpleItem("power_relay", new Item.Properties());

    public static final List<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS =
            List.of(
                    POWER_DIODE,
                    POWER_RESISTOR,
                    POWER_SWITCH,
                    POWER_REDSTONE_RELAY,
                    POWER_RELAY
            );

    private ModCircuitItems() {
    }
}
