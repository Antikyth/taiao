// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.world.poi.TaiaoPoiTypeTags;
import antikyth.taiao.world.poi.TaiaoPoiTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.concurrent.CompletableFuture;

public class TaiaoPoiTypeTagProvider extends FabricTagProvider<PointOfInterestType> {
	public TaiaoPoiTypeTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		getOrCreateTagBuilder(TaiaoPoiTypeTags.EEL_TRAPS)
			.add(TaiaoPoiTypes.WATERLOGGED_HIINAKI);
	}
}
