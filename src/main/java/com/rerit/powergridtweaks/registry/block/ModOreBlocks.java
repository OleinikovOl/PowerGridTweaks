package com.rerit.powergridtweaks.registry.block;

import com.rerit.powergridtweaks.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModOreBlocks {
    public static final DeferredBlock<Block> NETHER_LITHIUM_ORE =
            ModBlocks.BLOCKS.registerSimpleBlock(
                    "nether_lithium_ore",
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)
                            .requiresCorrectToolForDrops()
            );

    private ModOreBlocks() {
    }
}
