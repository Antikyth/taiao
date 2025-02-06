// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.block.CustomPlacementBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.NotNull;

public class CustomPlacementBlockFeature extends Feature<SimpleBlockFeatureConfig> {
	public CustomPlacementBlockFeature() {
		super(SimpleBlockFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(@NotNull FeatureContext<SimpleBlockFeatureConfig> context) {
		SimpleBlockFeatureConfig config = context.getConfig();
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		BlockState state = config.toPlace().get(context.getRandom(), pos);

		if (state.canPlaceAt(world, pos)) {
			if (state.getBlock() instanceof CustomPlacementBlock block) {
				return block.placeAt(world, state, pos, Block.NOTIFY_LISTENERS);
			} else {
				world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);

				return true;
			}
		}

		return false;
	}
}
