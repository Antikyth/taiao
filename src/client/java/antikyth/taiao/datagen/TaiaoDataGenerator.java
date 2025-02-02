// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.datagen.loottable.TaiaoBlockLootTableProvider;
import antikyth.taiao.datagen.loottable.TaiaoEntityLootTableProvider;
import antikyth.taiao.datagen.model.TaiaoModelProvider;
import antikyth.taiao.datagen.tag.*;
import antikyth.taiao.datagen.world.gen.TaiaoBiomeProvider;
import antikyth.taiao.datagen.world.gen.TaiaoConfiguredFeatureProvider;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class TaiaoDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(TaiaoModelProvider::new);
		pack.addProvider(TaiaoRecipeProvider::new);

		// Loot tables
		pack.addProvider(TaiaoBlockLootTableProvider::new);
		pack.addProvider(TaiaoEntityLootTableProvider::new);

		// Tags
		TaiaoBlockTagProvider blockTagProvider = pack.addProvider(TaiaoBlockTagProvider::new);
		pack.addProvider((output, lookup) -> new TaiaoItemTagProvider(output, lookup, blockTagProvider));
		pack.addProvider(TaiaoEntityTagProvider::new);
		pack.addProvider(TaiaoBiomeTagProvider::new);
		pack.addProvider(TaiaoBannerPatternTagProvider::new);

		// World gen
		pack.addProvider(TaiaoConfiguredFeatureProvider::new);
		pack.addProvider(TaiaoBiomeProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder builder) {
		DataGeneratorEntrypoint.super.buildRegistry(builder);

		builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, TaiaoConfiguredFeatures::bootstrap);
		builder.addRegistry(RegistryKeys.PLACED_FEATURE, TaiaoPlacedFeatures::bootstrap);

		builder.addRegistry(RegistryKeys.BIOME, TaiaoBiomes::bootstrap);
	}
}
