// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import antikyth.taiao.registry.TaiaoRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public abstract class EntityTypeProvider {
	public static final Codec<EntityTypeProvider> TYPE_CODEC = TaiaoRegistries.ENTITY_TYPE_PROVIDER_TYPE
		.getCodec()
		.dispatch(
			EntityTypeProvider::getType,
			EntityTypeProvider.Type::codec
		);

	public static SimpleEntityTypeProvider of(EntityType<?> entityType) {
		return new SimpleEntityTypeProvider(entityType);
	}

	/**
	 * {@return the type of this entity type provider}
	 */
	protected abstract Type<?> getType();

	/**
	 * {@return a provided entity type}
	 */
	public abstract @Nullable EntityType<?> get(Random random);

	public record Type<P extends EntityTypeProvider>(Codec<P> codec) {
	}
}
