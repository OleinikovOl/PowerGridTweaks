package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import com.rerit.powergridtweaks.block.entity.LithiumBatteryBlockEntity;
import com.rerit.powergridtweaks.block.entity.ShaftGeneratorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PowerGridTweaks.MOD_ID);

    public static final Supplier<BlockEntityType<ShaftGeneratorBlockEntity>> SHAFT_GENERATOR =
            BLOCK_ENTITIES.register(
                    "shaft_generator",
                    () -> BlockEntityType.Builder
                            .of(ShaftGeneratorBlockEntity::new, ModBlocks.SHAFT_GENERATOR.get())
                            .build(null)
            );

    public static final Supplier<BlockEntityType<LithiumBatteryBlockEntity>> LITHIUM_BATTERY =
            BLOCK_ENTITIES.register(
                    "lithium_battery",
                    () -> BlockEntityType.Builder
                            .of(LithiumBatteryBlockEntity::new, ModBlocks.LITHIUM_BATTERY.get())
                            .build(null)
            );
}
