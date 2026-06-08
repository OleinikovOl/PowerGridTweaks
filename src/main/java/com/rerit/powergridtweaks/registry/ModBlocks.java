package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.block.LithiumBatteryBlock;
import com.rerit.powergridtweaks.block.ShaftGeneratorBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PowerGridTweaks.MOD_ID);

    public static final DeferredBlock<ShaftGeneratorBlock> SHAFT_GENERATOR =
            BLOCKS.registerBlock(
                    "shaft_generator",
                    ShaftGeneratorBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                            .strength(3.5f, 6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<LithiumBatteryBlock> LITHIUM_BATTERY =
            BLOCKS.registerBlock(
                    "lithium_battery",
                    LithiumBatteryBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<net.minecraft.world.level.block.Block> NETHER_LITHIUM_ORE =
            BLOCKS.registerSimpleBlock(
                    "nether_lithium_ore",
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)
                            .requiresCorrectToolForDrops()
            );
}
