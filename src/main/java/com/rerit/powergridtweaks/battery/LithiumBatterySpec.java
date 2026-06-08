package com.rerit.powergridtweaks.battery;

import net.minecraft.util.Mth;
import org.patryk3211.powergrid.electricity.battery.BatterySpec;

public final class LithiumBatterySpec implements BatterySpec {
    public static final LithiumBatterySpec INSTANCE = new LithiumBatterySpec();

    public static final float CAPACITY = 120000.0f;
    public static final float INITIAL_CHARGE = CAPACITY * 0.5f;
    public static final float MAX_VOLTAGE = 240.0f;
    public static final float RATED_POWER = 3000.0f;
    public static final float THERMAL_MASS = 40.0f;

    private static final float MIN_VOLTAGE = 180.0f;
    private static final float BASE_RESISTANCE = 0.50f;
    private static final float DISCHARGED_RESISTANCE = 1.20f;

    private LithiumBatterySpec() {
    }

    @Override
    public float getInitialCharge() {
        return INITIAL_CHARGE;
    }

    @Override
    public float getMaxCharge() {
        return CAPACITY;
    }

    @Override
    public float calculateResistance(float charge) {
        float stateOfCharge = Mth.clamp(charge, 0.0f, 1.0f);
        return Mth.lerp(1.0f - stateOfCharge, BASE_RESISTANCE, DISCHARGED_RESISTANCE);
    }

    @Override
    public float calculateVoltage(float charge) {
        float stateOfCharge = Mth.clamp(charge, 0.0f, 1.0f);
        return Mth.lerp(stateOfCharge, MIN_VOLTAGE, MAX_VOLTAGE);
    }
}
