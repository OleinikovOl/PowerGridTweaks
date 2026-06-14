package com.rerit.powergridtweaks.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class PowerGridTweaksConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue STRONGER_LIGHTS_ENABLED;
    public static final ModConfigSpec.IntValue STRONGER_LIGHTS_RADIUS;
    public static final ModConfigSpec.IntValue STRONGER_LIGHTS_LEVEL;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("stronger_lights");

        STRONGER_LIGHTS_ENABLED = BUILDER
                .comment("Enable extra invisible light blocks around fully powered Create: Power Grid light fixtures.")
                .define("enabled", true);

        STRONGER_LIGHTS_RADIUS = BUILDER
                .comment("Radius around the fixture where extra invisible light blocks are placed. Minecraft handles natural light falloff after that.")
                .defineInRange("radius", 5, 1, 16);

        STRONGER_LIGHTS_LEVEL = BUILDER
                .comment("Light level for extra invisible light blocks. Minecraft maximum is 15.")
                .defineInRange("light_level", 15, 1, 15);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static boolean strongerLightsEnabled() {
        return STRONGER_LIGHTS_ENABLED.get();
    }

    public static int strongerLightsRadius() {
        return STRONGER_LIGHTS_RADIUS.get();
    }

    public static int strongerLightsLevel() {
        return STRONGER_LIGHTS_LEVEL.get();
    }
}
