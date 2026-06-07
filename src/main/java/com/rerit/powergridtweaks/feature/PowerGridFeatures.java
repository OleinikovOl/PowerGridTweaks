package com.rerit.powergridtweaks.feature;

import com.rerit.powergridtweaks.feature.lights.StrongerLightsFeature;

import java.util.List;

public class PowerGridFeatures {
    private static final List<PowerGridFeature> FEATURES = List.of(
            new StrongerLightsFeature()
    );

    public static void init() {
        for (PowerGridFeature feature : FEATURES) {
            if (feature.enabled()) {
                feature.init();
            }
        }
    }
}
