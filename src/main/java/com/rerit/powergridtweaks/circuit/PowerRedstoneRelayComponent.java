package com.rerit.powergridtweaks.circuit;

import com.google.common.collect.ImmutableCollection;
import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.resources.ResourceLocation;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.EdgeComponent;
import org.patryk3211.powergrid.circuits.components.IRedstoneComponent;
import org.patryk3211.powergrid.circuits.components.properties.BooleanProperty;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.schematic.ComponentFootprint;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.patryk3211.powergrid.electricity.sim.SwitchedWire;

import java.util.Collection;
import java.util.List;

public class PowerRedstoneRelayComponent extends EdgeComponent implements IRedstoneComponent {
    public static final BooleanProperty POWERED =
            new BooleanProperty(PowerGridTweaks.MOD_ID, "power_redstone_relay_powered");

    private static final float CONTACT_RESISTANCE = 0.02f;
    private static final float RATED_CURRENT = 40.0f;
    private static final float OVERHEAT_TEMPERATURE = 225.0f;
    private static final float THERMAL_MASS = 4.0f;

    private static final ComponentFootprint FOOTPRINT = new ComponentFootprint.Builder(3, 5)
            .addPad(1, 0, 0)
            .addPad(1, 4, 1)
            .withItem()
            .withArrow()
            .withOutline()
            .build();

    public PowerRedstoneRelayComponent() {
        super(FOOTPRINT);
    }

    @Override
    protected void addProperties(ImmutableCollection.Builder<ComponentProperty<?>> builder) {
        super.addProperties(builder);
        builder.add(POWERED, current(RATED_CURRENT));
    }

    @Override
    public void bake(PlacedComponent component, ComponentCircuitBuilder builder, ThermalBuilder.IEmitter thermals) {
        SwitchedWire wire = builder.connectSwitch(
                CONTACT_RESISTANCE,
                builder.terminalNode(0),
                builder.terminalNode(1),
                component.get(POWERED)
        );
        component.add(wire);
        thermals.builder()
                .setMaxCurrent(RATED_CURRENT, CONTACT_RESISTANCE, OVERHEAT_TEMPERATURE)
                .setThermalMass(THERMAL_MASS)
                .addHeatSource(wire);
    }

    @Override
    public boolean isReceiver() {
        return true;
    }

    @Override
    public void receiveRedstone(PlacedComponent component, int level) {
        component.set(POWERED, level > 0);
        component.notifyClients(POWERED);
        stateUpdated(component);
    }

    @Override
    public void stateUpdated(PlacedComponent component) {
        component.onClientWorld(() -> world -> modelChanged(component.getPos()));

        if (component.wires.isEmpty()) {
            return;
        }

        ((SwitchedWire) component.wires.get(0)).setState(component.get(POWERED));
    }

    @Override
    public ResourceLocation getModelId(PlacedComponent component) {
        return component.get(POWERED)
                ? PowerGrid.asResource("redstone_relay_on")
                : PowerGrid.asResource("redstone_relay");
    }

    @Override
    public Collection<ResourceLocation> requestedModels() {
        return List.of(PowerGrid.asResource("redstone_relay"), PowerGrid.asResource("redstone_relay_on"));
    }
}
