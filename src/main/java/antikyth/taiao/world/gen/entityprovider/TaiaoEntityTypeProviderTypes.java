// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import antikyth.taiao.Taiao;
import antikyth.taiao.registry.TaiaoRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TaiaoEntityTypeProviderTypes {
	public static final EntityTypeProvider.Type<EmptyEntityTypeProvider> NONE = register(
		Taiao.id("empty"),
		EmptyEntityTypeProvider.CODEC
	);
	public static final EntityTypeProvider.Type<SimpleEntityTypeProvider> SIMPLE = register(
		Taiao.id("simple"),
		SimpleEntityTypeProvider.CODEC
	);
	public static final EntityTypeProvider.Type<TagEntityTypeProvider> TAG = register(
		Taiao.id("tag"),
		TagEntityTypeProvider.CODEC
	);

	public static final EntityTypeProvider.Type<ProbabilityEntityTypeProvider> PROBABILITY = register(
		Taiao.id("probability"),
		ProbabilityEntityTypeProvider.CODEC
	);
	public static final EntityTypeProvider.Type<WeightedEntityTypeProvider> WEIGHTED = register(
		Taiao.id("weighted"),
		WeightedEntityTypeProvider.CODEC
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered entity type provider types");
	}

	public static <P extends EntityTypeProvider> EntityTypeProvider.Type<P> register(Identifier id, Codec<P> codec) {
		return Registry.register(TaiaoRegistries.ENTITY_TYPE_PROVIDER_TYPE, id, new EntityTypeProvider.Type<>(codec));
	}
}
