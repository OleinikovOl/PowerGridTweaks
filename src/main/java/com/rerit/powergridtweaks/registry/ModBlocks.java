package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.block.LithiumBatteryBlock;
import com.rerit.powergridtweaks.block.ShaftGeneratorBlock;
import com.rerit.powergridtweaks.registry.block.ModMachineBlocks;
import com.rerit.powergridtweaks.registry.block.ModOreBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PowerGridTweaks.MOD_ID);

    public static final DeferredBlock<ShaftGeneratorBlock> SHAFT_GENERATOR =
            ModMachineBlocks.SHAFT_GENERATOR;

    public static final DeferredBlock<LithiumBatteryBlock> LITHIUM_BATTERY =
            ModMachineBlocks.LITHIUM_BATTERY;

    public static final DeferredBlock<Block> NETHER_LITHIUM_ORE =
            ModOreBlocks.NETHER_LITHIUM_ORE;

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
