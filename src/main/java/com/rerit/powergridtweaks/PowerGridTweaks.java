package com.rerit.powergridtweaks;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import com.rerit.powergridtweaks.registry.ModBlockEntities;
import com.rerit.powergridtweaks.registry.ModBlocks;
import com.rerit.powergridtweaks.registry.ModCreativeTabs;
import com.rerit.powergridtweaks.registry.ModItems;
import com.rerit.powergridtweaks.registry.ModThermalValues;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(PowerGridTweaks.MOD_ID)
public class PowerGridTweaks {
    public static final String MOD_ID = "powergridtweaks";

    public PowerGridTweaks(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModThermalValues.register();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            PowerGridTweaksClient.register(modEventBus);
        }

        modEventBus.addListener(ModCreativeTabs::addCreativeTabItems);

        modContainer.registerConfig(ModConfig.Type.COMMON, PowerGridTweaksConfig.SPEC);
    }
}
