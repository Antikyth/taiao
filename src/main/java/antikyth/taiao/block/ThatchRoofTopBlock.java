// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

public class ThatchRoofTopBlock extends AbstractThatchBlock {
	protected static final VoxelShape X_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 0f, 0f, 4f, 4f, 16f),
		createCuboidShape(3f, 3f, 0f, 13f, 7f, 16f),
		createCuboidShape(12f, 0f, 0f, 16f, 4f, 16f)
	);
	protected static final VoxelShape Z_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 0f, 0f, 16f, 4f, 4f),
		createCuboidShape(0f, 3f, 3f, 16f, 7f, 13f),
		createCuboidShape(0f, 0f, 12f, 16f, 4f, 16f)
	);

	protected ThatchRoofTopBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(FACING).getAxis() == Direction.Axis.X) return X_SHAPE;
		else return Z_SHAPE;
	}
}
