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
import net.minecraft.world.gen.foliage.FoliagePlacerType;

/**
 * Generates a sphere of foliage blocks.
 */
public class SphericalFoliagePlacer extends ThreeDimensionalFoliagePlacer {
    public static final Codec<SphericalFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillFoliagePlacerFields(instance).apply(instance, SphericalFoliagePlacer::new)
    );

    public SphericalFoliagePlacer(
            IntProvider radius,
            IntProvider offset
    ) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return TaiaoTreePlacers.SPHERICAL_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(
            TestableWorld world,
            BlockPlacer placer,
            Random random,
            TreeFeatureConfig config,
            int trunkHeight,
            TreeNode treeNode,
            int foliageHeight,
            int radius,
            int offset
    ) {
        radius += treeNode.getFoliageRadius() - 1;
        BlockPos center = treeNode.getCenter().up(offset);

        boolean giantTrunk = treeNode.isGiantTrunk();
        int extra = giantTrunk ? 1 : 0;

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // Check each position in a bounding box around the center.
        for (int dx = -radius; dx <= radius + extra; dx++) {
            for (int dy = -radius; dy <= radius + extra; dy++) {
                for (int dz = -radius; dz <= radius + extra; dz++) {
                    if (isPositionValid3d(dx, dy, dz, radius, giantTrunk)) {
                        mutable.set(center, dx, dy, dz);

                        placeFoliageBlock(world, placer, random, config, mutable);
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
    protected boolean isValidForLeaves3d(int dx, int dy, int dz, int radius) {
        return (dx * dx) + (dy * dy) + (dz * dz) <= radius * radius;
    }
}
