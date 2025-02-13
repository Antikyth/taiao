// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import antikyth.taiao.block.plant.TripleBlockPart;
import antikyth.taiao.block.plant.TripleTallPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaiaoEmiStacks {
	@Contract("_, _ -> new")
	public static @NotNull TallBlockEmiStack createTripleTallPlant(Block block, @NotNull BlockState baseState) {
		return new TallBlockEmiStack(
			block,
			List.of(
				new Pair<>(new BlockPos(0, 0, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.LOWER)),
				new Pair<>(new BlockPos(0, 1, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.MIDDLE)),
				new Pair<>(new BlockPos(0, 2, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.UPPER))
			)
		)
			.center(new BlockPos(0, 1, 0))
			.scale(0.5f)
			.describeAllStates(false)
			.hiddenProperties(TripleTallPlantBlock.PART);
	}
}
