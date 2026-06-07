package com.rerit.powergridtweaks.mixin;

import com.rerit.powergridtweaks.feature.lights.ExtraLightHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightFixtureBlock.class)
public abstract class LightFixtureBlockMixin {
    @Inject(method = "destroy", at = @At("HEAD"))
    private void powergridtweaks$destroy(
            LevelAccessor level,
            BlockPos pos,
            BlockState state,
            CallbackInfo ci
    ) {
        if (level instanceof ServerLevel serverLevel) {
            ExtraLightHelper.remove(serverLevel, pos);
        }
    }
}
