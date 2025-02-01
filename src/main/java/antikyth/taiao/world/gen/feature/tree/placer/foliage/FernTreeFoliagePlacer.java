// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.foliage;

import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class FernTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<FernTreeFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillFoliagePlacerFields(instance).apply(instance, FernTreeFoliagePlacer::new)
    );

    public FernTreeFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return TaiaoTreePlacers.FERN_TREE_FOLIAGE_PLACER;
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
        int largerRadius = radius + FoliageUtils.ceilDiv(radius, 3);
        BlockPos center = treeNode.getCenter().up(offset);

        boolean giantTrunk = treeNode.isGiantTrunk();
        int extra = giantTrunk ? 1 : 0;

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int boundingBoxHeight = radius - FoliageUtils.ceilDiv(radius, 4);

        for (int dh = -radius; dh <= radius + extra; dh++) {
            for (int dv = 0; dv <= boundingBoxHeight; dv++) {
                if (isCircleOutline(dh, dv + largerRadius - boundingBoxHeight, largerRadius, giantTrunk)) {
                    // ddh used for giant trunks
                    for (int ddh = 0; ddh <= extra; ddh++) {
                        // X axis
                        mutable.set(center, dh, dv - boundingBoxHeight, ddh);
                        FoliageUtils.placeDirectionalLeaves(
                                world,
                                placer,
                                random,
                                config,
                                mutable,
                                dh,
                                dv - boundingBoxHeight,
                                ddh
                        );

                        // Z axis
                        mutable.set(center, ddh, dv - boundingBoxHeight, dh);
                        FoliageUtils.placeDirectionalLeaves(
                                world,
                                placer,
                                random,
                                config,
                                mutable,
                                ddh,
                                dv - boundingBoxHeight,
                                dh
                        );
                    }
                }
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }

    protected boolean isCircleOutline(int dh, int dv, int radius, boolean giantTrunk) {
        dh = FoliageUtils.prepareCoord(dh, giantTrunk);
        dv = FoliageUtils.prepareCoord(dv, giantTrunk);

        int innerRadius = radius - 1;

        BiFunction<Integer, Integer, Boolean> matches = (x, y) -> {
            int val = (x * x) + (y * y);

            return val >= (innerRadius * innerRadius) && val <= (radius * radius);
        };

        if (matches.apply(dh, dv)) return true;

        // We want to avoid diagonal-only connections, so if the block above and in front are valid, then we want to
        // connect them.
        return matches.apply(dh + 1, dv) && matches.apply(dh, dv + 1) && !matches.apply(dh + 1, dv + 1);
    }
}
