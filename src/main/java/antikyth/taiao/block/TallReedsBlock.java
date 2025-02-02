// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

/**
 * An optionally-waterloggable tall plant block.
 */
@SuppressWarnings("deprecation")
public class TallReedsBlock extends TallPlantBlock implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public TallReedsBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
	}

	@Override
	protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
		if (floor.isIn(TaiaoBlockTags.REEDS_PLANTABLE_ON)) {
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			if (world.getFluidState(mutable.set(pos, Direction.UP)).isIn(FluidTags.WATER)) {
				// If the block above is water, then the reeds will be waterlogged, and thereby hydrated.
				return true;
			} else {
				// Check if any adjacent blocks can hydrate the reeds.
				for (Direction direction : Direction.Type.HORIZONTAL) {
					mutable.set(pos, direction);

					FluidState adjacentFluid = world.getFluidState(mutable);
					BlockState adjacentState = world.getBlockState(mutable);

					if (adjacentFluid.isIn(FluidTags.WATER) || adjacentState.isIn(TaiaoBlockTags.HYDRATES_REEDS)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public BlockState getStateForNeighborUpdate(
		@NotNull BlockState state,
		Direction direction,
		BlockState neighborState,
		WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(WATERLOGGED);
	}
}
