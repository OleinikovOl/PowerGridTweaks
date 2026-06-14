package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.registry.item.ModBlockItems;
import com.rerit.powergridtweaks.registry.item.ModCircuitItems;
import com.rerit.powergridtweaks.registry.item.ModMaterialItems;
import com.rerit.powergridtweaks.registry.item.ModSpecialItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PowerGridTweaks.MOD_ID);

    public static final DeferredItem<Item> HIGH_VOLTAGE_LIGHT_BULB =
            ModSpecialItems.HIGH_VOLTAGE_LIGHT_BULB;

    public static final DeferredItem<Item> POWER_DIODE =
            ModCircuitItems.POWER_DIODE;

    public static final DeferredItem<Item> POWER_RESISTOR =
            ModCircuitItems.POWER_RESISTOR;

    public static final DeferredItem<Item> POWER_SWITCH =
            ModCircuitItems.POWER_SWITCH;

    public static final DeferredItem<Item> POWER_REDSTONE_RELAY =
            ModCircuitItems.POWER_REDSTONE_RELAY;

    public static final DeferredItem<Item> POWER_RELAY =
            ModCircuitItems.POWER_RELAY;

    public static final DeferredItem<BlockItem> SHAFT_GENERATOR =
            ModBlockItems.SHAFT_GENERATOR;

    public static final DeferredItem<BlockItem> LITHIUM_BATTERY =
            ModBlockItems.LITHIUM_BATTERY;

    public static final DeferredItem<BlockItem> NETHER_LITHIUM_ORE =
            ModBlockItems.NETHER_LITHIUM_ORE;

    public static final DeferredItem<Item> RAW_LITHIUM =
            ModMaterialItems.RAW_LITHIUM;

    public static final DeferredItem<Item> CRUSHED_RAW_LITHIUM =
            ModMaterialItems.CRUSHED_RAW_LITHIUM;

    public static final DeferredItem<Item> LITHIUM_INGOT =
            ModMaterialItems.LITHIUM_INGOT;

    public static final DeferredItem<Item> LITHIUM_NUGGET =
            ModMaterialItems.LITHIUM_NUGGET;

    public static final DeferredItem<Item> LITHIUM_SHEET =
            ModMaterialItems.LITHIUM_SHEET;

    public static final DeferredItem<Item> GRAPHITE_SHEET =
            ModMaterialItems.GRAPHITE_SHEET;

    public static final DeferredItem<Item> LITHIUM_CELL =
            ModMaterialItems.LITHIUM_CELL;

    public static final DeferredItem<Item> INCOMPLETE_LITHIUM_CELL =
            ModMaterialItems.INCOMPLETE_LITHIUM_CELL;

    public static final DeferredItem<Item> CARBON_MASS =
            ModMaterialItems.CARBON_MASS;

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
