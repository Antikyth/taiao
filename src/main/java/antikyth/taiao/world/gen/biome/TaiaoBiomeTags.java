// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TaiaoBiomeTags {
	/**
	 * Biomes in which {@linkplain antikyth.taiao.world.gen.structure.TaiaoStructures#VILLAGE_MARAE marae} can generate.
	 */
	public static final TagKey<Biome> VILLAGE_MARAE_HAS_STRUCTURE = tagKey(Taiao.id("has_structure/village_marae"));
	/**
	 * Biomes in which {@linkplain TaiaoPlacedFeatures#HIINAKI_SWAMP hīnaki} generate.
	 */
	public static final TagKey<Biome> HIINAKI_RIVER_HAS_FEATURE = tagKey(Taiao.id("has_feature/hiinaki_river"));

	/**
	 * Biomes in which cats are inhibited from {@linkplain net.minecraft.world.spawner.CatSpawner spawning}.
	 */
	public static final TagKey<Biome> INHIBITS_CAT_SPAWNING = tagKey(Taiao.id("inhibits_cat_spawns"));
	/**
	 * Biomes in which {@linkplain TaiaoEntities#EEL eels} can spawn with a lower weight.
	 */
	public static final TagKey<Biome> SPAWNS_RIVER_EELS = tagKey(Taiao.id("spawns_river_eels"));

	public static TagKey<Biome> tagKey(Identifier id) {
		return TagKey.of(RegistryKeys.BIOME, id);
	}
}
