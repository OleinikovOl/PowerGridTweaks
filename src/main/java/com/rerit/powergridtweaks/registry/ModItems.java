package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.item.HighVoltageLightBulbItem;
import com.rerit.powergridtweaks.item.MinerHelmetItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PowerGridTweaks.MOD_ID);

    public static final DeferredItem<Item> HIGH_VOLTAGE_LIGHT_BULB =
            ITEMS.register("high_voltage_light_bulb",
                    () -> new HighVoltageLightBulbItem(new Item.Properties()));

    public static final DeferredItem<Item> POWER_DIODE =
            ITEMS.registerSimpleItem("power_diode", new Item.Properties());

    public static final DeferredItem<Item> POWER_RESISTOR =
            ITEMS.registerSimpleItem("power_resistor", new Item.Properties());

    public static final DeferredItem<Item> POWER_SWITCH =
            ITEMS.registerSimpleItem("power_switch", new Item.Properties());

    public static final DeferredItem<Item> POWER_REDSTONE_RELAY =
            ITEMS.registerSimpleItem("power_redstone_relay", new Item.Properties());

    public static final DeferredItem<Item> POWER_RELAY =
            ITEMS.registerSimpleItem("power_relay", new Item.Properties());

    public static final DeferredItem<BlockItem> SHAFT_GENERATOR =
            ITEMS.registerSimpleBlockItem(ModBlocks.SHAFT_GENERATOR, new Item.Properties());

    public static final DeferredItem<BlockItem> LITHIUM_BATTERY =
            ITEMS.registerSimpleBlockItem(ModBlocks.LITHIUM_BATTERY, new Item.Properties());

    public static final DeferredItem<BlockItem> NETHER_LITHIUM_ORE =
            ITEMS.registerSimpleBlockItem(ModBlocks.NETHER_LITHIUM_ORE, new Item.Properties());

    public static final DeferredItem<Item> RAW_LITHIUM =
            ITEMS.registerSimpleItem("raw_lithium", new Item.Properties());

    public static final DeferredItem<Item> CRUSHED_RAW_LITHIUM =
            ITEMS.registerSimpleItem("crushed_raw_lithium", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_INGOT =
            ITEMS.registerSimpleItem("lithium_ingot", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_NUGGET =
            ITEMS.registerSimpleItem("lithium_nugget", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_SHEET =
            ITEMS.registerSimpleItem("lithium_sheet", new Item.Properties());

    public static final DeferredItem<Item> GRAPHITE_SHEET =
            ITEMS.registerSimpleItem("graphite_sheet", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_CELL =
            ITEMS.registerSimpleItem("lithium_cell", new Item.Properties());

    public static final DeferredItem<Item> INCOMPLETE_LITHIUM_CELL =
            ITEMS.registerSimpleItem("incomplete_lithium_cell", new Item.Properties());

    public static final DeferredItem<Item> CARBON_MASS =
            ITEMS.registerSimpleItem("carbon_mass", new Item.Properties());

    public static final DeferredItem<MinerHelmetItem> MINER_HELMET =
            ITEMS.register("miner_helmet",
                    () -> new MinerHelmetItem(ModArmorMaterials.MINER_HELMET, new Item.Properties().stacksTo(1)));
}
