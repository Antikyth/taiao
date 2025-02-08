// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure;

import antikyth.taiao.Taiao;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class TaiaoStructureSets {
	protected static Map<RegistryKey<StructureSet>, BiFunction<RegistryEntryLookup<Structure>, RegistryEntryLookup<Biome>, StructureSet>> TO_REGISTER = new HashMap<>();

	public static final RegistryKey<StructureSet> VILLAGES = register(
		Taiao.id("villages"),
		(structureLookup, biomeLookup) -> new StructureSet(
			List.of(StructureSet.createEntry(structureLookup.getOrThrow(TaiaoStructures.VILLAGE_MARAE))),
			new RandomSpreadStructurePlacement(34, 8, SpreadType.LINEAR, 10387313)
		)
	);

	public static void bootstrap(@NotNull Registerable<StructureSet> registerable) {
		RegistryEntryLookup<Structure> structureLookup = registerable.getRegistryLookup(RegistryKeys.STRUCTURE);
		RegistryEntryLookup<Biome> biomeLookup = registerable.getRegistryLookup(RegistryKeys.BIOME);

		for (Map.Entry<RegistryKey<StructureSet>, BiFunction<RegistryEntryLookup<Structure>, RegistryEntryLookup<Biome>, StructureSet>> entry : TO_REGISTER.entrySet()) {
			registerable.register(entry.getKey(), entry.getValue().apply(structureLookup, biomeLookup));
		}
	}

	public static RegistryKey<StructureSet> register(
		Identifier id,
		BiFunction<RegistryEntryLookup<Structure>, RegistryEntryLookup<Biome>, StructureSet> structureSetFactory
	) {
		RegistryKey<StructureSet> key = RegistryKey.of(RegistryKeys.STRUCTURE_SET, id);

		TO_REGISTER.put(key, structureSetFactory);

		return key;
	}
}
