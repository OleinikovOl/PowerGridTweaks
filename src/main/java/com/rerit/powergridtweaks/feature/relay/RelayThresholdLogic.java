package com.rerit.powergridtweaks.feature.relay;

public final class RelayThresholdLogic {
    private RelayThresholdLogic() {
    }

    public static float scaledHoldingCurrent(
            float originalHoldingCurrent,
            float triggerCurrent,
            float lowerVoltage,
            float upperVoltage
    ) {
        if (upperVoltage <= 0.0f) {
            return originalHoldingCurrent;
        }

        float clampedLowerVoltage = Math.min(lowerVoltage, upperVoltage);
        return triggerCurrent * clampedLowerVoltage / upperVoltage;
    }
}
