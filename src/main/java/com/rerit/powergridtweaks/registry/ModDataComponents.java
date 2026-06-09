package com.rerit.powergridtweaks.registry;

import com.mojang.serialization.Codec;
import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, PowerGridTweaks.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> ENERGY =
            DATA_COMPONENT_TYPES.register(
                    "energy",
                    () -> DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build()
            );
}
