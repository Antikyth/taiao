// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.decorator;

import antikyth.taiao.block.HaastsEagleNestBlock;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.state.HaastsEagleNestContents;
import antikyth.taiao.block.state.HorizontalDoubleSquareBlockPart;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class HaastsEagleNestTreeDecorator extends TreeDecorator {
	public static final Codec<HaastsEagleNestTreeDecorator> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.floatRange(0f, 1f)
				.fieldOf("probability")
				.forGetter(decorator -> decorator.probability),
			DataPool.createCodec(HaastsEagleNestContents.CODEC)
				.fieldOf("egg")
				.forGetter(decorator -> decorator.eggStagePool)
		).apply(instance, HaastsEagleNestTreeDecorator::new)
	);

	private final float probability;
	private final DataPool<HaastsEagleNestContents> eggStagePool;

	public HaastsEagleNestTreeDecorator(float probability, DataPool<HaastsEagleNestContents> eggStagePool) {
		this.probability = probability;
		this.eggStagePool = eggStagePool;
	}

	public HaastsEagleNestTreeDecorator(float probability) {
		this(
			probability,
			DataPool.<HaastsEagleNestContents>builder()
				.add(HaastsEagleNestContents.INTACT_EGG, 3)
				.add(HaastsEagleNestContents.PARTIALLY_CRACKED_EGG, 2)
				.add(HaastsEagleNestContents.CRACKED_EGG, 1)
				.add(HaastsEagleNestContents.EMPTY, 9)
				.build()
		);
	}

	@Override
	protected TreeDecoratorType<?> getType() {
		return TaiaoTreeDecorators.HAASTS_EAGLE_NEST;
	}

	@Override
	public void generate(@NotNull Generator generator) {
		Random random = generator.getRandom();
		TestableWorld world = generator.getWorld();

		if (random.nextFloat() < this.probability) {
			BlockPos.Mutable mutable = new BlockPos.Mutable();
			BlockPos.Mutable origin = new BlockPos.Mutable();

			HorizontalDoubleSquareBlockPart root = HorizontalDoubleSquareBlockPart.getRandom(random);

			// Randomly search leaves until a valid position for the nest has been found
			for (BlockPos leaves : Util.copyShuffled(generator.getLeavesPositions(), random)) {
				origin.set(leaves, Direction.UP.getVector());

				// TODO: ensure the two spaces above the nest are clear too
				// Whether all nest positions are replaceable.
				boolean replaceable = root.allPlacements()
					.map(Pair::getLeft)
					.map(offset -> mutable.set(origin, offset))
					.allMatch(generator::isAir);
				if (!replaceable) continue;

				// Whether the nest is diagonally supported underneath.
				boolean supported = root.diagonals().anyMatch(
					diagonal -> diagonal.map(Pair::getLeft)
						.map(offset -> mutable.set(leaves, offset))
						.allMatch(pos -> world.testBlockState(
							pos,
							state -> state.isIn(TaiaoBlockTags.SUPPORTS_HAASTS_EAGLE_NESTS)
						))
				);
				if (!supported) continue;

				// Place each part of the nest
				root.allPlacements().forEach(placement -> {
					BlockPos offset = placement.getLeft();
					HorizontalDoubleSquareBlockPart part = placement.getRight();
					mutable.set(origin, offset);

					HaastsEagleNestContents stage = this.eggStagePool.getDataOrEmpty(random)
						.orElse(HaastsEagleNestContents.EMPTY);

					generator.replace(
						mutable,
						TaiaoBlocks.HAASTS_EAGLE_NEST.getDefaultState()
							.with(HaastsEagleNestBlock.PART, part)
							.with(HaastsEagleNestBlock.CONTENTS, stage)
					);
				});

				break;
			}
		}
	}
}
