package com.rerit.powergridtweaks.item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FeEnergyLogicTest {
    @Test
    void consumesExistingInternalEnergyWithoutExternalDraw() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 20, 20, 15);
        RecordingEnergySource source = new RecordingEnergySource(true);

        assertTrue(FeEnergyLogic.consumeEnergy(storage, source, 5));

        assertEquals(10, storage.getEnergyStored());
        assertEquals(List.of(), source.requests);
    }

    @Test
    void returnsFalseForZeroOrNegativeConsumption() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 20, 20, 15);

        assertFalse(FeEnergyLogic.consumeEnergy(storage, amount -> true, 0));
        assertFalse(FeEnergyLogic.consumeEnergy(storage, amount -> true, -1));
        assertEquals(15, storage.getEnergyStored());
    }

    @Test
    void rechargesOnlyWhatStorageCanAccept() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 7, 20, 95);
        RecordingEnergySource source = new RecordingEnergySource(true);

        int received = FeEnergyLogic.recharge(storage, source, 20);

        assertEquals(5, received);
        assertEquals(100, storage.getEnergyStored());
        assertEquals(List.of(5), source.requests);
    }

    @Test
    void doesNotChargeWhenExternalSourceRejectsRequest() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 20, 20, 10);
        RecordingEnergySource source = new RecordingEnergySource(false);

        int received = FeEnergyLogic.recharge(storage, source, 20);

        assertEquals(0, received);
        assertEquals(10, storage.getEnergyStored());
        assertEquals(List.of(20), source.requests);
    }

    @Test
    void consumesAfterSuccessfulExternalRecharge() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 20, 20, 2);
        RecordingEnergySource source = new RecordingEnergySource(true);

        assertTrue(FeEnergyLogic.consumeEnergy(storage, source, 10));

        assertEquals(0, storage.getEnergyStored());
        assertEquals(List.of(8), source.requests);
    }

    @Test
    void doesNotConsumePartialRechargeWhenStillInsufficient() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 5, 20, 2);
        RecordingEnergySource source = new RecordingEnergySource(true);

        assertFalse(FeEnergyLogic.consumeEnergy(storage, source, 10));

        assertEquals(7, storage.getEnergyStored());
        assertEquals(List.of(5), source.requests);
    }

    @Test
    void doesNotRequestExternalEnergyWhenStorageIsFull() {
        FakeEnergyStorage storage = new FakeEnergyStorage(100, 20, 20, 100);
        RecordingEnergySource source = new RecordingEnergySource(true);

        int received = FeEnergyLogic.recharge(storage, source, 20);

        assertEquals(0, received);
        assertEquals(100, storage.getEnergyStored());
        assertEquals(List.of(), source.requests);
    }

    private static final class RecordingEnergySource implements IntPredicate {
        private final boolean result;
        private final List<Integer> requests = new ArrayList<>();

        private RecordingEnergySource(boolean result) {
            this.result = result;
        }

        @Override
        public boolean test(int amount) {
            requests.add(amount);
            return result;
        }
    }

    private static final class FakeEnergyStorage implements FeEnergyLogic.EnergyBuffer {
        private final int capacity;
        private final int maxReceive;
        private final int maxExtract;
        private int energy;

        private FakeEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
            this.capacity = capacity;
            this.maxReceive = maxReceive;
            this.maxExtract = maxExtract;
            this.energy = energy;
        }

        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
            if (!simulate) {
                energy += received;
            }
            return received;
        }

        public int extractEnergy(int maxExtract, boolean simulate) {
            int extracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
            if (!simulate) {
                energy -= extracted;
            }
            return extracted;
        }

        public int getEnergyStored() {
            return energy;
        }

        public int getMaxEnergyStored() {
            return capacity;
        }
    }
}
