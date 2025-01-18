// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer;

import antikyth.taiao.HexaFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A trunk splitting in a '┗┳┛' shape, supporting {@link ConnectingBlock}s for the trunk.
 */
public class ThinSplittingTrunkPlacer extends TrunkPlacer {
    public static final Codec<ThinSplittingTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillTrunkPlacerFields(instance).and(
                    instance.group(
                            IntProvider.POSITIVE_CODEC.fieldOf("first_split_minimum_height")
                                    .forGetter(placer -> placer.firstSplitMinimumHeight),
                            Codec.floatRange(0f, 1f)
                                    .fieldOf("split_trunk_per_log_probability")
                                    .forGetter(placer -> placer.splitTrunkPerLogProbability),
                            SplitTypeWeights.CODEC.fieldOf("split_type_weights")
                                    .forGetter(placer -> placer.splitTypeWeights)
                    )
            ).apply(instance, ThinSplittingTrunkPlacer::new)
    );

    /**
     * The minimum trunk height that must be reached before a split is allowed to take place.
     */
    private final IntProvider firstSplitMinimumHeight;
    /**
     * The probability to split the trunk for any given vertical log placed.
     */
    private final float splitTrunkPerLogProbability;
    /**
     *
     */
    private final SplitTypeWeights splitTypeWeights;

    /**
     * Creates a new {@link ThinSplittingTrunkPlacer}.
     *
     * @param baseHeight                  the minimum trunk height
     * @param firstRandomHeight           the upper bound of the first random amount to add to the trunk's height
     * @param secondRandomHeight          the upper bound of the second random amount to add to the trunk's height
     * @param firstSplitMinimumHeight     the minimum trunk height that must be reached before a split is allowed to
     *                                    take place
     * @param splitTrunkPerLogProbability the probability to split the trunk for any given vertical log placed above the
     *                                    {@code firstSplitMinimumHeight}
     * @param splitTypeWeights            the weighting for randomly choosing each split's {@link SplitType}
     */
    public ThinSplittingTrunkPlacer(
            int baseHeight,
            int firstRandomHeight,
            int secondRandomHeight,
            IntProvider firstSplitMinimumHeight,
            float splitTrunkPerLogProbability,
            SplitTypeWeights splitTypeWeights
    ) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);

        this.firstSplitMinimumHeight = firstSplitMinimumHeight;
        this.splitTrunkPerLogProbability = splitTrunkPerLogProbability;
        this.splitTypeWeights = splitTypeWeights;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TaiaoTreePlacers.THIN_SPLITTING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            @NotNull Random random,
            int height,
            @NotNull BlockPos startPos,
            TreeFeatureConfig config
    ) {
        int minFirstSplitHeight = Math.min(this.firstSplitMinimumHeight.get(random), height);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // Make a straight trunk up to the minimum height of the first split.
        for (int i = 0; i < minFirstSplitHeight; i++) {
            mutable.set(startPos, 0, i, 0);

            placeVerticalLog(world, replacer, random, mutable, config);
        }

        // random starting direction
        Direction splitDirection = Direction.Type.HORIZONTAL.random(random);
        int splitsRemaining = 2;

        return createSegment(
                world,
                replacer,
                random,
                mutable.up(),
                config,
                height - minFirstSplitHeight,
                splitDirection,
                splitsRemaining
        );
    }

    /**
     * Rotates the given {@code axis} around the {@linkplain Direction.Axis#Y Y axis}.
     */
    @Contract(pure = true)
    private static Direction.Axis rotateAxis(Direction.@NotNull Axis axis) {
        return switch (axis) {
            case X -> Direction.Axis.Z;
            case Z -> Direction.Axis.X;
            case Y -> Direction.Axis.Y;
        };
    }

    /**
     * Chooses a random direction in the given {@code axis}.
     */
    private static Direction randomDirectionFromAxis(@NotNull Random random, Direction.Axis axis) {
        Direction.AxisDirection randomDirection = Direction.AxisDirection.values()[random.nextInt(Direction.AxisDirection.values().length)];

        return Direction.from(axis, randomDirection);
    }

    /**
     * Recursively grows segments of tree
     * Grows segments of the trunk, recursing at each split.
     *
     * @param startPos        the position to start this segment from (inclusive)
     * @param height          the height this particular segment must grow to
     * @param splitDirection  the starting split direction (for the next split)
     * @param splitsRemaining the remaining number of splits allowed to take place
     * @return foliage nodes at the top of each branch
     */
    private List<FoliagePlacer.TreeNode> createSegment(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            @NotNull Random random,
            @NotNull BlockPos startPos,
            TreeFeatureConfig config,
            int height,
            Direction splitDirection,
            int splitsRemaining
    ) {
        List<FoliagePlacer.TreeNode> foliagePositions = new ArrayList<>();

        BlockPos.Mutable mutable = new BlockPos.Mutable().set(startPos);

        for (int i = 0; i < height; i++) {
            mutable.set(startPos, 0, i, 0);

            if (splitsRemaining > 0 && random.nextFloat() < this.splitTrunkPerLogProbability) {
                // Create a new split.
                List<BlockPos> branches = createSplit(
                        world,
                        replacer,
                        random,
                        mutable,
                        config,
                        this.splitTypeWeights.next(random),
                        splitDirection
                );

                // One of the split branches will grow to the maximum height, the others will not; this is the index of
                // that maximum height branch.
                int maxHeightBranchIndex = random.nextInt(branches.size());
                int maxBranchHeight = height - i - 1;

                for (int branchIndex = 0; branchIndex < branches.size(); branchIndex++) {
                    // If this is the maximum height branch, use the maximum height, otherwise randomise it.
                    int branchHeight = (maxBranchHeight > 0 && branchIndex != maxHeightBranchIndex)
                            ? random.nextInt(maxBranchHeight)
                            : maxBranchHeight;

                    // Flip the axis and choose a new random direction in that axis for the next split to face.
                    Direction.Axis newAxis = rotateAxis(splitDirection.getAxis());
                    Direction newDirection = randomDirectionFromAxis(random, newAxis);

                    // Recursively create segments for each branch, collecting all their foliage nodes.
                    foliagePositions.addAll(createSegment(
                            world,
                            replacer,
                            random,
                            branches.get(branchIndex).up(),
                            config,
                            branchHeight,
                            newDirection,
                            splitsRemaining - 1
                    ));
                }

                return foliagePositions;
            } else {
                // Place log
                placeVerticalLog(world, replacer, random, mutable, config);
            }
        }

        foliagePositions.add(new FoliagePlacer.TreeNode(startPos.up(height), 1, false));

        return foliagePositions;
    }

    /**
     * Places logs to form a split in the trunk.
     *
     * @param center    the position the trunk is splitting from
     * @param splitType the shape of the split
     * @param direction the direction that the split faces;
     *                  for {@link SplitType#Centered Centered} and {@link SplitType#Triple Triple}, only the
     *                  {@link Direction.Axis} matters
     * @return the positions above which new branches/trunk should be generated
     */
    private @NotNull @Unmodifiable List<BlockPos> createSplit(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos center,
            TreeFeatureConfig config,
            @NotNull SplitType splitType,
            @NotNull Direction direction
    ) {
        TriFunction<BlockPos, BlockRotation, HexaFunction<
                TestableWorld,
                BiConsumer<BlockPos, BlockState>,
                Random,
                BlockPos,
                TreeFeatureConfig,
                BlockRotation,
                Boolean>, Boolean> place = (pos, rotation, placementFunction) -> placementFunction.accept(
                world,
                replacer,
                random,
                pos,
                config,
                rotation
        );

        BlockPos.Mutable mutable = new BlockPos.Mutable().set(center);

        Direction oppositeDirection = direction.getOpposite();
        BlockRotation rotation = rotationFromDirection(direction);
        BlockRotation oppositeRotation = rotation.rotate(BlockRotation.CLOCKWISE_180);

        return switch (splitType) {
            case Centered -> {
                // Center
                place.apply(center, rotation, this::placeHorizontalT);

                // First bend
                BlockPos first = center.offset(direction);
                place.apply(first, rotation, this::placeBend);

                // Second bend
                BlockPos second = center.offset(oppositeDirection);
                place.apply(second, oppositeRotation, this::placeBend);

                yield List.of(first, second);
            }
            case Hanging -> {
                // Center
                place.apply(mutable, rotation, this::placeVerticalT);

                // Arm
                mutable.move(direction);
                place.apply(mutable, rotation, this::placeHorizontalLog);

                // Bend
                mutable.move(direction);
                place.apply(mutable, rotation, this::placeBend);

                yield List.of(center, mutable.toImmutable());
            }
            case Triple -> {
                // Center
                place.apply(mutable, rotation, this::placeVerticalCross);

                // First arm
                mutable.move(direction);
                place.apply(mutable, rotation, this::placeHorizontalLog);
                // First bend
                BlockPos first = mutable.move(direction).toImmutable();
                place.apply(first, rotation, this::placeBend);

                // Second arm
                mutable.set(center, oppositeDirection);
                place.apply(mutable, oppositeRotation, this::placeHorizontalLog);
                // Second bend
                BlockPos second = mutable.move(oppositeDirection).toImmutable();
                place.apply(second, oppositeRotation, this::placeBend);

                yield List.of(first, center, second);
            }
        };
    }

    /**
     * Gives the {@link BlockRotation} necessary to rotate from {@link Direction#NORTH} to the given
     * {@code horizontalDirection}.
     *
     * @throws IllegalArgumentException if a non-horizontal direction is provided
     */
    private static BlockRotation rotationFromDirection(@NotNull Direction horizontalDirection) {
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
    private boolean placeVerticalLog(
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
    private boolean placeHorizontalLog(
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
    private boolean placeBend(
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
    private boolean placeHorizontalT(
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
    private boolean placeVerticalT(
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
    private boolean placeVerticalCross(
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
     * Weights for randomly deciding which {@link SplitType} to use.
     */
    public static class SplitTypeWeights {
        protected final int centeredWeight;
        protected final int hangingWeight;
        protected final int tripleWeight;

        protected final int totalWeight;

        public static final Codec<ThinSplittingTrunkPlacer.SplitTypeWeights> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.intRange(0, Integer.MAX_VALUE).fieldOf("centered").forGetter(weights -> weights.centeredWeight),
                Codec.intRange(0, Integer.MAX_VALUE).fieldOf("hanging").forGetter(weights -> weights.hangingWeight),
                Codec.intRange(0, Integer.MAX_VALUE).fieldOf("triple").forGetter(weights -> weights.tripleWeight)
        ).apply(instance, SplitTypeWeights::new));

        /**
         * @param centeredWeight the weighting for a {@linkplain SplitType#Centered centered split shape}
         * @param hangingWeight  the weighting for a {@linkplain SplitType#Hanging hanging split shape}
         * @param tripleWeight   the weighting for a {@linkplain SplitType#Triple triple split shape}
         */
        public SplitTypeWeights(int centeredWeight, int hangingWeight, int tripleWeight) {
            this.centeredWeight = centeredWeight;
            this.hangingWeight = hangingWeight;
            this.tripleWeight = tripleWeight;

            this.totalWeight = centeredWeight + hangingWeight + tripleWeight;
        }

        /**
         * Randomly chooses a {@link SplitType} based on these weightings.
         */
        public SplitType next(@NotNull Random random) {
            int value = random.nextInt(this.totalWeight);

            if (value < this.centeredWeight) return SplitType.Centered;
            else if (value - this.centeredWeight < this.hangingWeight) return SplitType.Hanging;
            else return SplitType.Triple;
        }
    }

    /**
     * The shape of a split in the trunk.
     */
    public enum SplitType {
        /**
         * A '┗┳┛' shape.
         */
        Centered,
        /**
         * A '┣━┛' shape.
         */
        Hanging,
        /**
         * A '┗━╋━┛' shape.
         */
        Triple
    }
}
