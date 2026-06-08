package com.rerit.powergridtweaks.circuit;

import com.google.common.collect.ImmutableCollection;
import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.circuits.circuitboard.CircuitBoardBlockEntity;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.IInteractableComponent;
import org.patryk3211.powergrid.circuits.components.OrientableComponent;
import org.patryk3211.powergrid.circuits.components.properties.BooleanProperty;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.schematic.ComponentFootprint;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.patryk3211.powergrid.electricity.sim.SwitchedWire;

import java.util.Collection;
import java.util.List;

public class PowerSwitchComponent extends OrientableComponent implements IInteractableComponent {
    public static final BooleanProperty STATE = new BooleanProperty(PowerGridTweaks.MOD_ID, "power_switch_state");

    protected static final float CONTACT_RESISTANCE = 0.02f;
    protected static final float RATED_CURRENT = 40.0f;
    protected static final float OVERHEAT_TEMPERATURE = 225.0f;
    protected static final float THERMAL_MASS = 2.0f;

    private static final ComponentFootprint FOOTPRINT = new ComponentFootprint.Builder(3, 3)
            .addPad(0, 1, 0)
            .addPad(2, 1, 1)
            .withItem()
            .withOutline()
            .build();

    public PowerSwitchComponent() {
        super(FOOTPRINT);
    }

    @Override
    protected void addProperties(ImmutableCollection.Builder<ComponentProperty<?>> builder) {
        super.addProperties(builder);
        builder.add(STATE, current(RATED_CURRENT));
    }

    @Override
    public void bake(PlacedComponent component, ComponentCircuitBuilder builder, ThermalBuilder.IEmitter thermals) {
        SwitchedWire wire = builder.connectSwitch(
                CONTACT_RESISTANCE,
                builder.terminalNode(0),
                builder.terminalNode(1),
                component.get(STATE)
        );
        component.add(wire);
        thermals.builder()
                .setMaxCurrent(RATED_CURRENT, CONTACT_RESISTANCE, OVERHEAT_TEMPERATURE)
                .setThermalMass(THERMAL_MASS)
                .addHeatSource(wire);
    }

    @Override
    public VoxelShape getShape(PlacedComponent component) {
        return IInteractableComponent.extrudedFootprint(component, 0.125f);
    }

    @Override
    public InteractionResult use(CircuitBoardBlockEntity board, PlacedComponent component, Player player) {
        boolean newState = !component.get(STATE);
        component.set(STATE, newState);
        component.notifyClients(STATE);
        stateUpdated(component);
        board.setChanged();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void stateUpdated(PlacedComponent component) {
        if (component.wires.isEmpty()) {
            return;
        }

        ((SwitchedWire) component.wires.get(0)).setState(component.get(STATE));
        component.onClientWorld(() -> world -> modelChanged(component.getPos()));
    }

    @Override
    public ResourceLocation getModelId(PlacedComponent component) {
        return component.get(STATE)
                ? PowerGrid.asResource("switch_on")
                : PowerGrid.asResource("switch");
    }

    @Override
    public Collection<ResourceLocation> requestedModels() {
        return List.of(PowerGrid.asResource("switch"), PowerGrid.asResource("switch_on"));
    }
}
