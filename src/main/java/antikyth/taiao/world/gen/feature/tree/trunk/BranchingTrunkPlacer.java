// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.BiConsumer;

public class BranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillTrunkPlacerFields(instance).apply(instance, BranchingTrunkPlacer::new)
    );

    public BranchingTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TaiaoTrunkPlacers.BRANCHING_TRUNK_PLACER;
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
        List<FoliagePlacer.TreeNode> foliagePositions = Lists.newArrayList();
        // random starting axis
        Axis axis = Axis.values()[random.nextInt(Axis.values().length)];

        float splitChance = 0.8f;
        boolean split = random.nextFloat() < splitChance;
        int initialSegmentHeight = split ? random.nextBetween(1, 3) : height;

        createStraight(world, replacer, random, startPos, config, Math.min(height, initialSegmentHeight));

        if (initialSegmentHeight < height - 1) {
            for (BlockPos pos : createSplit(world, replacer, random, startPos.up(initialSegmentHeight), config, axis)) {
                int segmentHeight = random.nextBetween(1, height - initialSegmentHeight - 1);

                createStraight(world, replacer, random, pos.up(), config, segmentHeight);

                foliagePositions.add(new FoliagePlacer.TreeNode(pos.up(segmentHeight), 1, false));
            }
        }

        if (!split) foliagePositions.add(new FoliagePlacer.TreeNode(startPos.up(height - 1), 1, false));

        return foliagePositions;
    }

    private @Nullable @Unmodifiable List<BlockPos> createSegment(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            int height,
            float splitChance,
            Axis axis
    ) {
        boolean createSplit = random.nextFloat() < splitChance;
        int straightHeight = createSplit ? height - 1 : height;

        createStraight(world, replacer, random, pos, config, straightHeight);

        return createSplit ? createSplit(world, replacer, random, pos.up(straightHeight), config, axis) : null;
    }

    private void createStraight(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            BlockPos pos,
            TreeFeatureConfig config,
            int height
    ) {
        for (int i = 0; i < height; i++) {
            this.getAndSetState(
                    world,
                    replacer,
                    random,
                    pos.up(i),
                    config,
                    state -> state.withIfExists(ConnectingBlock.DOWN, true).withIfExists(ConnectingBlock.UP, true)
            );
        }
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
        Z
    }
}
