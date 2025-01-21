// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.trunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public abstract class ThinTrunkPlacer extends TrunkPlacer {
    public ThinTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    /**
     * Gives the {@link BlockRotation} necessary to rotate from {@link Direction#NORTH} to the given
     * {@code horizontalDirection}.
     *
     * @throws IllegalArgumentException if a non-horizontal direction is provided
     */
    protected static BlockRotation rotationFromDirection(@NotNull Direction horizontalDirection) {
        return switch (horizontalDirection) {
            case NORTH -> BlockRotation.NONE;
            case EAST -> BlockRotation.CLOCKWISE_90;
            case SOUTH -> BlockRotation.CLOCKWISE_180;
            case WEST -> BlockRotation.COUNTERCLOCKWISE_90;

            default -> throw new IllegalArgumentException("expected horizontal direction");
        };
    }

    /**
     * Places a log {@link ConnectingBlock#UP UP} and {@link ConnectingBlock#DOWN DOWN} connections.
     * <p>
     * A '┃' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeVerticalLog(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true).withIfExists(ConnectingBlock.UP, true)
        );
    }

    /**
     * Places a straight log with horizontal connections, rotated from
     * {@link ConnectingBlock#NORTH NORTH}/{@link ConnectingBlock#SOUTH SOUTH} by the given {@code rotation}.
     * <p>
     * A '━' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be {@link Direction.Axis#Z}
     * rotated by the given {@code rotation}.
     */
    protected boolean placeHorizontalLog(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            BlockRotation rotation
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.NORTH, true)
                        .withIfExists(ConnectingBlock.SOUTH, true)
                        .withIfExists(PillarBlock.AXIS, Direction.Axis.Z)
                        .rotate(rotation)
        );
    }

    /**
     * Places a log with two connections, rotated from {@link ConnectingBlock#UP UP}/{@link ConnectingBlock#SOUTH SOUTH}
     * by the given {@code rotation}.
     * <p>
     * A '┗' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeBend(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            BlockRotation rotation
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.SOUTH, true)
                        .withIfExists(ConnectingBlock.UP, true)
                        .rotate(rotation)
        );
    }

    /**
     * Places a log with three connections, rotated from
     * {@link ConnectingBlock#NORTH NORTH}/{@link ConnectingBlock#DOWN DOWN}/{@link ConnectingBlock#SOUTH SOUTH} by the
     * given {@code rotation}.
     * <p>
     * A '┳' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeHorizontalT(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            BlockRotation rotation
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(ConnectingBlock.NORTH, true)
                        .withIfExists(ConnectingBlock.SOUTH, true)
                        .rotate(rotation)
        );
    }

    /**
     * Places a log with three connections, rotated from
     * {@link ConnectingBlock#DOWN DOWN}/{@link ConnectingBlock#NORTH NORTH}/{@link ConnectingBlock#UP UP} by the given
     * {@code rotation}.
     * <p>
     * A '┫' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeVerticalT(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            BlockRotation rotation
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(ConnectingBlock.UP, true)
                        .withIfExists(ConnectingBlock.NORTH, true)
                        .rotate(rotation)
        );
    }

    /**
     * Places a log with four connections, rotated from
     * {@link ConnectingBlock#DOWN DOWN}/{@link ConnectingBlock#NORTH NORTH}/{@link ConnectingBlock#UP UP}/{@link ConnectingBlock#SOUTH SOUTH}
     * by the given {@code rotation}.
     * <p>
     * A '╋' shape.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeVerticalCross(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            BlockRotation rotation
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(ConnectingBlock.UP, true)
                        .withIfExists(ConnectingBlock.NORTH, true)
                        .withIfExists(ConnectingBlock.SOUTH, true)
                        .rotate(rotation)
        );
    }

    /**
     * Places a log with all four horizontal connections and a {@link ConnectingBlock#DOWN DOWN} connection.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeHorizontalCrossTop(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(ConnectingBlock.NORTH, true)
                        .withIfExists(ConnectingBlock.EAST, true)
                        .withIfExists(ConnectingBlock.SOUTH, true)
                        .withIfExists(ConnectingBlock.WEST, true)
        );
    }

    /**
     * Places a log connected on all sides.
     * <p>
     * If this is a {@link PillarBlock} log instead, the {@link PillarBlock#AXIS} will be vertical.
     */
    protected boolean placeFullyConnectedLog(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config
    ) {
        return this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(ConnectingBlock.UP, true)
                        .withIfExists(ConnectingBlock.NORTH, true)
                        .withIfExists(ConnectingBlock.EAST, true)
                        .withIfExists(ConnectingBlock.SOUTH, true)
                        .withIfExists(ConnectingBlock.WEST, true)
        );
    }
}
