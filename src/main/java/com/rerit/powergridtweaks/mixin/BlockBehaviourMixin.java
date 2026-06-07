package com.rerit.powergridtweaks.mixin;

import com.rerit.powergridtweaks.feature.lights.ExtraLightHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "onPlace", at = @At("TAIL"))
    private void powergridtweaks$onPlace(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState oldState,
            boolean movedByPiston,
            CallbackInfo ci
    ) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (ExtraLightHelper.isUpdatingExtraLights()) return;
        if (state.is(Blocks.LIGHT)) return;

        if ((Object) this instanceof LightFixtureBlock) {
            ExtraLightHelper.update(serverLevel, pos, state);
        } else {
            ExtraLightHelper.refreshNearbyFixtures(serverLevel, pos);
        }
    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    private void powergridtweaks$onRemove(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState newState,
            boolean movedByPiston,
            CallbackInfo ci
    ) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (ExtraLightHelper.isUpdatingExtraLights()) return;
        if (state.is(Blocks.LIGHT)) return;

        if ((Object) this instanceof LightFixtureBlock) {
            ExtraLightHelper.remove(serverLevel, pos);
        } else {
            ExtraLightHelper.refreshNearbyFixtures(serverLevel, pos);
        }
    }
}
