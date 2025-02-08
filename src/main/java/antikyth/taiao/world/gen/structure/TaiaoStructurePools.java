// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.structure;

import com.mojang.datafixers.util.Either;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class TaiaoStructurePools {
	public static void bootstrap(Registerable<StructurePool> registerable) {
		MaraeVillageStructurePools.bootstrap(registerable);
	}

	@Contract(pure = true)
	public static @NotNull Function<StructurePool.Projection, LegacySinglePoolElement> createLegacySingleElement(
		Identifier id
	) {
		return projection -> new LegacySinglePoolElement(
			Either.left(id),
			StructurePoolElement.EMPTY_PROCESSORS,
			projection
		);
	}

	@Contract(pure = true)
	public static @NotNull Function<StructurePool.Projection, LegacySinglePoolElement> createProcessedLegacySingleElement(
		Identifier id,
		RegistryEntry<StructureProcessorList> processorList
	) {
		return projection -> new LegacySinglePoolElement(Either.left(id), processorList, projection);
	}
}
