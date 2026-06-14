package com.rerit.powergridtweaks.registry.item;

import com.rerit.powergridtweaks.registry.ModBlocks;
import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class ModBlockItems {
    public static final DeferredItem<BlockItem> SHAFT_GENERATOR =
            ModItems.ITEMS.registerSimpleBlockItem(ModBlocks.SHAFT_GENERATOR, new Item.Properties());

    public static final DeferredItem<BlockItem> LITHIUM_BATTERY =
            ModItems.ITEMS.registerSimpleBlockItem(ModBlocks.LITHIUM_BATTERY, new Item.Properties());

    public static final DeferredItem<BlockItem> NETHER_LITHIUM_ORE =
            ModItems.ITEMS.registerSimpleBlockItem(ModBlocks.NETHER_LITHIUM_ORE, new Item.Properties());

    public static final List<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS =
            List.of(
                    SHAFT_GENERATOR,
                    LITHIUM_BATTERY,
                    NETHER_LITHIUM_ORE
            );

    private ModBlockItems() {
    }
}
