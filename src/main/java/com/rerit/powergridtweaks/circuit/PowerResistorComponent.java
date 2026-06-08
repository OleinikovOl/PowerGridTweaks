package com.rerit.powergridtweaks.circuit;

import com.google.common.collect.ImmutableCollection;
import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.resources.ResourceLocation;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.VerticallyOrientableComponent;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.components.properties.FloatProperty;
import org.patryk3211.powergrid.circuits.schematic.ComponentFootprint;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;

import java.util.Collection;
import java.util.List;

public class PowerResistorComponent extends VerticallyOrientableComponent {
    public static final FloatProperty RESISTANCE = new FloatProperty(
            PowerGridTweaks.MOD_ID,
            "power_resistor_value",
            2.0f,
            0.05f,
            10000.0f
    );

    private static final float RATED_POWER = 5000.0f;
    private static final float OVERHEAT_TEMPERATURE = 225.0f;
    private static final float THERMAL_MASS = 8.0f;

    private static final ComponentFootprint HORIZONTAL_FOOTPRINT = new ComponentFootprint.Builder(5, 3)
            .addPad(0, 1, 0)
            .addPad(4, 1, 1)
            .withItem()
            .withOutline()
            .build();
    private static final ComponentFootprint VERTICAL_FOOTPRINT = new ComponentFootprint.Builder(3, 3)
            .addPad(0, 1, 0)
            .addPad(2, 1, 1)
            .withItem()
            .withOutline()
            .build();

    public PowerResistorComponent() {
        super(HORIZONTAL_FOOTPRINT, VERTICAL_FOOTPRINT);
    }

    @Override
    protected void addProperties(ImmutableCollection.Builder<ComponentProperty<?>> builder) {
        super.addProperties(builder);
        builder.add(RESISTANCE, power(RATED_POWER));
    }

    @Override
    public void bake(PlacedComponent component, ComponentCircuitBuilder builder, ThermalBuilder.IEmitter thermals) {
        ElectricWire resistor = builder.connect(
                component.get(RESISTANCE),
                builder.terminalNode(0),
                builder.terminalNode(1)
        );
        thermals.builder()
                .setThermalMass(THERMAL_MASS)
                .setMaxPower(RATED_POWER, OVERHEAT_TEMPERATURE)
                .addHeatSource(resistor);
    }

    @Override
    public ResourceLocation getModelId(PlacedComponent component) {
        return component.get(VERTICAL)
                ? PowerGrid.asResource("resistor_vertical")
                : PowerGrid.asResource("resistor");
    }

    @Override
    public Collection<ResourceLocation> requestedModels() {
        return List.of(PowerGrid.asResource("resistor"), PowerGrid.asResource("resistor_vertical"));
    }
}
