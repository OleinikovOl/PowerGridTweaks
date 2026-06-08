package com.rerit.powergridtweaks.registry;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ModCreativeTabs {
    public static void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.HIGH_VOLTAGE_LIGHT_BULB.get());
            event.accept(ModItems.SHAFT_GENERATOR.get());
            event.accept(ModItems.LITHIUM_BATTERY.get());
            event.accept(ModItems.NETHER_LITHIUM_ORE.get());
            event.accept(ModItems.RAW_LITHIUM.get());
            event.accept(ModItems.CRUSHED_RAW_LITHIUM.get());
            event.accept(ModItems.LITHIUM_INGOT.get());
            event.accept(ModItems.LITHIUM_NUGGET.get());
            event.accept(ModItems.LITHIUM_SHEET.get());
            event.accept(ModItems.LITHIUM_CELL.get());
        }
    }
}
