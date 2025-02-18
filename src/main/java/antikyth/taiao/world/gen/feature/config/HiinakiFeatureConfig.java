// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.config;

import antikyth.taiao.world.gen.entityprovider.EntityTypeProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.jetbrains.annotations.NotNull;

public record HiinakiFeatureConfig(
	ContentWeights weights,
	Identifier baitLootTableId,
	EntityTypeProvider trappedEntityProvider
) implements FeatureConfig {
	public static final Codec<HiinakiFeatureConfig> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ContentWeights.CODEC
				.fieldOf("content_weights")
				.forGetter(config -> config.weights),
			Identifier.CODEC
				.fieldOf("bait_loot_table")
				.forGetter(config -> config.baitLootTableId),
			EntityTypeProvider.TYPE_CODEC
				.fieldOf("trapped_entity_provider")
				.forGetter(config -> config.trappedEntityProvider)
		).apply(instance, HiinakiFeatureConfig::new)
	);

	public enum Contents {
		EMPTY,
		BAIT,
		TRAPPED_ENTITY
	}

	public static class ContentWeights {
		public static final Codec<ContentWeights> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.intRange(0, Integer.MAX_VALUE)
				.fieldOf("empty")
				.forGetter(weights -> weights.emptyWeight),
			Codec.intRange(0, Integer.MAX_VALUE)
				.fieldOf("bait")
				.forGetter(weights -> weights.hasBaitWeight),
			Codec.intRange(0, Integer.MAX_VALUE)
				.fieldOf("trapped_entity")
				.forGetter(weights -> weights.hasTrappedEntityWeight)
		).apply(instance, ContentWeights::new));

		protected final int emptyWeight;
		protected final int hasBaitWeight;
		protected final int hasTrappedEntityWeight;

		protected final int totalWeight;

		public ContentWeights(int emptyWeight, int hasBaitWeight, int hasTrappedEntityWeight) {
			this.emptyWeight = emptyWeight;
			this.hasBaitWeight = hasBaitWeight;
			this.hasTrappedEntityWeight = hasTrappedEntityWeight;

			this.totalWeight = emptyWeight + hasBaitWeight + hasTrappedEntityWeight;
		}

		public Contents get(@NotNull Random random) {
			int value = random.nextInt(this.totalWeight);

			if (value < this.emptyWeight) return Contents.EMPTY;
			else if (value - this.emptyWeight < this.hasBaitWeight) return Contents.BAIT;
			else return Contents.TRAPPED_ENTITY;
		}
	}
}
