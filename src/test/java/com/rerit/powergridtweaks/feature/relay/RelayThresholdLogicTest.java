package com.rerit.powergridtweaks.feature.relay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelayThresholdLogicTest {
    @Test
    void scalesHoldingCurrentByLowerThresholdRatio() {
        float result = RelayThresholdLogic.scaledHoldingCurrent(1.0f, 2.0f, 30.0f, 120.0f);

        assertEquals(0.5f, result, 0.0001f);
    }

    @Test
    void clampsLowerThresholdToUpperThreshold() {
        float result = RelayThresholdLogic.scaledHoldingCurrent(1.0f, 2.0f, 240.0f, 120.0f);

        assertEquals(2.0f, result, 0.0001f);
    }

    @Test
    void keepsOriginalHoldingCurrentWhenUpperThresholdIsInvalid() {
        float result = RelayThresholdLogic.scaledHoldingCurrent(1.25f, 2.0f, 30.0f, 0.0f);

        assertEquals(1.25f, result, 0.0001f);
    }
}
