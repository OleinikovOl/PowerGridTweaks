package com.rerit.powergridtweaks.block.entity;

import com.rerit.powergridtweaks.battery.LithiumBatterySpec;
import com.rerit.powergridtweaks.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.base.ThermalBehaviour;
import org.patryk3211.powergrid.electricity.battery.MultiBlockBatteryEntity;

public class LithiumBatteryBlockEntity extends MultiBlockBatteryEntity {
    public LithiumBatteryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public LithiumBatteryBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.LITHIUM_BATTERY.get(), pos, state);
    }

    @Override
    public ThermalBehaviour specifyThermalBehaviour() {
        return ThermalBehaviour.forMaxPower(this, LithiumBatterySpec.THERMAL_MASS, LithiumBatterySpec.RATED_POWER);
    }
}
