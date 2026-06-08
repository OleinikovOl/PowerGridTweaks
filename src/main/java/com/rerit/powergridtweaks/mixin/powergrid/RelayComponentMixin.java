package com.rerit.powergridtweaks.mixin.powergrid;

import com.google.common.collect.ImmutableCollection;
import com.rerit.powergridtweaks.feature.relay.RelayThresholdHelper;
import org.patryk3211.powergrid.circuits.circuitboard.ComponentCircuitBuilder;
import org.patryk3211.powergrid.circuits.components.RelayComponent;
import org.patryk3211.powergrid.circuits.components.properties.ComponentProperty;
import org.patryk3211.powergrid.circuits.schematic.PlacedComponent;
import org.patryk3211.powergrid.circuits.thermal.ThermalBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RelayComponent.class)
public abstract class RelayComponentMixin {
    @Inject(method = "addProperties", at = @At("TAIL"))
    private void powergridtweaks$addLowerThreshold(
            ImmutableCollection.Builder<ComponentProperty<?>> builder,
            CallbackInfo ci
    ) {
        builder.add(RelayThresholdHelper.LOWER_THRESHOLD_VOLTAGE);
    }

    @Inject(method = "bake", at = @At("HEAD"))
    private void powergridtweaks$beginBake(
            PlacedComponent component,
            ComponentCircuitBuilder builder,
            ThermalBuilder.IEmitter thermals,
            CallbackInfo ci
    ) {
        RelayThresholdHelper.beginBake(component);
    }

    @Inject(method = "bake", at = @At("RETURN"))
    private void powergridtweaks$endBake(
            PlacedComponent component,
            ComponentCircuitBuilder builder,
            ThermalBuilder.IEmitter thermals,
            CallbackInfo ci
    ) {
        RelayThresholdHelper.endBake();
    }

    @ModifyArg(
            method = "bake",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/patryk3211/powergrid/electricity/sim/special/RelaySwitchWire;<init>(FLorg/patryk3211/powergrid/electricity/sim/node/IElectricNode;Lorg/patryk3211/powergrid/electricity/sim/node/IElectricNode;ZLorg/patryk3211/powergrid/electricity/sim/ElectricWire;FFZ)V"
            ),
            index = 6
    )
    private float powergridtweaks$useLowerThreshold(float originalHoldingCurrent) {
        return RelayThresholdHelper.holdingCurrent(originalHoldingCurrent);
    }
}
