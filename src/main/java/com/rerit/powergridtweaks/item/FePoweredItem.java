package com.rerit.powergridtweaks.item;

import com.rerit.powergridtweaks.registry.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.patryk3211.powergrid.equipment.portablebattery.BatteryUtils;

import java.util.List;

public interface FePoweredItem {
    int BAR_COLOR = 0x4DA6FF;

    int energyCapacity(ItemStack stack);

    int maxReceive(ItemStack stack);

    int maxExtract(ItemStack stack);

    int batteryRechargeBatch(ItemStack stack);

    default IEnergyStorage energyStorage(ItemStack stack) {
        return new ComponentEnergyStorage(
                stack,
                ModDataComponents.ENERGY.get(),
                energyCapacity(stack),
                maxReceive(stack),
                maxExtract(stack)
        );
    }

    default boolean consumeEnergy(ItemStack stack, Player player, int amount) {
        return FeEnergyLogic.consumeEnergy(
                energyBuffer(stack),
                externalAmount -> BatteryUtils.drawEnergy(player, externalAmount),
                amount
        );
    }

    default int getEnergyStored(ItemStack stack) {
        return energyStorage(stack).getEnergyStored();
    }

    default int getBarWidth(ItemStack stack) {
        int capacity = energyCapacity(stack);
        if (capacity <= 0) {
            return 0;
        }

        return Math.round(13.0f * getEnergyStored(stack) / capacity);
    }

    default boolean isBarVisible(ItemStack stack) {
        return energyCapacity(stack) > 0 && getEnergyStored(stack) < energyCapacity(stack);
    }

    default void appendEnergyTooltip(ItemStack stack, List<Component> tooltip) {
        tooltip.add(Component.translatable(
                "item.powergridtweaks.tooltip.energy",
                getEnergyStored(stack),
                energyCapacity(stack)
        ));
    }

    default int rechargeFromPortableBattery(ItemStack stack, Player player) {
        return rechargeFromPortableBattery(stack, player, batteryRechargeBatch(stack));
    }

    default int rechargeFromPortableBattery(ItemStack stack, Player player, int requestedAmount) {
        return FeEnergyLogic.recharge(
                energyBuffer(stack),
                amount -> BatteryUtils.drawEnergy(player, amount),
                requestedAmount
        );
    }

    private FeEnergyLogic.EnergyBuffer energyBuffer(ItemStack stack) {
        IEnergyStorage storage = energyStorage(stack);
        return new FeEnergyLogic.EnergyBuffer() {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return storage.receiveEnergy(maxReceive, simulate);
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return storage.extractEnergy(maxExtract, simulate);
            }

            @Override
            public int getEnergyStored() {
                return storage.getEnergyStored();
            }

            @Override
            public int getMaxEnergyStored() {
                return storage.getMaxEnergyStored();
            }
        };
    }
}
