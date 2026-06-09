package com.rerit.powergridtweaks.item;

import java.util.function.IntPredicate;

public final class FeEnergyLogic {
    private FeEnergyLogic() {
    }

    public static boolean consumeEnergy(EnergyBuffer storage, IntPredicate externalEnergySource, int amount) {
        if (amount <= 0) {
            return false;
        }

        if (storage.extractEnergy(amount, true) >= amount) {
            storage.extractEnergy(amount, false);
            return true;
        }

        int shortfall = amount - storage.extractEnergy(amount, true);
        recharge(storage, externalEnergySource, shortfall);

        if (storage.extractEnergy(amount, true) < amount) {
            return false;
        }

        storage.extractEnergy(amount, false);
        return true;
    }

    public static int recharge(EnergyBuffer storage, IntPredicate externalEnergySource, int requestedAmount) {
        if (requestedAmount <= 0) {
            return 0;
        }

        int space = storage.getMaxEnergyStored() - storage.getEnergyStored();
        if (space <= 0) {
            return 0;
        }

        int requested = Math.min(space, requestedAmount);
        int receivable = storage.receiveEnergy(requested, true);
        if (receivable <= 0 || !externalEnergySource.test(receivable)) {
            return 0;
        }

        return storage.receiveEnergy(receivable, false);
    }

    public interface EnergyBuffer {
        int receiveEnergy(int maxReceive, boolean simulate);

        int extractEnergy(int maxExtract, boolean simulate);

        int getEnergyStored();

        int getMaxEnergyStored();
    }
}
