// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.TaiaoBuiltinResourcePacks;
import antikyth.taiao.datagen.language.*;
import antikyth.taiao.datagen.loottable.TaiaoBlockLootTableProvider;
import antikyth.taiao.datagen.loottable.TaiaoChestLootTableProvider;
import antikyth.taiao.datagen.loottable.TaiaoEntityLootTableProvider;
import antikyth.taiao.datagen.model.TaiaoModelProvider;
import antikyth.taiao.datagen.tag.*;
import antikyth.taiao.datagen.world.gen.TaiaoBiomeProvider;
import antikyth.taiao.datagen.world.gen.TaiaoDynamicFeatureProvider;
import antikyth.taiao.datagen.world.gen.TaiaoStructureProvider;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import antikyth.taiao.world.gen.structure.TaiaoStructurePools;
import antikyth.taiao.world.gen.structure.TaiaoStructureProcessorLists;
import antikyth.taiao.world.gen.structure.TaiaoStructureSets;
import antikyth.taiao.world.gen.structure.TaiaoStructures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.NotNull;

public class TaiaoDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		// Languages
		pack.addProvider(EnglishUsLangProvider::new);
		// British-like English
		pack.addProvider((FabricDataOutput output) -> new EnglishGbLangProvider(output, "en_gb"));
		pack.addProvider((FabricDataOutput output) -> new EnglishGbLangProvider(output, "en_ca"));
		pack.addProvider((FabricDataOutput output) -> new EnglishGbLangProvider(output, "en_au"));
		pack.addProvider((FabricDataOutput output) -> new EnglishGbLangProvider(output, "en_nz"));

		pack.addProvider(TaiaoModelProvider::new);
		pack.addProvider(TaiaoRecipeProvider::new);

		// Loot tables
		pack.addProvider(TaiaoBlockLootTableProvider::new);
		pack.addProvider(TaiaoEntityLootTableProvider::new);
		pack.addProvider(TaiaoChestLootTableProvider::new);

		// Tags
		TaiaoBlockTagProvider blockTagProvider = pack.addProvider(TaiaoBlockTagProvider::new);
		pack.addProvider((output, lookup) -> new TaiaoItemTagProvider(output, lookup, blockTagProvider));
		pack.addProvider(TaiaoEntityTagProvider::new);
		pack.addProvider(TaiaoBiomeTagProvider::new);
		pack.addProvider(TaiaoStructureTagProvider::new);
		pack.addProvider(TaiaoBannerPatternTagProvider::new);

		// World gen
		pack.addProvider(TaiaoDynamicFeatureProvider::new);
		pack.addProvider(TaiaoBiomeProvider::new);
		pack.addProvider(TaiaoStructureProvider::new);

		// English-names language resource pack
		FabricDataGenerator.Pack englishNamesPack = generator.createBuiltinResourcePack(
			TaiaoBuiltinResourcePacks.ENGLISH_NAMES
		);

		englishNamesPack.addProvider(EnglishNamesUsLangProvider::new);
		// British-like English
		englishNamesPack.addProvider((FabricDataOutput output) -> new EnglishNamesGbLangProvider(
			output,
			"en_gb"
		));
		englishNamesPack.addProvider((FabricDataOutput output) -> new EnglishNamesGbLangProvider(
			output,
			"en_ca"
		));
		englishNamesPack.addProvider((FabricDataOutput output) -> new EnglishNamesGbLangProvider(
			output,
			"en_au"
		));
		englishNamesPack.addProvider(EnglishNamesNzLangProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder builder) {
		DataGeneratorEntrypoint.super.buildRegistry(builder);

		builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, TaiaoConfiguredFeatures::bootstrap);
		builder.addRegistry(RegistryKeys.PLACED_FEATURE, TaiaoPlacedFeatures::bootstrap);

		builder.addRegistry(RegistryKeys.BIOME, TaiaoBiomes::bootstrap);

		builder.addRegistry(RegistryKeys.PROCESSOR_LIST, TaiaoStructureProcessorLists::bootstrap);
		builder.addRegistry(RegistryKeys.TEMPLATE_POOL, TaiaoStructurePools::bootstrap);
		builder.addRegistry(RegistryKeys.STRUCTURE, TaiaoStructures::bootstrap);
		builder.addRegistry(RegistryKeys.STRUCTURE_SET, TaiaoStructureSets::bootstrap);
	}
}
