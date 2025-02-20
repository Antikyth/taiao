// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class WeightedEntityTypeProvider extends EntityTypeProvider {
	public static final Codec<WeightedEntityTypeProvider> CODEC = DataPool.createEmptyAllowedCodec(Registries.ENTITY_TYPE.getCodec())
		.xmap(WeightedEntityTypeProvider::new, provider -> provider.entityTypes)
		.fieldOf("entries")
		.codec();

	protected final DataPool<EntityType<?>> entityTypes;

	public WeightedEntityTypeProvider(DataPool<EntityType<?>> entityTypes) {
		this.entityTypes = entityTypes;
	}

	@Override
	protected Type<?> getProviderType() {
		return TaiaoEntityTypeProviderTypes.WEIGHTED;
	}

	@Override
	public @Nullable EntityType<?> get(Random random) {
		return this.entityTypes.getDataOrEmpty(random).orElse(null);
	}
}
