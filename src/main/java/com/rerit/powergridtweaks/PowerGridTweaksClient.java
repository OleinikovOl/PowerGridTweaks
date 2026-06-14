package com.rerit.powergridtweaks;

import com.rerit.powergridtweaks.ponder.PowerGridTweaksPonderPlugin;
import com.rerit.powergridtweaks.registry.ModBlockEntities;
import com.rerit.powergridtweaks.registry.ModBlocks;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.model.ModelSwapper;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;

public class PowerGridTweaksClient {
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(PowerGridTweaksClient::onClientSetup);
        modEventBus.addListener(PowerGridTweaksClient::onRegisterRenderers);
        modEventBus.addListener(PowerGridTweaksClient::onModifyBakingResult);

        NeoForge.EVENT_BUS.addListener(PowerGridTweaksClient::onItemTooltip);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            PonderIndex.addPlugin(new PowerGridTweaksPonderPlugin());
            SimpleBlockEntityVisualizer
                    .builder(ModBlockEntities.SHAFT_GENERATOR.get())
                    .factory(SingleAxisRotatingVisual::shaft)
                    .apply();
        });
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.SHAFT_GENERATOR.get(), ShaftRenderer::new);
    }

    private static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        LithiumBatteryCTBehaviour behaviour = new LithiumBatteryCTBehaviour();
        ModelSwapper.swapModels(
                event.getModels(),
                ModelSwapper.getAllBlockStateModelLocations(ModBlocks.LITHIUM_BATTERY.get()),
                model -> new CTModel(model, behaviour)
        );
    }

    private static void onItemTooltip(ItemTooltipEvent event) {
        IHaveElectricProperties properties = null;

        if (event.getItemStack().getItem() instanceof IHaveElectricProperties itemProperties) {
            properties = itemProperties;
        } else if (event.getItemStack().getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() instanceof IHaveElectricProperties blockProperties) {
            properties = blockProperties;
        }

        if (properties == null) {
            return;
        }

        Player player = event.getEntity();

        if (!properties.alwaysDisplay() && !Screen.hasShiftDown()) {
            event.getToolTip().add(
                    Component.translatable(
                            "powergrid.tooltip.holdForDescription",
                            Component.literal("Shift").withStyle(ChatFormatting.WHITE)
                    ).withStyle(ChatFormatting.DARK_GRAY)
            );
            return;
        }

        if (player == null) {
            return;
        }

        properties.appendProperties(
                event.getItemStack(),
                player,
                event.getToolTip()
        );
    }
}
