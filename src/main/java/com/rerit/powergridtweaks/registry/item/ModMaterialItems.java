package com.rerit.powergridtweaks.registry.item;

import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class ModMaterialItems {
    public static final DeferredItem<Item> RAW_LITHIUM =
            ModItems.ITEMS.registerSimpleItem("raw_lithium", new Item.Properties());

    public static final DeferredItem<Item> CRUSHED_RAW_LITHIUM =
            ModItems.ITEMS.registerSimpleItem("crushed_raw_lithium", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_INGOT =
            ModItems.ITEMS.registerSimpleItem("lithium_ingot", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_NUGGET =
            ModItems.ITEMS.registerSimpleItem("lithium_nugget", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_SHEET =
            ModItems.ITEMS.registerSimpleItem("lithium_sheet", new Item.Properties());

    public static final DeferredItem<Item> GRAPHITE_SHEET =
            ModItems.ITEMS.registerSimpleItem("graphite_sheet", new Item.Properties());

    public static final DeferredItem<Item> LITHIUM_CELL =
            ModItems.ITEMS.registerSimpleItem("lithium_cell", new Item.Properties());

    public static final DeferredItem<Item> INCOMPLETE_LITHIUM_CELL =
            ModItems.ITEMS.registerSimpleItem("incomplete_lithium_cell", new Item.Properties());

    public static final DeferredItem<Item> CARBON_MASS =
            ModItems.ITEMS.registerSimpleItem("carbon_mass", new Item.Properties());

    public static final List<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS =
            List.of(
                    RAW_LITHIUM,
                    CRUSHED_RAW_LITHIUM,
                    LITHIUM_INGOT,
                    LITHIUM_NUGGET,
                    LITHIUM_SHEET,
                    GRAPHITE_SHEET,
                    LITHIUM_CELL,
                    INCOMPLETE_LITHIUM_CELL,
                    CARBON_MASS
            );

    private ModMaterialItems() {
    }
}
