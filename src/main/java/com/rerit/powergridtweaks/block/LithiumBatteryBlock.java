package com.rerit.powergridtweaks.block;

import com.rerit.powergridtweaks.battery.LithiumBatterySpec;
import com.rerit.powergridtweaks.block.entity.LithiumBatteryBlockEntity;
import com.rerit.powergridtweaks.registry.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.patryk3211.powergrid.electricity.battery.BatteryBlock;
import org.patryk3211.powergrid.electricity.battery.MultiBlockBatteryEntity;
import org.patryk3211.powergrid.electricity.info.Power;
import org.patryk3211.powergrid.electricity.info.Voltage;

import java.util.List;

public class LithiumBatteryBlock extends BatteryBlock {
    public LithiumBatteryBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.spec = LithiumBatterySpec.INSTANCE;
    }

    @Override
    public Class<MultiBlockBatteryEntity> getBlockEntityClass() {
        return MultiBlockBatteryEntity.class;
    }

    @Override
    public BlockEntityType<? extends LithiumBatteryBlockEntity> getBlockEntityType() {
        return ModBlockEntities.LITHIUM_BATTERY.get();
    }

    @Override
    public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
        Voltage.max(LithiumBatterySpec.MAX_VOLTAGE, player, tooltip);
        Power.rated(LithiumBatterySpec.RATED_POWER, player, tooltip);
    }
}
