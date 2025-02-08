// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure;

import antikyth.taiao.Taiao;
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
	public static final RegistryKey<StructurePool> TOWN_CENTERS = key(maraeId("town_centers"));
	public static final RegistryKey<StructurePool> STREETS = key(maraeId("streets"));
	public static final RegistryKey<StructurePool> HOUSES = key(maraeId("houses"));
	public static final RegistryKey<StructurePool> TERMINATORS = key(maraeId("terminators"));

	public static final RegistryKey<StructurePool> TREES = key(maraeId("trees"));
	public static final RegistryKey<StructurePool> DECOR = key(maraeId("decor"));
	public static final RegistryKey<StructurePool> VILLAGERS = key(maraeId("villagers"));

	public static void bootstrap(@NotNull Registerable<StructurePool> registerable) {
		RegistryEntryLookup<PlacedFeature> placedFeatureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
		RegistryEntryLookup<StructureProcessorList> processorListLookup = registerable.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);
		RegistryEntryLookup<StructurePool> poolLookup = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

		RegistryEntry<StructureProcessorList> farmProcessorList = processorListLookup.getOrThrow(
			TaiaoStructureProcessorLists.FARM_MARAE
		);
		RegistryEntry<StructureProcessorList> streetProcessorList = processorListLookup.getOrThrow(
			TaiaoStructureProcessorLists.STREET_MARAE
		);

		RegistryEntry<StructurePool> terminatorsPool = poolLookup.getOrThrow(TERMINATORS);
		RegistryEntry<StructurePool> emptyPool = poolLookup.getOrThrow(StructurePools.EMPTY);

		registerable.register(
			TOWN_CENTERS,
			new StructurePool(
				emptyPool,
				List.of(
					Pair.of(
						TaiaoStructurePools.createLegacySingleElement(maraeId("town_centers/marae_wharenui_1")),
						2
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
							maraeId("streets/straight_1"),
							streetProcessorList
						),
						2
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
						TaiaoStructurePools.createProcessedLegacySingleElement(
							maraeId("houses/marae_farm_1"),
							farmProcessorList
						),
						3
					),
					Pair.of(StructurePoolElement.ofEmpty(), 6)
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
	}

	public static Identifier maraeId(String name) {
		return Taiao.id("village/marae/" + name);
	}

	public static RegistryKey<StructurePool> key(Identifier id) {
		return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, id);
	}
}
