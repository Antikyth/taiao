// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.banner.TaiaoBannerPatterns;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BannerPatternTags;

import java.util.concurrent.CompletableFuture;

public class TaiaoBannerPatternTagProvider extends FabricTagProvider<BannerPattern> {
	public TaiaoBannerPatternTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, RegistryKeys.BANNER_PATTERN, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		getOrCreateTagBuilder(BannerPatternTags.NO_ITEM_REQUIRED)
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_PRIMARY)
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_SECONDARY)
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_PRIMARY)
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_SECONDARY)
			.add(TaiaoBannerPatterns.PAATIKI_PRIMARY)
			.add(TaiaoBannerPatterns.PAATIKI_SECONDARY)
			.add(TaiaoBannerPatterns.KAOKAO_UP_PRIMARY)
			.add(TaiaoBannerPatterns.KAOKAO_UP_SECONDARY)
			.add(TaiaoBannerPatterns.KAOKAO_DOWN_PRIMARY)
			.add(TaiaoBannerPatterns.KAOKAO_DOWN_SECONDARY);
	}
}
