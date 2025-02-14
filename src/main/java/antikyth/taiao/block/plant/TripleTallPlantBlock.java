// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import antikyth.taiao.block.CustomPlacementBlock;
import antikyth.taiao.block.TaiaoStateProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TripleTallPlantBlock extends PlantBlock implements CustomPlacementBlock {
	public static final EnumProperty<TripleBlockPart> TRIPLE_BLOCK_PART = TaiaoStateProperties.TRIPLE_BLOCK_PART;

	public TripleTallPlantBlock(AbstractBlock.Settings settings) {
		super(settings);

		this.setDefaultState(this.getDefaultState().with(TRIPLE_BLOCK_PART, TripleBlockPart.LOWER));
	}

	@Override
	public BlockState getStateForNeighborUpdate(
		@NotNull BlockState state,
		@NotNull Direction direction,
		BlockState neighborState,
		WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		TripleBlockPart part = state.get(TRIPLE_BLOCK_PART);

		if (
			// Unaffected by horizontally adjacent changes
			direction.getAxis().isHorizontal()
				// Unaffected by changes above
				|| (part == TripleBlockPart.UPPER && direction == Direction.UP)
				// Changes below are okay if we can still be placed on that block
				|| (part == TripleBlockPart.LOWER && direction == Direction.DOWN && state.canPlaceAt(world, pos))
		) {
			return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		} else if (
			// Valid connections to other parts of the same block are okay
			this.canConnect(state, neighborState, direction)
		) {
			return this.getStateForConnectionUpdate(state, direction, neighborState, world, pos, neighborPos);
		} else {
			return Blocks.AIR.getDefaultState();
		}
	}

	/**
	 * Returns the {@link BlockState} after a connection is updated and is still valid.
	 * <p>
	 * The neighbor will be of the same block and will have a valid {@link TripleBlockPart} connection to this part.
	 * <p>
	 * By default, this returns
	 * {@link PlantBlock#getStateForNeighborUpdate(BlockState, Direction, BlockState, WorldAccess, BlockPos, BlockPos) super.getStateForNeighborUpdate(...)},
	 * but this may be overridden by subclasses; e.g., to sync state changes throughout the plant.
	 */
	protected BlockState getStateForConnectionUpdate(
		BlockState state,
		Direction direction,
		BlockState neighborState,
		WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public @Nullable BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		BlockPos pos = ctx.getBlockPos();
		World world = ctx.getWorld();

		// Enough room for all 3 parts
		if (pos.getY() > world.getTopY() - 3) return null;
		// Middle part is placeable
		if (!world.getBlockState(pos.up(1)).canReplace(ctx)) return null;
		// Upper part is placeable
		if (!world.getBlockState(pos.up(2)).canReplace(ctx)) return null;

		BlockState state = super.getPlacementState(ctx);

		if (state != null) return TallPlantBlock.withWaterloggedState(world, pos, state);
		else return null;
	}

	@Override
	public void onPlaced(
		@NotNull World world,
		@NotNull BlockPos pos,
		@NotNull BlockState state,
		@Nullable LivingEntity placer,
		ItemStack itemStack
	) {
		BlockPos middlePos = pos.up();
		BlockPos upperPos = middlePos.up();

		BlockState middleState = state.with(TRIPLE_BLOCK_PART, TripleBlockPart.MIDDLE);
		BlockState upperState = state.with(TRIPLE_BLOCK_PART, TripleBlockPart.UPPER);

		world.setBlockState(
			middlePos,
			TallPlantBlock.withWaterloggedState(world, middlePos, middleState),
			Block.NOTIFY_ALL
		);
		world.setBlockState(
			upperPos,
			TallPlantBlock.withWaterloggedState(world, upperPos, upperState),
			Block.NOTIFY_ALL
		);
	}

	@Override
	public boolean placeAt(@NotNull WorldAccess world, @NotNull BlockState state, @NotNull BlockPos pos, int flags) {
		BlockPos middlePos = pos.up();
		BlockPos upperPos = middlePos.up();

		// Enough room for all 3 parts
		if (pos.getY() > world.getTopY() - 3) return false;
		// Middle part is placeable
		if (!world.getBlockState(middlePos).isReplaceable()) return false;
		// Upper part is placeable
		if (!world.getBlockState(upperPos).isReplaceable()) return false;

		world.setBlockState(
			pos,
			TallPlantBlock.withWaterloggedState(world, pos, state.with(TRIPLE_BLOCK_PART, TripleBlockPart.LOWER)),
			flags
		);
		world.setBlockState(
			middlePos,
			TallPlantBlock.withWaterloggedState(
				world,
				middlePos,
				state.with(TRIPLE_BLOCK_PART, TripleBlockPart.MIDDLE)
			),
			flags
		);
		world.setBlockState(
			upperPos,
			TallPlantBlock.withWaterloggedState(world, upperPos, state.with(TRIPLE_BLOCK_PART, TripleBlockPart.UPPER)),
			flags
		);

		return true;
	}

	@Override
	public boolean canPlaceAt(@NotNull BlockState state, WorldView world, BlockPos pos) {
		if (state.get(TRIPLE_BLOCK_PART) == TripleBlockPart.LOWER) return super.canPlaceAt(state, world, pos);

		return canConnect(state, world.getBlockState(pos.down()), Direction.DOWN);
	}

	@Override
	public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient) {
			if (player.isCreative()) {
				onBreakInCreative(world, pos, state, player);
			} else {
				dropStacks(state, world, pos, null, player, player.getMainHandStack());
			}
		}

		super.onBreak(world, pos, state, player);
	}

	// TODO: does this need to handle upper/middle parts too?
	//     : why does the vanilla double tall plant only handle the lower part?
	protected static void onBreakInCreative(World world, BlockPos pos, @NotNull BlockState state, PlayerEntity player) {
		TripleBlockPart part = state.get(TRIPLE_BLOCK_PART);
		BlockPos lowerPos;

		if (part == TripleBlockPart.MIDDLE) lowerPos = pos.down(1);
		else if (part == TripleBlockPart.UPPER) lowerPos = pos.down(2);
		else return;

		BlockState lowerState = world.getBlockState(lowerPos);

		if (lowerState.isOf(state.getBlock()) && lowerState.get(TRIPLE_BLOCK_PART) == TripleBlockPart.LOWER) {
			boolean isWater = lowerState.getFluidState().isOf(Fluids.WATER);
			BlockState replacementState = isWater ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();

			world.setBlockState(lowerPos, replacementState, Block.NOTIFY_ALL | Block.SKIP_DROPS);
			world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, lowerPos, Block.getRawIdFromState(lowerState));
		}
	}

	@Override
	public void afterBreak(
		World world,
		PlayerEntity player,
		BlockPos pos,
		BlockState state,
		@Nullable BlockEntity blockEntity,
		ItemStack tool
	) {
		super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
	}

	/**
	 * Whether this part connects to the {@code otherState} in the given {@code direction} (i.e. it is the correct part).
	 */
	protected boolean canConnect(BlockState state, @NotNull BlockState otherState, Direction direction) {
		if (!otherState.isOf(this)) return false;

		TripleBlockPart part = state.get(TRIPLE_BLOCK_PART);
		TripleBlockPart otherPart = otherState.get(TRIPLE_BLOCK_PART);

		if (direction == Direction.UP) {
			if (part == TripleBlockPart.LOWER && otherPart == TripleBlockPart.MIDDLE) return true;
			return part == TripleBlockPart.MIDDLE && otherPart == TripleBlockPart.UPPER;
		} else if (direction == Direction.DOWN) {
			if (part == TripleBlockPart.UPPER && otherPart == TripleBlockPart.MIDDLE) return true;
			return part == TripleBlockPart.MIDDLE && otherPart == TripleBlockPart.LOWER;
		}

		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(TRIPLE_BLOCK_PART);
	}

	@Override
	public long getRenderingSeed(@NotNull BlockState state, BlockPos pos) {
		// The rendering seed of the upper and middle parts should match that of the lower part.
		int offset;
		switch (state.get(TRIPLE_BLOCK_PART)) {
			case UPPER -> offset = 2;
			case MIDDLE -> offset = 1;
			default -> offset = 0;
		}

		return MathHelper.hashCode(pos.down(offset));
	}
}
