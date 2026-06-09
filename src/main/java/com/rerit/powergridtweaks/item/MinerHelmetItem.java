package com.rerit.powergridtweaks.item;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;

import java.util.List;

public class MinerHelmetItem extends ArmorItem implements FePoweredItem {
    private static final int EFFECT_DURATION_TICKS = 260;
    private static final int ENERGY_TICK_INTERVAL = 20;
    private static final int RECHARGE_TICK_INTERVAL = 5;

    public MinerHelmetItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.HELMET, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        if (level.isClientSide
                || !(entity instanceof Player player)
                || player.getItemBySlot(EquipmentSlot.HEAD) != stack) {
            return;
        }

        if (player.tickCount % RECHARGE_TICK_INTERVAL == 0) {
            rechargeFromPortableBattery(stack, player);
        }

        if (PowerGridTweaksConfig.minerHelmetEnabled()
                && player.tickCount % ENERGY_TICK_INTERVAL == 0
                && consumeEnergy(stack, player, PowerGridTweaksConfig.minerHelmetEnergyPerSecond())) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    EFFECT_DURATION_TICKS,
                    0,
                    true,
                    false,
                    true
            ));
        }
    }

    @Override
    public int energyCapacity(ItemStack stack) {
        return PowerGridTweaksConfig.minerHelmetCapacity();
    }

    @Override
    public int maxReceive(ItemStack stack) {
        return PowerGridTweaksConfig.minerHelmetMaxReceive();
    }

    @Override
    public int maxExtract(ItemStack stack) {
        return PowerGridTweaksConfig.minerHelmetMaxExtract();
    }

    @Override
    public int batteryRechargeBatch(ItemStack stack) {
        return PowerGridTweaksConfig.minerHelmetBatteryRechargeBatch();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return FePoweredItem.super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return FePoweredItem.super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        appendEnergyTooltip(stack, tooltip);
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
