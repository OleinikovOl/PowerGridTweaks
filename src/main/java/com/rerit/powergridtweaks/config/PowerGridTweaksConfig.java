package com.rerit.powergridtweaks.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class PowerGridTweaksConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue STRONGER_LIGHTS_ENABLED;
    public static final ModConfigSpec.IntValue STRONGER_LIGHTS_RADIUS;
    public static final ModConfigSpec.IntValue STRONGER_LIGHTS_LEVEL;
    public static final ModConfigSpec.BooleanValue MINER_HELMET_ENABLED;
    public static final ModConfigSpec.IntValue MINER_HELMET_CAPACITY;
    public static final ModConfigSpec.IntValue MINER_HELMET_MAX_RECEIVE;
    public static final ModConfigSpec.IntValue MINER_HELMET_MAX_EXTRACT;
    public static final ModConfigSpec.IntValue MINER_HELMET_ENERGY_PER_SECOND;
    public static final ModConfigSpec.IntValue MINER_HELMET_BATTERY_RECHARGE_BATCH;

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

        BUILDER.push("miner_helmet");

        MINER_HELMET_ENABLED = BUILDER
                .comment("Enable the miner helmet night vision effect.")
                .define("enabled", true);

        MINER_HELMET_CAPACITY = BUILDER
                .comment("Internal FE buffer capacity for the miner helmet.")
                .defineInRange("capacity", 1200, 1, Integer.MAX_VALUE);

        MINER_HELMET_MAX_RECEIVE = BUILDER
                .comment("Maximum FE the miner helmet can receive per transfer.")
                .defineInRange("max_receive", 100, 0, Integer.MAX_VALUE);

        MINER_HELMET_MAX_EXTRACT = BUILDER
                .comment("Maximum FE the miner helmet can extract per transfer.")
                .defineInRange("max_extract", 100, 1, Integer.MAX_VALUE);

        MINER_HELMET_ENERGY_PER_SECOND = BUILDER
                .comment("FE consumed each second while the miner helmet grants night vision.")
                .defineInRange("energy_per_second", 1, 1, Integer.MAX_VALUE);

        MINER_HELMET_BATTERY_RECHARGE_BATCH = BUILDER
                .comment("FE requested from a worn Power Grid portable battery when the miner helmet refills itself.")
                .defineInRange("battery_recharge_batch", 20, 1, Integer.MAX_VALUE);

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

    public static boolean minerHelmetEnabled() {
        return MINER_HELMET_ENABLED.get();
    }

    public static int minerHelmetCapacity() {
        return MINER_HELMET_CAPACITY.get();
    }

    public static int minerHelmetMaxReceive() {
        return MINER_HELMET_MAX_RECEIVE.get();
    }

    public static int minerHelmetMaxExtract() {
        return MINER_HELMET_MAX_EXTRACT.get();
    }

    public static int minerHelmetEnergyPerSecond() {
        return MINER_HELMET_ENERGY_PER_SECOND.get();
    }

    public static int minerHelmetBatteryRechargeBatch() {
        return MINER_HELMET_BATTERY_RECHARGE_BATCH.get();
    }
}
