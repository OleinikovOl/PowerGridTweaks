package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.item.HighVoltageLightBulbItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PowerGridTweaks.MOD_ID);

    public static final DeferredItem<Item> HIGH_VOLTAGE_LIGHT_BULB =
            ITEMS.register("high_voltage_light_bulb",
                    () -> new HighVoltageLightBulbItem(new Item.Properties()));

    public static final DeferredItem<BlockItem> SHAFT_GENERATOR =
            ITEMS.registerSimpleBlockItem(ModBlocks.SHAFT_GENERATOR, new Item.Properties());
}
