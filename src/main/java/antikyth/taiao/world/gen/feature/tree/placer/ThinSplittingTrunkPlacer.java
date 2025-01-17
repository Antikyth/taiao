// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
                                    .forGetter(placer -> placer.splitTrunkPerLogProbability)
                    )
            ).apply(instance, ThinSplittingTrunkPlacer::new)
    );

    private final IntProvider firstSplitMinimumHeight;
    private final float splitTrunkPerLogProbability;

    public ThinSplittingTrunkPlacer(
            int baseHeight,
            int firstRandomHeight,
            int secondRandomHeight,
            IntProvider firstSplitMinimumHeight,
            float splitTrunkPerLogProbability
    ) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);

        this.firstSplitMinimumHeight = firstSplitMinimumHeight;
        this.splitTrunkPerLogProbability = splitTrunkPerLogProbability;
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
        // random starting axis
        Axis axis = Axis.values()[random.nextInt(Axis.values().length)];
        int splitsRemaining = 2;

        int minFirstSplitHeight = this.firstSplitMinimumHeight.get(random);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // Make a straight trunk up to the minimum height of the first split.
        for (int i = 0; i < Math.min(height, minFirstSplitHeight); i++) {
            mutable.set(startPos, 0, i, 0);

            placeStraightLog(world, replacer, random, mutable, config);
        }

        return createSegment(
                world,
                replacer,
                random,
                height - minFirstSplitHeight,
                splitsRemaining,
                axis,
                startPos.up(minFirstSplitHeight),
                config
        );
    }

    private List<FoliagePlacer.TreeNode> createSegment(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            @NotNull Random random,
            int height,
            int splitsRemaining,
            Axis axis,
            @NotNull BlockPos startPos,
            TreeFeatureConfig config
    ) {
        List<FoliagePlacer.TreeNode> foliagePositions = Lists.newArrayList();

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int i = 0; i < height; i++) {
            mutable.set(startPos, 0, i, 0);

            if (splitsRemaining > 0 && random.nextFloat() < this.splitTrunkPerLogProbability) {
                List<BlockPos> branches = createSplit(world, replacer, random, mutable, config, axis);

                // One of the split branches will grow to the maximum height, the others will not; this is the index of
                // that maximum height branch.
                int maxHeightBranchIndex = random.nextInt(branches.size());
                int maxBranchHeight = height - i - 1;

                for (int branchIndex = 0; branchIndex < branches.size(); branchIndex++) {
                    // If this is the maximum height branch, use the maximum height, otherwise randomise it.
                    int branchHeight = (maxBranchHeight > 0 && branchIndex != maxHeightBranchIndex)
                            ? random.nextInt(maxBranchHeight)
                            : maxBranchHeight;

                    foliagePositions.addAll(createSegment(
                            world,
                            replacer,
                            random,
                            branchHeight,
                            splitsRemaining - 1,
                            axis.flip(),
                            branches.get(branchIndex).up(),
                            config
                    ));
                }

                return foliagePositions;
            } else {
                // Place log
                placeStraightLog(world, replacer, random, mutable, config);
            }
        }

        foliagePositions.add(new FoliagePlacer.TreeNode(startPos.up(height), 1, false));

        return foliagePositions;
    }

    private void placeStraightLog(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config
    ) {
        getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(Properties.DOWN, true).withIfExists(Properties.UP, true)
        );
    }

    private @NotNull @Unmodifiable List<BlockPos> createSplit(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            Axis axis
    ) {
        // Center piece
        this.getAndSetState(
                world,
                replacer,
                random,
                pos,
                config,
                state -> state.withIfExists(ConnectingBlock.DOWN, true)
                        .withIfExists(axis == Axis.X ? ConnectingBlock.NORTH : ConnectingBlock.EAST, true)
                        .withIfExists(axis == Axis.X ? ConnectingBlock.SOUTH : ConnectingBlock.WEST, true)
        );

        // First bend
        this.getAndSetState(
                world,
                replacer,
                random,
                axis == Axis.X ? pos.north() : pos.east(),
                config,
                state -> state.withIfExists(ConnectingBlock.UP, true)
                        .withIfExists(axis == Axis.X ? ConnectingBlock.SOUTH : ConnectingBlock.WEST, true)
        );
        // Second bend
        this.getAndSetState(
                world,
                replacer,
                random,
                axis == Axis.X ? pos.south() : pos.west(),
                config,
                state -> state.withIfExists(ConnectingBlock.UP, true)
                        .withIfExists(axis == Axis.X ? ConnectingBlock.NORTH : ConnectingBlock.EAST, true)
        );

        return List.of(axis == Axis.X ? pos.north() : pos.east(), axis == Axis.X ? pos.south() : pos.west());
    }

    private enum Axis {
        X,
        Z;

        public Axis flip() {
            return switch (this) {
                case X -> Axis.Z;
                case Z -> Axis.X;
            };
        }
    }
}
