// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.TaiaoEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class TaiaoEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public TaiaoEntityTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		// Vanilla tags
		getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(TaiaoEntities.PUUKEKO);

		// Te Taiao o Aotearoa tags
		getOrCreateTagBuilder(TaiaoEntityTypeTags.PUUKEKO_PREDATORS)
			.add(EntityType.CAT)
			.add(EntityType.WOLF)
			.add(EntityType.FOX);
		getOrCreateTagBuilder(TaiaoEntityTypeTags.KAAKAAPOO_PREDATORS)
			.add(EntityType.CAT)
			.add(EntityType.WOLF)
			.add(EntityType.FOX);
	}
}
