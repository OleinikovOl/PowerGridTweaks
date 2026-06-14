package com.rerit.powergridtweaks.registry.block;

import com.rerit.powergridtweaks.block.LithiumBatteryBlock;
import com.rerit.powergridtweaks.block.ShaftGeneratorBlock;
import com.rerit.powergridtweaks.registry.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModMachineBlocks {
    public static final DeferredBlock<ShaftGeneratorBlock> SHAFT_GENERATOR =
            ModBlocks.BLOCKS.registerBlock(
                    "shaft_generator",
                    ShaftGeneratorBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                            .strength(3.5f, 6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<LithiumBatteryBlock> LITHIUM_BATTERY =
            ModBlocks.BLOCKS.registerBlock(
                    "lithium_battery",
                    LithiumBatteryBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
            );

    private ModMachineBlocks() {
    }
}
