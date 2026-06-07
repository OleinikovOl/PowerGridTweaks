package com.rerit.powergridtweaks.registry;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ModCreativeTabs {
    public static void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.HIGH_VOLTAGE_LIGHT_BULB.get());
        }
    }
}
