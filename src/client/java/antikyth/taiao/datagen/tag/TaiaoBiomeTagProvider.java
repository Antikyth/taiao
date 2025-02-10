// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.world.gen.biome.TaiaoBiomeTags;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class TaiaoBiomeTagProvider extends FabricTagProvider<Biome> {
	public TaiaoBiomeTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, RegistryKeys.BIOME, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		// Vanilla tags
		getOrCreateTagBuilder(BiomeTags.IS_FOREST).add(TaiaoBiomes.NATIVE_FOREST);
		getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO).add(TaiaoBiomes.NATIVE_FOREST);

		getOrCreateTagBuilder(BiomeTags.MINESHAFT_HAS_STRUCTURE).add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(BiomeTags.RUINED_PORTAL_SWAMP_HAS_STRUCTURE).add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(BiomeTags.WATER_ON_MAP_OUTLINES).add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(BiomeTags.INCREASED_FIRE_BURNOUT).add(TaiaoBiomes.KAHIKATEA_SWAMP);

		// Conventional tags
		getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS).add(TaiaoBiomes.NATIVE_FOREST);
		getOrCreateTagBuilder(ConventionalBiomeTags.SWAMP).add(TaiaoBiomes.KAHIKATEA_SWAMP);

		getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_TEMPERATE).add(TaiaoBiomes.NATIVE_FOREST);
		getOrCreateTagBuilder(ConventionalBiomeTags.CLIMATE_WET).add(TaiaoBiomes.KAHIKATEA_SWAMP);

		// Te Taiao o Aotearoa tags
		getOrCreateTagBuilder(TaiaoBiomeTags.INHIBITS_CAT_SPAWNING)
			.add(TaiaoBiomes.NATIVE_FOREST)
			.add(TaiaoBiomes.KAHIKATEA_SWAMP);
		getOrCreateTagBuilder(TaiaoBiomeTags.VILLAGE_MARAE_HAS_STRUCTURE)
			.add(TaiaoBiomes.NATIVE_FOREST);
	}
}
