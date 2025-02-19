// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

public class ThatchRoofBlock extends AbstractThatchBlock {
	protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 0f, 0f, 16f, 4f, 4f),
		createCuboidShape(0f, 3f, 3f, 16f, 7f, 7f),
		createCuboidShape(0f, 6f, 6f, 16f, 10f, 10f),
		createCuboidShape(0f, 9f, 9f, 16f, 13f, 13f),
		createCuboidShape(0f, 12f, 12f, 16f, 16f, 16f)
	);
	protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
		createCuboidShape(12f, 0f, 0f, 16f, 4f, 16f),
		createCuboidShape(9f, 3f, 0f, 13f, 7f, 16f),
		createCuboidShape(6f, 6f, 0f, 10f, 10f, 16f),
		createCuboidShape(3f, 9f, 0f, 7f, 13f, 16f),
		createCuboidShape(0f, 12f, 0f, 4f, 16f, 16f)
	);
	protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 0f, 12f, 16f, 4f, 16f),
		createCuboidShape(0f, 3f, 9f, 16f, 7f, 13f),
		createCuboidShape(0f, 6f, 6f, 16f, 10f, 10f),
		createCuboidShape(0f, 9f, 3f, 16f, 13f, 7f),
		createCuboidShape(0f, 12f, 0f, 16f, 16f, 4f)
	);
	protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 0f, 0f, 4f, 4f, 16f),
		createCuboidShape(3f, 3f, 0f, 7f, 7f, 16f),
		createCuboidShape(6f, 6f, 0f, 10f, 10f, 16f),
		createCuboidShape(9f, 9f, 0f, 13f, 13f, 16f),
		createCuboidShape(12f, 12f, 0f, 16f, 16f, 16f)
	);

	protected ThatchRoofBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(FACING)) {
			case NORTH -> NORTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;

			default -> throw new IllegalStateException("horizontal facing block facing vertical direction");
		};
	}
}
