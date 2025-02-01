// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.foliage;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.function.Function;

public class FoliageUtils {
    public static int prepareCoord(int d, boolean giantTrunk) {
        if (giantTrunk) return Math.min(Math.abs(d), Math.abs(d - 1));
        else return Math.abs(d);
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

    /**
     * Places a foliage block, applying the given {@code blockStateFunction} to the foliage block's state before
     * placing.
     */
    public static boolean placeFoliageBlock(
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

    public static boolean placeDirectionalLeaves(
            TestableWorld world,
            FoliagePlacer.BlockPlacer placer,
            Random random,
            TreeFeatureConfig config,
            BlockPos pos,
            int dx,
            int dy,
            int dz
    ) {
        return placeFoliageBlock(
                world,
                placer,
                random,
                config,
                pos,
                state -> directionalLeavesStateFunction(state, dx, dy, dz)
        );
    }

    public static BlockState directionalLeavesStateFunction(BlockState state, int dx, int dy, int dz) {
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
}
