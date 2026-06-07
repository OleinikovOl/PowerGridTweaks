package com.rerit.powergridtweaks.feature;

public interface PowerGridFeature {
    String id();

    boolean enabled();

    default void init() {
    }
}
