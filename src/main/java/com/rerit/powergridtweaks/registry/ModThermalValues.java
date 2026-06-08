package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.battery.LithiumBatterySpec;
import net.minecraft.world.level.block.Block;
import org.patryk3211.powergrid.config.ThermalValues;

import java.util.function.DoubleSupplier;

public class ModThermalValues {
    public static void register() {
        ThermalValues.register(new ThermalValues.Provider() {
            @Override
            public DoubleSupplier getPower(Block block) {
                return block == ModBlocks.LITHIUM_BATTERY.get()
                        ? () -> LithiumBatterySpec.RATED_POWER
                        : null;
            }

            @Override
            public DoubleSupplier getMass(Block block) {
                return block == ModBlocks.LITHIUM_BATTERY.get()
                        ? () -> LithiumBatterySpec.THERMAL_MASS
                        : null;
            }
        });
    }
}
