package com.rerit.powergridtweaks.block.entity;

import com.rerit.powergridtweaks.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.collections.ModdedConfigs;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.calculation.Precalculated;
import org.patryk3211.powergrid.electricity.sim.special.GeneratorCoupling;
import org.patryk3211.powergrid.electricity.sim.special.IRotor;
import org.patryk3211.powergrid.kinetics.base.ElectricKineticBlockEntity;

public class ShaftGeneratorBlockEntity extends ElectricKineticBlockEntity implements IRotor {
    public static final float NOMINAL_SPEED = 120.0f;
    public static final float NOMINAL_VOLTAGE = 240.0f;
    public static final float MAX_VOLTAGE = 300.0f;
    public static final float RATED_OUTPUT_POWER = 3000.0f;
    public static final float INTERNAL_RESISTANCE = NOMINAL_VOLTAGE * NOMINAL_VOLTAGE / (4.0f * RATED_OUTPUT_POWER);
    public static final float MAX_OUTPUT_POWER = MAX_VOLTAGE * MAX_VOLTAGE / (4.0f * INTERNAL_RESISTANCE);
    public static final float BASE_STRESS_IMPACT = 2.0f;

    private static final float DEFAULT_TORQUE_FOR_STRESS = 15.0f;
    private static final float POWER_TO_SPEED = 94.24778f;
    private static final float MECHANICAL_LOAD_MULTIPLIER = 1.0f;
    private static final float MAX_DYNAMIC_STRESS_IMPACT = 1024.0f;
    private static final float VOLTS_PER_RPM = NOMINAL_VOLTAGE / NOMINAL_SPEED;
    private static final float MAX_SPEED = MAX_VOLTAGE / VOLTS_PER_RPM;
    private static final float FIELD_STRENGTH = NOMINAL_VOLTAGE / (NOMINAL_SPEED * ((float) Math.PI / 30.0f));
    private static final float ARMATURE_INERTIA = 32.0f;
    private static final float STRESS_UPDATE_THRESHOLD = 0.01f;
    private static final float STRESS_SYNC_THRESHOLD = 0.1f;

    private GeneratorCoupling source;
    private float lastNetworkStressImpact = -1.0f;
    private float lastSyncedStressImpact = -1.0f;

    public ShaftGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ShaftGeneratorBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.SHAFT_GENERATOR.get(), pos, state);
    }

    @Override
    public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
        builder.setTerminalCount(2);
        source = new GeneratorCoupling(
                builder.terminalNode(0),
                builder.terminalNode(1),
                INTERNAL_RESISTANCE,
                this);
        source.setFieldStrengthProvider(constant(FIELD_STRENGTH));
        builder.add(source);
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide && !isVirtual()) {
            return;
        }

        float stressImpact = calculateStressApplied();
        if (hasNetwork() && Math.abs(stressImpact - lastNetworkStressImpact) > STRESS_UPDATE_THRESHOLD) {
            getOrCreateNetwork().updateStressFor(this, stressImpact);
            lastNetworkStressImpact = stressImpact;
        }

        if (Math.abs(stressImpact - lastSyncedStressImpact) > STRESS_SYNC_THRESHOLD) {
            sendData();
            lastSyncedStressImpact = stressImpact;
        }
    }

    public float generatedVoltage() {
        return Mth.clamp(getSpeed() * VOLTS_PER_RPM, -MAX_VOLTAGE, MAX_VOLTAGE);
    }

    @Override
    public float calculateStressApplied() {
        if (level != null && level.isClientSide && !isVirtual()) {
            return lastStressApplied == 0.0f ? BASE_STRESS_IMPACT : lastStressApplied;
        }

        float stressImpact = BASE_STRESS_IMPACT;

        if (source != null) {
            float speed = Math.max(1.0f, Math.abs(getTheoreticalSpeed()));
            float electricalPower = (float) Math.abs(source.getCurrent() * source.getVoltage());
            float dynamicStress = electricalPower * MECHANICAL_LOAD_MULTIPLIER * POWER_TO_SPEED
                    / (speed * torqueForStress());
            stressImpact += Math.min(dynamicStress, MAX_DYNAMIC_STRESS_IMPACT);
        }

        lastStressApplied = stressImpact;
        return stressImpact;
    }

    @Override
    public float getInertia() {
        return ARMATURE_INERTIA;
    }

    @Override
    public float getAngularVelocity() {
        return Mth.clamp(getSpeed(), -MAX_SPEED, MAX_SPEED);
    }

    @Override
    public void applyTickForce(float force) {
        // Create owns the actual shaft speed; dynamic SU is updated from solved electrical power.
    }

    private static float torqueForStress() {
        return ModdedConfigs.server() == null
                ? DEFAULT_TORQUE_FOR_STRESS
                : ModdedConfigs.server().kinetics.torqueForStress.getF();
    }

    private static Precalculated<Float> constant(float value) {
        return new Precalculated<>(value) {
            @Override
            public Float get() {
                return this.value;
            }

            @Override
            public int getStamp() {
                return this.ourStamp;
            }

            @Override
            public void invalidate() {
            }
        };
    }
}
