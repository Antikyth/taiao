// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.foliage;

import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public class PalmFoliagePlacer extends ThreeDimensionalFoliagePlacer {
    public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillFoliagePlacerFields(instance).apply(instance, PalmFoliagePlacer::new)
    );

    public PalmFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return TaiaoTreePlacers.PALM_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(
            TestableWorld world,
            BlockPlacer placer,
            Random random,
            TreeFeatureConfig config,
            int trunkHeight,
            @NotNull TreeNode treeNode,
            int foliageHeight,
            int radius,
            int offset
    ) {
        radius += treeNode.getFoliageRadius() - 1;
        // We intersect the surface of a larger sphere to get the palm frond shape.
        int largerRadius = radius + ceilDiv(radius, 3);
        BlockPos center = treeNode.getCenter().up(offset);

        boolean giantTrunk = treeNode.isGiantTrunk();
        int extra = giantTrunk ? 1 : 0;

        BlockPos.Mutable mutable = new BlockPos.Mutable().set(center);

        int boundingBoxHeight = radius - ceilDiv(radius, 4);

        for (int dy = 0; dy <= boundingBoxHeight; dy++) {
            // A plane intersecting in the X axis.
            for (int dx = -radius; dx <= radius + extra; dx++) {
                for (int dz = 0; dz < 1 + extra; dz++) {
                    if (isPositionValid3d(dx, dy + (largerRadius - boundingBoxHeight), dz, largerRadius, giantTrunk)) {
                        placeDirectionalLeaves(
                                world,
                                placer,
                                random,
                                config,
                                mutable,
                                center,
                                dx,
                                dy - boundingBoxHeight,
                                dz
                        );
                    }
                }
            }

            // A plane intersecting in the Z axis.
            for (int dz = -radius; dz <= radius + extra; dz++) {
                for (int dx = 0; dx < 1 + extra; dx++) {
                    if (isPositionValid3d(dx, dy + (largerRadius - boundingBoxHeight), dz, largerRadius, giantTrunk)) {
                        placeDirectionalLeaves(
                                world,
                                placer,
                                random,
                                config,
                                mutable,
                                center,
                                dx,
                                dy - boundingBoxHeight,
                                dz
                        );
                    }
                }
            }
        }
    }

    /**
     * Copied from Java 18+, since Java 17 is supported here.
     */
    protected static int ceilDiv(int x, int y) {
        final int q = x / y;
        // if the signs are the same and modulo not zero, round up
        if ((x ^ y) >= 0 && (q * y != x)) {
            return q + 1;
        }
        return q;
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    private boolean placeDirectionalLeaves(
            TestableWorld world,
            FoliagePlacer.BlockPlacer placer,
            Random random,
            TreeFeatureConfig config,
            @NotNull BlockPos.Mutable mutable,
            @NotNull BlockPos center,
            int dx,
            int dy,
            int dz
    ) {
        mutable.set(center, dx, dy, dz);

        return placeFoliageBlock(
                world, placer, random, config, mutable, state -> {
                    Direction.Axis axis;
                    Direction.AxisDirection axisDirection;

                    if (dx == 0 && dz == 0) {
                        // Y-axis - if no horizontal offset
                        axis = Direction.Axis.Y;

                        if (dy >= 0) axisDirection = Direction.AxisDirection.POSITIVE;
                        else axisDirection = Direction.AxisDirection.NEGATIVE;
                    } else if (Math.abs(dx) > Math.abs(dz)) {
                        // X-axis - if x offset is greater than z offset
                        axis = Direction.Axis.X;

                        if (dx < 0) axisDirection = Direction.AxisDirection.NEGATIVE;
                        else axisDirection = Direction.AxisDirection.POSITIVE;
                    } else {
                        // Z-axis - if z offset is greater than or equal to z offset
                        axis = Direction.Axis.Z;

                        if (dz < 0) axisDirection = Direction.AxisDirection.NEGATIVE;
                        else axisDirection = Direction.AxisDirection.POSITIVE;
                    }

                    return state.withIfExists(FacingBlock.FACING, Direction.from(axis, axisDirection));
                }
        );
    }

    @Override
    protected boolean isValidForLeaves3d(int dx, int dy, int dz, int radius) {
        if (dx != 0 && dz != 0) return false;

        int dHorizontal = dx == 0 ? dz : dx;

        return isCircleOutline(dHorizontal, dy, radius);
    }

    protected boolean isCircleOutline(int dx, int dy, int radius) {
        int innerRadius = radius - 1;

        BiFunction<Integer, Integer, Boolean> matches = (x, y) -> {
            int val = (x * x) + (y * y);

            return val >= (innerRadius * innerRadius) && val <= (radius * radius);
        };

        if (matches.apply(dx, dy)) return true;

        // We want to avoid diagonal-only connections, so if the block above and in front are valid, then we want to
        // connect them.
        return matches.apply(dx + 1, dy) && matches.apply(dx, dy + 1) && !matches.apply(dx + 1, dy + 1);
    }

    /**
     * Places a foliage block, applying the given {@code blockStateFunction} to the foliage block's state before
     * placing.
     */
    protected static boolean placeFoliageBlock(
            TestableWorld world,
            FoliagePlacer.BlockPlacer placer,
            Random random,
            TreeFeatureConfig config,
            BlockPos pos,
            Function<BlockState, BlockState> blockStateFunction
    ) {
        if (!TreeFeature.canReplace(world, pos)) return false;

        BlockState state = config.foliageProvider.get(random, pos);
        // Waterlogged
        if (state.contains(Properties.WATERLOGGED)) {
            state = state.with(
                    Properties.WATERLOGGED,
                    world.testFluidState(pos, fluidState -> fluidState.isEqualAndStill(Fluids.WATER))
            );
        }
        // Apply custom state function
        state = blockStateFunction.apply(state);

        placer.placeBlock(pos, state);

        return true;
    }
}
