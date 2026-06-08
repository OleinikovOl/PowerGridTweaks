package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.circuit.PowerDiodeComponent;
import com.rerit.powergridtweaks.circuit.PowerRedstoneRelayComponent;
import com.rerit.powergridtweaks.circuit.PowerRelayComponent;
import com.rerit.powergridtweaks.circuit.PowerResistorComponent;
import com.rerit.powergridtweaks.circuit.PowerSwitchComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.patryk3211.powergrid.circuits.components.ComponentRegistry;

public class ModCircuitComponents {
    public static final ResourceLocation POWER_DIODE =
            ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "power_diode");
    public static final ResourceLocation POWER_RESISTOR =
            ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "power_resistor");
    public static final ResourceLocation POWER_SWITCH =
            ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "power_switch");
    public static final ResourceLocation POWER_REDSTONE_RELAY =
            ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "power_redstone_relay");
    public static final ResourceLocation POWER_RELAY =
            ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "power_relay");

    public static void registerComponents(RegisterEvent event) {
        event.register(ComponentRegistry.REGISTRY_KEY, registry -> {
            registry.register(POWER_DIODE, new PowerDiodeComponent());
            registry.register(POWER_RESISTOR, new PowerResistorComponent());
            registry.register(POWER_SWITCH, new PowerSwitchComponent());
            registry.register(POWER_REDSTONE_RELAY, new PowerRedstoneRelayComponent());
            registry.register(POWER_RELAY, new PowerRelayComponent());
        });
    }
}
