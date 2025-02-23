// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen;

import antikyth.taiao.Taiao;
import antikyth.taiao.TaiaoConfig;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.world.gen.biome.TaiaoBiomeTags;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.gen.GenerationStep;

public class TaiaoBiomeModifications {
	public static void initialize() {
		Taiao.LOGGER.debug("Creating biome modifications");

		// Hīnaki
		if (TaiaoConfig.SpawnsAndGeneration.addEelTrapsToVanillaBiomes) {
			BiomeModifications.addFeature(
				BiomeSelectors.tag(TaiaoBiomeTags.HIINAKI_RIVER_HAS_FEATURE),
				GenerationStep.Feature.SURFACE_STRUCTURES,
				TaiaoPlacedFeatures.HIINAKI_RIVER
			);
		}
		// Eels
		if (TaiaoConfig.SpawnsAndGeneration.addEelsToVanillaBiomes) {
			BiomeModifications.addSpawn(
				BiomeSelectors.tag(TaiaoBiomeTags.SPAWNS_RIVER_EELS),
				SpawnGroup.WATER_AMBIENT,
				TaiaoEntities.EEL,
				3,
				1,
				3
			);
		}
	}
}
