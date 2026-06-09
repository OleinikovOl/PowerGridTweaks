package com.rerit.powergridtweaks.feature.relay;

import com.rerit.powergridtweaks.PowerGridTweaks;
import org.patryk3211.powergrid.circuits.components.RelayComponent;
import org.patryk3211.powergrid.circuits.components.properties.FloatProperty;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;

public final class RelayThresholdHelper {
    public static final FloatProperty LOWER_THRESHOLD_VOLTAGE = new FloatProperty(
            PowerGridTweaks.MOD_ID,
            "relay_lower_threshold",
            10.8f,
            1.0f,
            120.0f
    );

    private static final ThreadLocal<PlacedComponent> BAKING_COMPONENT = new ThreadLocal<>();

    private RelayThresholdHelper() {
    }

    public static void beginBake(PlacedComponent component) {
        BAKING_COMPONENT.set(component);
    }

    public static void endBake() {
        BAKING_COMPONENT.remove();
    }

    public static float holdingCurrent(float originalHoldingCurrent) {
        PlacedComponent component = BAKING_COMPONENT.get();

        if (component == null || !component.has(LOWER_THRESHOLD_VOLTAGE)) {
            return originalHoldingCurrent;
        }

        float upperVoltage = component.get(RelayComponent.THRESHOLD_VOLTAGE);
        float lowerVoltage = Math.min(component.get(LOWER_THRESHOLD_VOLTAGE), upperVoltage);

        float triggerCurrent = component.get(RelayComponent.THRESHOLD_CURRENT);
        return RelayThresholdLogic.scaledHoldingCurrent(
                originalHoldingCurrent,
                triggerCurrent,
                lowerVoltage,
                upperVoltage
        );
    }
}
