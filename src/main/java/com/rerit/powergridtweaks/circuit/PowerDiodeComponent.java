package com.rerit.powergridtweaks.circuit;

import com.google.common.collect.ImmutableCollection;
import net.minecraft.resources.ResourceLocation;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.VerticallyOrientableComponent;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.schematic.ComponentFootprint;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.patryk3211.powergrid.electricity.sim.special.PNJunctionWire;

import java.util.Collection;
import java.util.List;

public class PowerDiodeComponent extends VerticallyOrientableComponent {
    private static final float RATED_POWER = 5000.0f;
    private static final float OVERHEAT_TEMPERATURE = 225.0f;
    private static final float THERMAL_MASS = 4.0f;

    private static final ComponentFootprint HORIZONTAL_FOOTPRINT = new ComponentFootprint.Builder(
            5,
            3,
            null,
            "component.powergrid"
    )
            .addPadSharedText(0, 1, 0, "generic.cathode", "generic.cathode.short")
            .addPadSharedText(4, 1, 1, "generic.anode", "generic.anode.short")
            .withItem()
            .withOutline()
            .build();
    private static final ComponentFootprint VERTICAL_FOOTPRINT = new ComponentFootprint.Builder(
            3,
            3,
            null,
            "component.powergrid"
    )
            .addPadSharedText(0, 1, 0, "generic.cathode", "generic.cathode.short")
            .addPadSharedText(2, 1, 1, "generic.anode", "generic.anode.short")
            .withItem()
            .withOutline()
            .build();

    public PowerDiodeComponent() {
        super(HORIZONTAL_FOOTPRINT, VERTICAL_FOOTPRINT);
    }

    @Override
    protected void addProperties(ImmutableCollection.Builder<ComponentProperty<?>> builder) {
        super.addProperties(builder);
        builder.add(power(RATED_POWER));
    }

    @Override
    public void bake(PlacedComponent component, ComponentCircuitBuilder builder, ThermalBuilder.IEmitter thermals) {
        PNJunctionWire diode = new PNJunctionWire(
                5.47E-6,
                0.07500000298023224,
                22.0,
                1.783,
                builder.terminalNode(1),
                builder.terminalNode(0)
        );
        builder.add(diode);
        thermals.builder()
                .setThermalMass(THERMAL_MASS)
                .setMaxPower(RATED_POWER, OVERHEAT_TEMPERATURE)
                .setOverheatTemperature(OVERHEAT_TEMPERATURE)
                .withTemperatureCallback(temperature -> diode.setTemperatureCelsius(temperature))
                .addHeatSource(diode);
    }

    @Override
    public ResourceLocation getModelId(PlacedComponent component) {
        return component.get(VERTICAL)
                ? PowerGrid.asResource("diode_vertical")
                : PowerGrid.asResource("diode");
    }

    @Override
    public Collection<ResourceLocation> requestedModels() {
        return List.of(PowerGrid.asResource("diode"), PowerGrid.asResource("diode_vertical"));
    }
}
