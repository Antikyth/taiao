// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.entity.damage.TaiaoDamageTypeTags;
import antikyth.taiao.entity.damage.TaiaoDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TaiaoDamageTypeTagProvider extends FabricTagProvider<DamageType> {
	public TaiaoDamageTypeTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		getOrCreateTagBuilder(TaiaoDamageTypeTags.TRAPS)
			.add(TaiaoDamageTypes.HIINAKI);
	}
}
