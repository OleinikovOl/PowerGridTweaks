package com.rerit.powergridtweaks.block;

import com.rerit.powergridtweaks.block.entity.ShaftGeneratorBlockEntity;
import com.rerit.powergridtweaks.registry.ModBlockEntities;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.patryk3211.powergrid.electricity.base.DirectionalElectricBlock;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.electricity.info.Power;
import org.patryk3211.powergrid.electricity.info.Voltage;
import org.patryk3211.powergrid.kinetics.base.ElectricKineticBlock;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ShaftGeneratorBlock extends ElectricKineticBlock
        implements IBE<ShaftGeneratorBlockEntity>, IHaveElectricProperties {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(3.0, 3.0, 0.5, 13.0, 13.0, 15.5),
            Block.box(2.5, 2.5, 10.5, 13.5, 13.5, 15.5));
    private static final VoxelShape UP_SHAPE = Shapes.or(
            Block.box(3.0, 0.5, 3.0, 13.0, 15.5, 13.0),
            Block.box(2.5, 0.5, 2.5, 13.5, 5.5, 13.5));

    private static final TerminalBoundingBox POSITIVE_NORTH_TERMINAL =
            new TerminalBoundingBox(Component.literal("+"), 5.0, 13.0, 14.0, 7.0, 14.0, 16.0)
                    .withColor(0xD65A31);
    private static final TerminalBoundingBox NEGATIVE_NORTH_TERMINAL =
            new TerminalBoundingBox(Component.literal("-"), 9.0, 13.0, 14.0, 11.0, 14.0, 16.0)
                    .withColor(0x3E78B2);
    private static final TerminalBoundingBox[] NORTH_TERMINALS = {
            POSITIVE_NORTH_TERMINAL,
            NEGATIVE_NORTH_TERMINAL
    };

    public ShaftGeneratorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.UP));
        setTerminalCollection(DirectionalElectricBlock.directionalNorthTerminals(
                this,
                NORTH_TERMINALS,
                NORTH_SHAPE,
                UP_SHAPE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferredFacing = getPreferredFacing(context);
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();

        if (preferredFacing != null && !sneaking) {
            return defaultBlockState().setValue(FACING, preferredFacing);
        }

        Direction lookingDirection = context.getNearestLookingDirection();
        return defaultBlockState().setValue(FACING, sneaking
                ? lookingDirection
                : lookingDirection.getOpposite());
    }

    private Direction getPreferredFacing(BlockPlaceContext context) {
        Direction preferredFacing = null;
        boolean hasMultipleShaftsOnAxis = false;

        for (Direction side : Direction.values()) {
            BlockPos neighborPos = context.getClickedPos().relative(side);
            BlockState neighborState = context.getLevel().getBlockState(neighborPos);

            if (!(neighborState.getBlock() instanceof com.simibubi.create.content.kinetics.base.IRotate rotate)) {
                continue;
            }

            if (!rotate.hasShaftTowards(context.getLevel(), neighborPos, neighborState, side.getOpposite())) {
                continue;
            }

            if (preferredFacing != null && preferredFacing.getAxis() != side.getAxis()) {
                return null;
            }

            hasMultipleShaftsOnAxis = preferredFacing != null && preferredFacing != side;
            if (preferredFacing == null) {
                preferredFacing = side;
            }
        }

        if (preferredFacing == null) {
            return null;
        }

        Direction clickedFace = context.getClickedFace();
        if (hasMultipleShaftsOnAxis && clickedFace.getAxis() == preferredFacing.getAxis()) {
            return clickedFace;
        }

        return preferredFacing;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(FACING).getAxis();
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public Class<ShaftGeneratorBlockEntity> getBlockEntityClass() {
        return ShaftGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ShaftGeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SHAFT_GENERATOR.get();
    }

    @Override
    public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
        Voltage.rated(ShaftGeneratorBlockEntity.NOMINAL_VOLTAGE, player, tooltip);
        Power.rated(ShaftGeneratorBlockEntity.RATED_OUTPUT_POWER, player, tooltip);
    }
}
