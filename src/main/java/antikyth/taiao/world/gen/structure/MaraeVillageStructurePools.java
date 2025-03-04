// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import antikyth.taiao.world.gen.structure.processor.TaiaoStructureProcessorLists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MaraeVillageStructurePools {
	public static final RegistryKey<StructurePool> TOWN_CENTERS = key(villageId("town_centers"));
	public static final RegistryKey<StructurePool> STREETS = key(villageId("streets"));
	public static final RegistryKey<StructurePool> HOUSES = key(villageId("houses"));
	public static final RegistryKey<StructurePool> TERMINATORS = key(villageId("terminators"));

	public static final RegistryKey<StructurePool> TREES = key(villageId("trees"));
	public static final RegistryKey<StructurePool> DECOR = key(villageId("decor"));
	public static final RegistryKey<StructurePool> VILLAGERS = key(villageId("villagers"));
	public static final RegistryKey<StructurePool> PETS = key(villageId("pets"));

	public static void bootstrap(@NotNull Registerable<StructurePool> registerable) {
		RegistryEntryLookup<PlacedFeature> placedFeatureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
		RegistryEntryLookup<StructureProcessorList> processorListLookup = registerable.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);
		RegistryEntryLookup<StructurePool> poolLookup = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

		RegistryEntry<PlacedFeature> cabbageTree = placedFeatureLookup.getOrThrow(TaiaoPlacedFeatures.CABBAGE_TREE_CHECKED);
		RegistryEntry<PlacedFeature> mamakuTree = placedFeatureLookup.getOrThrow(TaiaoPlacedFeatures.MAMAKU_TREE_CHECKED);
		RegistryEntry<PlacedFeature> whekiiPongaTree = placedFeatureLookup.getOrThrow(TaiaoPlacedFeatures.WHEKII_PONGA_TREE_CHECKED);

		RegistryEntry<StructureProcessorList> farmProcessorList = processorListLookup.getOrThrow(
			TaiaoStructureProcessorLists.FARM_MARAE
		);
		RegistryEntry<StructureProcessorList> streetProcessorList = processorListLookup.getOrThrow(
			TaiaoStructureProcessorLists.STREET_MARAE
		);
		RegistryEntry<StructureProcessorList> fisherProcessorList = processorListLookup.getOrThrow(
			TaiaoStructureProcessorLists.FISHER_MARAE
		);

		RegistryEntry<StructurePool> terminatorsPool = poolLookup.getOrThrow(TERMINATORS);
		RegistryEntry<StructurePool> emptyPool = poolLookup.getOrThrow(StructurePools.EMPTY);

		registerable.register(
			TOWN_CENTERS,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("town_centers/wharenui_1")
						), 2
					)
				),
				StructurePool.Projection.RIGID
			)
		);
		registerable.register(
			STREETS,
			new StructurePool(
				terminatorsPool,
				List.of(
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/corner_01"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/corner_02"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/corner_03"),
							streetProcessorList
						), 2
					),

					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_01"),
							streetProcessorList
						), 4
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_02"),
							streetProcessorList
						), 4
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_03"),
							streetProcessorList
						), 7
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_04"),
							streetProcessorList
						), 7
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_05"),
							streetProcessorList
						), 3
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/straight_06"),
							streetProcessorList
						), 4
					),

					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_01"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_02"),
							streetProcessorList
						), 1
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_03"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_04"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_05"),
							streetProcessorList
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("streets/crossroad_06"),
							streetProcessorList
						), 2
					)
				),
				StructurePool.Projection.TERRAIN_MATCHING
			)
		);
		registerable.register(
			HOUSES,
			new StructurePool(
				terminatorsPool,
				List.of(
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("houses/wharepuni_1")
						), 14
					),
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("houses/kaauta_1")
						), 1
					),
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("houses/kaauta_2")
						), 2
					),
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("houses/kaauta_3")
						), 1
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("houses/fisher_whare_1"),
							fisherProcessorList
						), 4
					),
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(
							villageId("houses/paa_harakeke_1")
						), 4
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("houses/small_farm_1"),
							farmProcessorList
						), 7
					),
					Pair.of(
						TaiaoStructurePools.createProcessedLegacySingleElement(
							villageId("houses/small_farm_2"),
							farmProcessorList
						), 4
					),
					Pair.of(StructurePoolElement.ofEmpty(), 4)
				),
				StructurePool.Projection.RIGID
			)
		);
		registerable.register(
			TERMINATORS,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(
						StructurePoolElement.ofProcessedLegacySingle(
							"village/plains/terminators/terminator_01",
							streetProcessorList
						), 1
					),
					Pair.of(
						StructurePoolElement.ofProcessedLegacySingle(
							"village/plains/terminators/terminator_02",
							streetProcessorList
						), 1
					),
					Pair.of(
						StructurePoolElement.ofProcessedLegacySingle(
							"village/plains/terminators/terminator_03",
							streetProcessorList
						), 1
					),
					Pair.of(
						StructurePoolElement.ofProcessedLegacySingle(
							"village/plains/terminators/terminator_04",
							streetProcessorList
						), 1
					)
				),
				StructurePool.Projection.TERRAIN_MATCHING
			)
		);

		registerable.register(
			TREES,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(StructurePoolElement.ofFeature(cabbageTree), 1),
					Pair.of(StructurePoolElement.ofFeature(mamakuTree), 1),
					Pair.of(StructurePoolElement.ofFeature(whekiiPongaTree), 1)
				),
				StructurePool.Projection.RIGID
			)
		);
		registerable.register(
			DECOR,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("decor/lamp_1")), 2),
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("decor/lamp_2")), 4),
					Pair.of(StructurePoolElement.ofFeature(cabbageTree), 1),
					Pair.of(StructurePoolElement.ofFeature(mamakuTree), 1),
					Pair.of(StructurePoolElement.ofFeature(whekiiPongaTree), 1),
					Pair.of(StructurePoolElement.ofEmpty(), 4)
				),
				StructurePool.Projection.RIGID
			)
		);
		registerable.register(
			VILLAGERS,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("villagers/nitwit")), 1),
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("villagers/baby")), 1),
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("villagers/unemployed")), 10)
				),
				StructurePool.Projection.RIGID
			)
		);
		registerable.register(
			PETS,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(TaiaoStructurePools.createLegacySingleElement(villageId("pets/kaakaapoo_1")), 1)
				),
				StructurePool.Projection.RIGID
			)
		);
	}

	public static Identifier villageId(String name) {
		return Taiao.id("village/marae/" + name);
	}

	public static RegistryKey<StructurePool> key(Identifier id) {
		return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, id);
	}
}
