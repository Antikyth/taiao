// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.biome.TaiaoBiomeTags;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TaiaoStructures {
	protected static Map<RegistryKey<Structure>, BiFunction<RegistryEntryLookup<Biome>, RegistryEntryLookup<StructurePool>, Structure>> TO_REGISTER = new HashMap<>();

	public static final RegistryKey<Structure> VILLAGE_MARAE = register(
		Taiao.id("village_marae"),
		(biomeLookup, poolLookup) -> new JigsawStructure(
			Structures.createConfig(
				biomeLookup.getOrThrow(TaiaoBiomeTags.VILLAGE_MARAE_HAS_STRUCTURE),
				StructureTerrainAdaptation.BEARD_THIN
			),
			poolLookup.getOrThrow(MaraeVillageStructurePools.TOWN_CENTERS),
			6,
			ConstantHeightProvider.create(YOffset.fixed(0)),
			true,
			Heightmap.Type.WORLD_SURFACE_WG
		)
	);

	public static void bootstrap(@NotNull Registerable<Structure> registerable) {
		RegistryEntryLookup<Biome> biomeLookup = registerable.getRegistryLookup(RegistryKeys.BIOME);
		RegistryEntryLookup<StructurePool> structurePoolLookup = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

		for (Map.Entry<RegistryKey<Structure>, BiFunction<RegistryEntryLookup<Biome>, RegistryEntryLookup<StructurePool>, Structure>> entry : TO_REGISTER.entrySet()) {
			registerable.register(entry.getKey(), entry.getValue().apply(biomeLookup, structurePoolLookup));
		}
	}

	public static RegistryKey<Structure> register(
		Identifier id,
		BiFunction<RegistryEntryLookup<Biome>, RegistryEntryLookup<StructurePool>, Structure> structureFactory
	) {
		RegistryKey<Structure> key = RegistryKey.of(RegistryKeys.STRUCTURE, id);

		TO_REGISTER.put(key, structureFactory);

		return key;
	}
}
