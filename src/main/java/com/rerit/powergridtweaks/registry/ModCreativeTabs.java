package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PowerGridTweaks.MOD_ID);

    public static final Supplier<CreativeModeTab> POWERGRIDTWEAKS =
            CREATIVE_TABS.register(
                    "powergridtweaks",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.powergridtweaks"))
                            .icon(() -> new ItemStack(ModItems.LITHIUM_CELL.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.HIGH_VOLTAGE_LIGHT_BULB.get());
                                output.accept(ModItems.POWER_DIODE.get());
                                output.accept(ModItems.POWER_RESISTOR.get());
                                output.accept(ModItems.POWER_SWITCH.get());
                                output.accept(ModItems.POWER_REDSTONE_RELAY.get());
                                output.accept(ModItems.POWER_RELAY.get());
                                output.accept(ModItems.SHAFT_GENERATOR.get());
                                output.accept(ModItems.LITHIUM_BATTERY.get());
                                output.accept(ModItems.NETHER_LITHIUM_ORE.get());
                                output.accept(ModItems.RAW_LITHIUM.get());
                                output.accept(ModItems.CRUSHED_RAW_LITHIUM.get());
                                output.accept(ModItems.LITHIUM_INGOT.get());
                                output.accept(ModItems.LITHIUM_NUGGET.get());
                                output.accept(ModItems.LITHIUM_SHEET.get());
                                output.accept(ModItems.GRAPHITE_SHEET.get());
                                output.accept(ModItems.LITHIUM_CELL.get());
                                output.accept(ModItems.CARBON_MASS.get());
                            })
                            .build()
            );
}
