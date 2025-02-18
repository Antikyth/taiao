// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProbabilityEntityTypeProvider extends EntityTypeProvider {
	public static final Codec<ProbabilityEntityTypeProvider> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.floatRange(0f, 1f)
				.fieldOf("probability")
				.forGetter(provider -> provider.probability),
			Registries.ENTITY_TYPE.getCodec()
				.fieldOf("entity_type")
				.forGetter(provider -> provider.entityType)
		).apply(instance, ProbabilityEntityTypeProvider::new)
	);

	protected final float probability;
	protected final EntityType<?> entityType;

	public ProbabilityEntityTypeProvider(float probability, EntityType<?> entityType) {
		this.probability = probability;
		this.entityType = entityType;
	}

	@Override
	protected Type<?> getType() {
		return TaiaoEntityTypeProviderTypes.PROBABILITY;
	}

	@Override
	public @Nullable EntityType<?> get(@NotNull Random random) {
		return random.nextFloat() < this.probability ? this.entityType : null;
	}
}
