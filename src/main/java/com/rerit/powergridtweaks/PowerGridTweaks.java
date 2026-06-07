package com.rerit.powergridtweaks;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import com.rerit.powergridtweaks.registry.ModCreativeTabs;
import com.rerit.powergridtweaks.registry.ModItems;
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
        ModItems.ITEMS.register(modEventBus);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            PowerGridTweaksClient.register(modEventBus);
        }

        modEventBus.addListener(ModCreativeTabs::addCreativeTabItems);

        modContainer.registerConfig(ModConfig.Type.COMMON, PowerGridTweaksConfig.SPEC);
    }
}
