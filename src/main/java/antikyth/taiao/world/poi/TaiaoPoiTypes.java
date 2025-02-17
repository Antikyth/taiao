// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.poi;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.HiinakiBlock;
import antikyth.taiao.block.LongBlockHalf;
import antikyth.taiao.block.TaiaoBlocks;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

public class TaiaoPoiTypes {
	public static final RegistryKey<PointOfInterestType> WATERLOGGED_HIINAKI = register(
		Taiao.id("hiinaki"),
		0,
		1,
		state -> state.get(HiinakiBlock.HALF) == LongBlockHalf.FRONT && state.get(HiinakiBlock.WATERLOGGED),
		TaiaoBlocks.HIINAKI
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering POI types");
	}

	public static RegistryKey<PointOfInterestType> register(
		Identifier id,
		int tickets,
		int searchDistance,
		Predicate<BlockState> stateFilter,
		@NotNull Block... blocks
	) {
		return register(
			id,
			tickets,
			searchDistance,
			Arrays.stream(blocks)
				.flatMap(block -> block.getStateManager().getStates().stream())
				.filter(stateFilter)
				.collect(ImmutableSet.toImmutableSet())
		);
	}

	public static RegistryKey<PointOfInterestType> register(
		Identifier id,
		int tickets,
		int searchDistance,
		Set<BlockState> states
	) {
		PointOfInterestHelper.register(id, tickets, searchDistance, states);

		return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, id);
	}
}
