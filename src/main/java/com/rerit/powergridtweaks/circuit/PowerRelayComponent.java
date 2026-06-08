package com.rerit.powergridtweaks.circuit;

import com.google.common.collect.ImmutableCollection;
import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.resources.ResourceLocation;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.MirrorableComponent;
import org.patryk3211.powergrid.circuits.components.properties.BooleanProperty;
import org.patryk3211.powergrid.circuits.components.properties.CalculatedProperty;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.components.properties.FloatProperty;
import org.patryk3211.powergrid.circuits.schematic.ComponentFootprint;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.patryk3211.powergrid.collections.ModdedSoundEvents;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;
import org.patryk3211.powergrid.electricity.sim.special.RelaySwitchWire;
import org.patryk3211.powergrid.utility.Unit;

import java.util.Collection;
import java.util.List;

public class PowerRelayComponent extends MirrorableComponent {
    public static final FloatProperty THRESHOLD_VOLTAGE = new FloatProperty(
            PowerGridTweaks.MOD_ID,
            "power_relay_threshold",
            240.0f,
            1.0f,
            600.0f
    );
    public static final FloatProperty LOWER_THRESHOLD_VOLTAGE = new FloatProperty(
            PowerGridTweaks.MOD_ID,
            "power_relay_lower_threshold",
            216.0f,
            1.0f,
            600.0f
    );
    public static final BooleanProperty STATE = new BooleanProperty(
            PowerGridTweaks.MOD_ID,
            "power_relay_state"
    ).hidden().cast();
    public static final CalculatedProperty<Float> THRESHOLD_CURRENT = new CalculatedProperty<>(
            PowerGridTweaks.MOD_ID,
            "power_relay_current",
            component -> 1.2f / component.get(THRESHOLD_VOLTAGE),
            value -> Unit.CURRENT.formatWithPrefixes(value).string()
    );

    private static final float CONTACT_RESISTANCE = 0.02f;
    private static final float RATED_CONTACT_CURRENT = 40.0f;
    private static final float CONTACT_OVERHEAT_TEMPERATURE = 225.0f;
    private static final float CONTACT_THERMAL_MASS = 4.0f;
    private static final float COIL_OVERHEAT_TEMPERATURE = 125.0f;
    private static final float COIL_THERMAL_MASS = 0.02f;

    private static final ComponentFootprint FOOTPRINT = new ComponentFootprint.Builder(4, 3)
            .addPad(0, 0, 0)
            .addPad(0, 2, 1)
            .addPad(2, 0, 2)
            .addPad(2, 1, 3)
            .addPad(2, 2, 4)
            .withItem()
            .withOutline()
            .build();

    public PowerRelayComponent() {
        super(FOOTPRINT);
    }

    @Override
    protected void addProperties(ImmutableCollection.Builder<ComponentProperty<?>> builder) {
        super.addProperties(builder);
        builder.add(
                THRESHOLD_VOLTAGE,
                LOWER_THRESHOLD_VOLTAGE,
                STATE,
                THRESHOLD_CURRENT,
                current(RATED_CONTACT_CURRENT)
        );
    }

    @Override
    public void bake(PlacedComponent component, ComponentCircuitBuilder builder, ThermalBuilder.IEmitter thermals) {
        float triggerCurrent = component.get(THRESHOLD_CURRENT);
        float thresholdVoltage = component.get(THRESHOLD_VOLTAGE);
        float holdingCurrent = triggerCurrent * Math.min(component.get(LOWER_THRESHOLD_VOLTAGE), thresholdVoltage)
                / thresholdVoltage;
        float coilResistance = thresholdVoltage / triggerCurrent;

        ElectricWire coil = builder.connect(coilResistance, builder.terminalNode(0), builder.terminalNode(1));
        var common = builder.terminalNode(3);
        boolean state = component.get(STATE);

        RelaySwitchWire normallyClosed = new RelaySwitchWire(
                CONTACT_RESISTANCE,
                common,
                builder.terminalNode(2),
                !state,
                coil,
                triggerCurrent,
                holdingCurrent,
                true
        );
        RelaySwitchWire normallyOpen = new RelaySwitchWire(
                CONTACT_RESISTANCE,
                common,
                builder.terminalNode(4),
                state,
                coil,
                triggerCurrent,
                holdingCurrent,
                false
        );

        builder.add(normallyClosed);
        builder.add(normallyOpen);
        component.add(normallyClosed);
        component.add(normallyOpen);

        thermals.builder()
                .setMaxCurrent(triggerCurrent * 2.0f, coilResistance, COIL_OVERHEAT_TEMPERATURE)
                .setThermalMass(COIL_THERMAL_MASS)
                .addHeatSource(coil);
        thermals.builder()
                .setMaxCurrent(RATED_CONTACT_CURRENT, CONTACT_RESISTANCE, CONTACT_OVERHEAT_TEMPERATURE)
                .setThermalMass(CONTACT_THERMAL_MASS)
                .addHeatSource(normallyClosed)
                .addHeatSource(normallyOpen);
    }

    @Override
    public boolean tick(PlacedComponent component) {
        if (component.wires.isEmpty()) {
            return true;
        }

        RelaySwitchWire normallyClosed = (RelaySwitchWire) component.wires.get(0);
        RelaySwitchWire normallyOpen = (RelaySwitchWire) component.wires.get(1);

        if (normallyClosed.wasSwitched() || normallyOpen.wasSwitched()) {
            boolean state = normallyOpen.getState();
            component.onServerWorld(() -> world -> ModdedSoundEvents.RELAY_CLICK.playOnServer(
                    world,
                    component.getPos(),
                    0.75f,
                    state ? 2.0f : 1.9f
            ));
            component.set(STATE, state);
        }

        return true;
    }

    @Override
    public ResourceLocation getModelId(PlacedComponent component) {
        return PowerGrid.asResource("relay");
    }

    @Override
    public Collection<ResourceLocation> requestedModels() {
        return List.of(PowerGrid.asResource("relay"));
    }
}
