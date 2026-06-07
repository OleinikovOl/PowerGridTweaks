package com.rerit.powergridtweaks.ponder;

import com.rerit.powergridtweaks.registry.ModItems;
import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.patryk3211.powergrid.PowerGrid;
import org.patryk3211.powergrid.ponder.scenes.DeviceScenes;

public class PowerGridTweaksPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return PowerGrid.MOD_ID; // важно: "powergrid", а не "powergridtweaks"
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModItems.HIGH_VOLTAGE_LIGHT_BULB.getId())
                .addStoryBoard(
                        ResourceLocation.fromNamespaceAndPath("powergrid", "lightbulb"),
                        DeviceScenes::light
                );
    }

    @Override
    public void onPonderLevelRestore(PonderLevel ponderLevel) {
    }
}
