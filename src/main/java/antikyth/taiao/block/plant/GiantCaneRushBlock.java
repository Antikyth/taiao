// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class GiantCaneRushBlock extends TripleTallPlantBlock {
	protected static final VoxelShape NORMAL_SHAPE = GiantCaneRushBlock.createCuboidShape(1d, 0d, 1d, 15d, 16d, 15d);
	protected static final VoxelShape UPPER_SHAPE = GiantCaneRushBlock.createCuboidShape(1d, 0d, 1d, 15d, 12d, 15d);

	public GiantCaneRushBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(TRIPLE_BLOCK_PART) == TripleBlockPart.UPPER ? UPPER_SHAPE : NORMAL_SHAPE;
	}
}
