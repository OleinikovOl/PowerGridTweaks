package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.item.FePoweredItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;

public class ModItemCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.EnergyStorage.ITEM,
                (stack, context) -> {
                    Item item = stack.getItem();
                    if (!(item instanceof FePoweredItem poweredItem)) {
                        return null;
                    }

                    return new ComponentEnergyStorage(
                            stack,
                            ModDataComponents.ENERGY.get(),
                            poweredItem.energyCapacity(stack),
                            poweredItem.maxReceive(stack),
                            poweredItem.maxExtract(stack)
                    );
                },
                ModItems.MINER_HELMET.get()
        );
    }
}
