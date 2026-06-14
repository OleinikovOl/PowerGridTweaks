package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.registry.item.ModBlockItems;
import com.rerit.powergridtweaks.registry.item.ModCircuitItems;
import com.rerit.powergridtweaks.registry.item.ModMaterialItems;
import com.rerit.powergridtweaks.registry.item.ModSpecialItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PowerGridTweaks.MOD_ID);

    public static final Supplier<CreativeModeTab> POWERGRIDTWEAKS =
            CREATIVE_TABS.register(
                    "powergridtweaks",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.powergridtweaks"))
                            .icon(() -> new ItemStack(ModItems.LITHIUM_CELL.get()))
                            .displayItems((parameters, output) -> {
                                acceptAll(output, ModSpecialItems.CREATIVE_TAB_ITEMS);
                                acceptAll(output, ModCircuitItems.CREATIVE_TAB_ITEMS);
                                acceptAll(output, ModBlockItems.CREATIVE_TAB_ITEMS);
                                acceptAll(output, ModMaterialItems.CREATIVE_TAB_ITEMS);
                            })
                            .build()
            );

    private ModCreativeTabs() {
    }

    private static void acceptAll(CreativeModeTab.Output output, List<DeferredItem<? extends Item>> items) {
        items.forEach(item -> output.accept(item.get()));
    }
}
