package com.rerit.powergridtweaks.feature.lights;

import com.rerit.powergridtweaks.config.PowerGridTweaksConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlock;
import com.rerit.powergridtweaks.registry.ModItems;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.patryk3211.powergrid.electricity.light.fixture.LightFixtureBlockEntity;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class ExtraLightHelper {
    private static boolean updatingExtraLights = false;

    public static boolean isUpdatingExtraLights() {
        return updatingExtraLights;
    }

    public static void update(ServerLevel level, BlockPos lampPos, BlockState lampState) {
        remove(level, lampPos);

        if (!PowerGridTweaksConfig.strongerLightsEnabled()) {
            return;
        }

        if (lampState.getBlock() instanceof LightFixtureBlock
                && lampState.getValue(LightFixtureBlock.POWER) == 2
                && hasHighVoltageBulb(level, lampPos)) {
            place(level, lampPos);
        }
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
        int radius = PowerGridTweaksConfig.strongerLightsRadius();

        for (BlockPos pos : BlockPos.betweenClosed(
                changedPos.offset(-radius, -radius, -radius),
                changedPos.offset(radius, radius, radius)
        )) {
            BlockState state = level.getBlockState(pos);

            if (state.getBlock() instanceof LightFixtureBlock) {
                update(level, pos.immutable(), state);
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

            Set<BlockPos> visited = new HashSet<>();
            Queue<LightNode> queue = new ArrayDeque<>();

            visited.add(lampPos);
            queue.add(new LightNode(lampPos, 0));

            while (!queue.isEmpty()) {
                LightNode currentNode = queue.poll();
                BlockPos current = currentNode.pos();
                int distance = currentNode.distance();

                for (BlockPos next : neighbors(current)) {
                    BlockPos immutableNext = next.immutable();

                    if (visited.contains(immutableNext)) {
                        continue;
                    }

                    int nextDistance = distance + 1;

                    if (nextDistance > radius) {
                        continue;
                    }

                    BlockState state = level.getBlockState(immutableNext);

                    if (!canLightPassThrough(state)) {
                        continue;
                    }

                    visited.add(immutableNext);
                    queue.add(new LightNode(immutableNext, nextDistance));

                    if (!immutableNext.equals(lampPos) && state.isAir()) {
                        level.setBlock(
                                immutableNext,
                                Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, lightLevel),
                                3
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

            for (BlockPos pos : BlockPos.betweenClosed(
                    lampPos.offset(-radius, -radius, -radius),
                    lampPos.offset(radius, radius, radius)
            )) {
                if (pos.equals(lampPos)) continue;
                if (pos.distManhattan(lampPos) > radius) continue;

                BlockState state = level.getBlockState(pos);

                if (state.is(Blocks.LIGHT)) {
                    level.removeBlock(pos, false);
                }
            }
        } finally {
            updatingExtraLights = false;
        }
    }

    private static boolean canLightPassThrough(BlockState state) {
        return state.isAir() || state.is(Blocks.LIGHT);
    }

    private record LightNode(BlockPos pos, int distance) {
    }

    private static BlockPos[] neighbors(BlockPos pos) {
        return new BlockPos[] {
                pos.above(),
                pos.below(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        };
    }
}
