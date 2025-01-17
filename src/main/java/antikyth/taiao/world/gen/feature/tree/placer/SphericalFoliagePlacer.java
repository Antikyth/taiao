// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

/**
 * Generates a sphere of foliage blocks.
 */
public class SphericalFoliagePlacer extends FoliagePlacer {
    public static final Codec<SphericalFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> fillFoliagePlacerFields(
            instance).apply(instance, SphericalFoliagePlacer::new));

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

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // It would be nice to support giant trees, where the center would be a 2x2x2 area instead of 1x1x1, but I'm not
        // sure how to alter the sphere equation to account for the center effectively being 'between' blocks.

        // Check each position in a bounding box around the center.
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    // Check if this position within the bounding box is also within the sphere.
                    if ((x * x) + (y * y) + (z * z) <= radius * radius) {
                        mutable.set(center, x, y, z);

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
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        // This would be perfect to use if not for the fact that it works in 2D, not 3D like we need.
        return false;
    }
}
