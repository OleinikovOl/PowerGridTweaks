package com.rerit.powergridtweaks.feature.lights;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import com.rerit.powergridtweaks.feature.PowerGridFeature;

public class StrongerLightsFeature implements PowerGridFeature {
    @Override
    public String id() {
        return "stronger_lights";
    }

    @Override
    public boolean enabled() {
        return PowerGridTweaksConfig.strongerLightsEnabled();
    }
}
