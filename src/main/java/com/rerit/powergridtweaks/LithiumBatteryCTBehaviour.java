package com.rerit.powergridtweaks;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class LithiumBatteryCTBehaviour extends ConnectedTextureBehaviour.Base {
    private static final CTSpriteShiftEntry TOP_BOTTOM = CTSpriteShifter.getCT(
            AllCTTypes.RECTANGLE,
            texture("lithium_battery_top"),
            texture("lithium_battery_top_connected")
    );
    private static final CTSpriteShiftEntry SIDES = CTSpriteShifter.getCT(
            AllCTTypes.RECTANGLE,
            texture("lithium_battery_side"),
            texture("lithium_battery_side_connected")
    );

    @Override
    public CTSpriteShiftEntry getShift(BlockState state, Direction direction, TextureAtlasSprite sprite) {
        return direction.getAxis() == Direction.Axis.Y ? TOP_BOTTOM : SIDES;
    }

    @Override
    public boolean connectsTo(
            BlockState state,
            BlockState other,
            BlockAndTintGetter level,
            BlockPos pos,
            BlockPos otherPos,
            Direction face
    ) {
        return state == other && ConnectivityHandler.isConnected(level, pos, otherPos);
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "block/" + name);
    }
}
