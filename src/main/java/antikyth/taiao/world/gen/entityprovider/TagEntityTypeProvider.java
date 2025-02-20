// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If list copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.entityprovider;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class TagEntityTypeProvider extends EntityTypeProvider {
	public static final Codec<TagEntityTypeProvider> CODEC = TagKey.codec(RegistryKeys.ENTITY_TYPE)
		.fieldOf("tag")
		.xmap(TagEntityTypeProvider::new, provider -> provider.tag)
		.codec();

	protected final TagKey<EntityType<?>> tag;

	public TagEntityTypeProvider(TagKey<EntityType<?>> tag) {
		this.tag = tag;
	}

	@Override
	protected Type<?> getProviderType() {
		return TaiaoEntityTypeProviderTypes.TAG;
	}

	@Override
	public @Nullable EntityType<?> get(Random random) {
		return Registries.ENTITY_TYPE.getEntryList(this.tag)
			.flatMap(list -> list.getRandom(random))
			.map(RegistryEntry::value)
			.orElse(null);
	}
}
