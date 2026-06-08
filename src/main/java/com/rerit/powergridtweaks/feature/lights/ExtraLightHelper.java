package com.rerit.powergridtweaks.feature.lights;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlock;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlockEntity;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class ExtraLightHelper {
    private static final Direction[] DIRECTIONS = Direction.values();

    private static boolean updatingExtraLights = false;

    public static boolean isUpdatingExtraLights() {
        return updatingExtraLights;
    }

    public static void update(ServerLevel level, BlockPos lampPos, BlockState lampState) {
        if (!PowerGridTweaksConfig.strongerLightsEnabled()) {
            remove(level, lampPos);
            return;
        }

        remove(level, lampPos);

        if (!(lampState.getBlock() instanceof LightFixtureBlock)
                || lampState.getValue(LightFixtureBlock.POWER) != 2
                || !hasHighVoltageBulb(level, lampPos)) {
            return;
        }

        place(level, lampPos);
    }

    private static boolean hasHighVoltageBulb(ServerLevel level, BlockPos lampPos) {
        BlockEntity blockEntity = level.getBlockEntity(lampPos);

        if (!(blockEntity instanceof LightFixtureBlockEntity fixture)) {
            return false;
        }

        var bulbState = fixture.getBulbState();

        if (bulbState == null || bulbState.isBurned()) {
            return false;
        }

        return bulbState.getItem() == ModItems.HIGH_VOLTAGE_LIGHT_BULB.get();
    }

    public static void refreshNearbyFixtures(ServerLevel level, BlockPos changedPos) {
        if (!PowerGridTweaksConfig.strongerLightsEnabled()) {
            return;
        }

        int radius = PowerGridTweaksConfig.strongerLightsRadius();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            int remainingYz = radius - Math.abs(dx);
            for (int dy = -remainingYz; dy <= remainingYz; dy++) {
                int remainingZ = remainingYz - Math.abs(dy);
                for (int dz = -remainingZ; dz <= remainingZ; dz++) {
                    pos.set(changedPos.getX() + dx, changedPos.getY() + dy, changedPos.getZ() + dz);

                    if (!level.isLoaded(pos)) {
                        continue;
                    }

                    BlockState state = level.getBlockState(pos);

                    if (state.getBlock() instanceof LightFixtureBlock) {
                        update(level, pos.immutable(), state);
                    }
                }
            }
        }
    }

    public static void place(ServerLevel level, BlockPos lampPos) {
        if (updatingExtraLights) {
            return;
        }

        updatingExtraLights = true;
        try {
            int radius = PowerGridTweaksConfig.strongerLightsRadius();
            int lightLevel = PowerGridTweaksConfig.strongerLightsLevel();

            Set<Long> visited = new HashSet<>();
            Queue<LightNode> queue = new ArrayDeque<>();

            visited.add(lampPos.asLong());
            queue.add(new LightNode(lampPos, 0));

            while (!queue.isEmpty()) {
                LightNode currentNode = queue.poll();
                BlockPos current = currentNode.pos();
                int distance = currentNode.distance();

                for (Direction direction : DIRECTIONS) {
                    BlockPos next = current.relative(direction);
                    long nextKey = next.asLong();

                    if (visited.contains(nextKey)) {
                        continue;
                    }

                    int nextDistance = distance + 1;

                    if (nextDistance > radius) {
                        continue;
                    }

                    if (!level.isLoaded(next)) {
                        continue;
                    }

                    BlockState state = level.getBlockState(next);

                    if (!canLightPassThrough(state)) {
                        continue;
                    }

                    visited.add(nextKey);
                    queue.add(new LightNode(next, nextDistance));

                    if (!next.equals(lampPos) && state.isAir()) {
                        level.setBlock(
                                next,
                                Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, lightLevel),
                                Block.UPDATE_CLIENTS
                        );
                    }
                }
            }
        } finally {
            updatingExtraLights = false;
        }
    }

    public static void remove(ServerLevel level, BlockPos lampPos) {
        if (updatingExtraLights) {
            return;
        }

        updatingExtraLights = true;
        try {
            int radius = PowerGridTweaksConfig.strongerLightsRadius();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int dx = -radius; dx <= radius; dx++) {
                int remainingYz = radius - Math.abs(dx);
                for (int dy = -remainingYz; dy <= remainingYz; dy++) {
                    int remainingZ = remainingYz - Math.abs(dy);
                    for (int dz = -remainingZ; dz <= remainingZ; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }

                        pos.set(lampPos.getX() + dx, lampPos.getY() + dy, lampPos.getZ() + dz);

                        if (!level.isLoaded(pos)) {
                            continue;
                        }

                        BlockState state = level.getBlockState(pos);

                        if (state.is(Blocks.LIGHT)) {
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                        }
                    }
                }
            }
        } finally {
            updatingExtraLights = false;
        }
    }

    public static boolean affectsExtraLight(BlockState oldState, BlockState newState) {
        return canLightPassThrough(oldState) != canLightPassThrough(newState);
    }

    private static boolean canLightPassThrough(BlockState state) {
        return state.isAir() || state.is(Blocks.LIGHT);
    }

    private record LightNode(BlockPos pos, int distance) {
    }
}
