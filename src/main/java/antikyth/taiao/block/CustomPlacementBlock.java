// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public interface CustomPlacementBlock {
	/**
	 * Attempts to place this block.
	 *
	 * @return {@code true} if the block was able to be placed at this position
	 */
	boolean placeAt(WorldAccess world, BlockState state, BlockPos pos, int flags);
}
