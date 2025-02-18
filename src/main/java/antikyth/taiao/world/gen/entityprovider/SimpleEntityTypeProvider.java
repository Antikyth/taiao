// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;

public class SimpleEntityTypeProvider extends EntityTypeProvider {
	public static final Codec<SimpleEntityTypeProvider> CODEC = Registries.ENTITY_TYPE.getCodec()
		.fieldOf("entity_type")
		.xmap(SimpleEntityTypeProvider::new, provider -> provider.entityType)
		.codec();

	protected final EntityType<?> entityType;

	public SimpleEntityTypeProvider(EntityType<?> entityType) {
		this.entityType = entityType;
	}

	@Override
	protected Type<?> getType() {
		return TaiaoEntityTypeProviderTypes.SIMPLE;
	}

	@Override
	public EntityType<?> get(Random random) {
		return this.entityType;
	}
}
