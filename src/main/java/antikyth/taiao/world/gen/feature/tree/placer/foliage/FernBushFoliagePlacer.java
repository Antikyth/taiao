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

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FernBushFoliagePlacer extends FoliagePlacer {
	public static final Codec<FernBushFoliagePlacer> CODEC = RecordCodecBuilder.create(
		instance -> fillFoliagePlacerFields(instance).and(
			IntProvider.createValidatingCodec(0, 7)
				.optionalFieldOf("vertical_radius")
				.forGetter(placer -> placer.verticalRadius)
		).apply(instance, FernBushFoliagePlacer::new)
	);

	protected final Optional<IntProvider> verticalRadius;

	public FernBushFoliagePlacer(IntProvider radius, IntProvider offset) {
		this(radius, offset, Optional.empty());
	}

	public FernBushFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider verticalRadius) {
		this(radius, offset, Optional.of(verticalRadius));
	}

	public FernBushFoliagePlacer(IntProvider radius, IntProvider offset, Optional<IntProvider> verticalRadius) {
		super(radius, offset);

		this.verticalRadius = verticalRadius;
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return TaiaoTreePlacers.FERN_BUSH_FOLIAGE_PLACER;
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
		radius += treeNode.getFoliageRadius();
		BlockPos center = treeNode.getCenter().up(offset - 1);

		int height = foliageHeight + this.verticalRadius.orElse(this.radius).get(random);

		boolean giantTrunk = treeNode.isGiantTrunk();
		int extra = giantTrunk ? 1 : 0;

		BlockPos.Mutable mutable = new BlockPos.Mutable();

		// Vertical arm.
		for (int dx = 0; dx <= extra; dx++) {
			for (int dz = 0; dz <= extra; dz++) {
				for (int dy = 0; dy <= height; dy++) {
					mutable.set(center, dx, dy, dz);
					placeFoliageBlock(world, placer, random, config, mutable);
				}
			}
		}

		// Horizontal arms.
		for (int dh = -radius; dh <= radius + extra; dh++) {
			// Skip the center.
			if (dh >= 0 && dh <= extra) continue;

			for (int ddh = 0; ddh <= extra; ddh++) {
				// X axis
				mutable.set(center, dh, 0, ddh);
				FoliageUtils.placeDirectionalLeaves(world, placer, random, config, mutable, dh, 0, ddh);

				// Z axis
				mutable.set(center, ddh, 0, dh);
				FoliageUtils.placeDirectionalLeaves(world, placer, random, config, mutable, ddh, 0, dh);
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
}
