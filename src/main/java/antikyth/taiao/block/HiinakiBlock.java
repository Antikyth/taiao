// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.item.TaiaoItemTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: add stat for using
@SuppressWarnings("deprecation")
public class HiinakiBlock extends BlockWithEntity {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final EnumProperty<LongBlockHalf> HALF = TaiaoStateProperties.LONG_BLOCK_HALF;

	protected static final VoxelShape FRONT_SHAPE = createCuboidShape(0f, 0f, 0f, 16f, 16f, 16f);
	protected static final VoxelShape BACK_NORTH_SHAPE = VoxelShapes.union(
		createCuboidShape(2f, 2f, 0f, 14f, 14f, 13f),
		createCuboidShape(5f, 5f, 13f, 11f, 11f, 16f)
	);
	protected static final VoxelShape BACK_EAST_SHAPE = VoxelShapes.union(
		createCuboidShape(3f, 2f, 2f, 16f, 14f, 14f),
		createCuboidShape(0f, 5f, 5f, 3f, 11f, 11f)
	);
	protected static final VoxelShape BACK_SOUTH_SHAPE = VoxelShapes.union(
		createCuboidShape(2f, 2f, 3f, 14f, 14f, 16f),
		createCuboidShape(5f, 5f, 0f, 11f, 11f, 3f)
	);
	protected static final VoxelShape BACK_WEST_SHAPE = VoxelShapes.union(
		createCuboidShape(0f, 2f, 2f, 13f, 14f, 14f),
		createCuboidShape(13f, 5f, 5f, 16f, 11f, 11f)
	);

	protected HiinakiBlock(Settings settings) {
		super(settings);

		this.setDefaultState(
			this.getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(HALF, LongBlockHalf.FRONT)
		);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, @NotNull BlockState state) {
		// FIXME: is this allowable?
		return switch (state.get(HALF)) {
			case FRONT -> new HiinakiBlockEntity(pos, state);
			case BACK -> null;
		};
	}

	protected static @Nullable BlockEntity getBlockEntity(World world, BlockPos pos, @NotNull BlockState state) {
		if (state.get(HALF) == LongBlockHalf.BACK) {
			pos = pos.offset(LongBlockHalf.BACK.getDirectionTowardsOtherHalf(state.get(FACING)));
		}

		return world.getBlockEntity(pos);
	}

	@Override
	public ActionResult onUse(
		BlockState state,
		World world,
		BlockPos pos,
		PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		if (getBlockEntity(world, pos, state) instanceof HiinakiBlockEntity blockEntity) {
			ItemStack stack = player.getStackInHand(hand);
			if (blockEntity.hasTrappedEntity()) {
				if (!world.isClient && blockEntity.killTrappedEntity()) {
					// TODO: add use trap stat
					return ActionResult.success(true);
				}

				return ActionResult.success(false);
			} else if (blockEntity.hasBait()) {
				if (!world.isClient) {
					ItemStack bait = blockEntity.removeBait(player);

					if (!player.getInventory().insertStack(bait)) {
						player.dropItem(stack, false);
					}

					return ActionResult.success(true);
				}

				return ActionResult.success(false);
			} else if (stack.isIn(TaiaoItemTags.HIINAKI_BAIT)) {
				if (!world.isClient) {
					ItemStack bait = player.getAbilities().creativeMode ? stack.copy() : stack;

					if (blockEntity.addBait(player, bait)) {
						// TODO: add use bait stat
						return ActionResult.success(true);
					}
				}

				return ActionResult.success(false);
			}
		}

		return ActionResult.PASS;
	}

	@Override
	public BlockState getStateForNeighborUpdate(
		@NotNull BlockState state,
		Direction direction,
		BlockState neighborState,
		@NotNull WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		// Block is permanently waterlogged
		world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

		if (direction == state.get(HALF).getDirectionTowardsOtherHalf(state.get(FACING))) {
			if (neighborState.isOf(this) && neighborState.get(HALF) != state.get(HALF) && neighborState.get(FACING) == state.get(
				FACING)) {
				return state;
			} else {
				return Blocks.WATER.getDefaultState();
			}
		} else {
			return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		}
	}

	@Override
	public void onStateReplaced(
		@NotNull BlockState state,
		World world,
		BlockPos pos,
		@NotNull BlockState newState,
		boolean moved
	) {
		if (!newState.isOf(state.getBlock())) {
			if (getBlockEntity(world, pos, state) instanceof HiinakiBlockEntity blockEntity) {
				// Scatter bait
				ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), blockEntity.getBait());
				// Free trapped entity
				blockEntity.freeTrappedEntity(false);
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getStill(false);
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(HALF) == LongBlockHalf.BACK) {
			return switch (state.get(FACING)) {
				case EAST -> BACK_EAST_SHAPE;
				case SOUTH -> BACK_SOUTH_SHAPE;
				case WEST -> BACK_WEST_SHAPE;

				default -> BACK_NORTH_SHAPE;
			};
		} else {
			return FRONT_SHAPE;
		}
	}

	@Override
	public @Nullable BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		World world = ctx.getWorld();
		Direction playerFacing = ctx.getHorizontalPlayerFacing();

		BlockPos frontPos = ctx.getBlockPos();
		BlockPos backPos = frontPos.offset(playerFacing);

		FluidState frontFluidState = world.getFluidState(frontPos);
		FluidState backFluidState = world.getFluidState(backPos);

		if (
			// In water
			frontFluidState.isIn(FluidTags.WATER) && backFluidState.isIn(FluidTags.WATER)
				// Fully submerged
				&& frontFluidState.getLevel() == 8 && backFluidState.getLevel() == 8
				// Can place at the back position
				&& world.getBlockState(backPos).canReplace(ctx) && world.getWorldBorder().contains(backPos)
		) {
			Direction facing = playerFacing.getOpposite();

			return this.getDefaultState().with(FACING, facing);
		} else {
			return null;
		}
	}

	@Override
	public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient && player.isCreative()) {
			LongBlockHalf half = state.get(HALF);

			// Break the back half
			if (half == LongBlockHalf.FRONT) {
				BlockPos backPos = pos.offset(half.getDirectionTowardsOtherHalf(state.get(FACING)));
				BlockState backState = world.getBlockState(backPos);

				if (backState.isOf(this) && backState.get(HALF) == LongBlockHalf.BACK) {
					world.setBlockState(backPos, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
					world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, backPos, Block.getRawIdFromState(backState));
				}
			}
		}

		super.onBreak(world, pos, state, player);
	}

	@Override
	public void onPlaced(
		World world,
		BlockPos pos,
		BlockState state,
		@Nullable LivingEntity placer,
		ItemStack itemStack
	) {
		super.onPlaced(world, pos, state, placer, itemStack);

		// Place the back half
		if (!world.isClient) {
			BlockPos backPos = pos.offset(LongBlockHalf.FRONT.getDirectionTowardsOtherHalf(state.get(FACING)));

			world.setBlockState(backPos, state.with(HALF, LongBlockHalf.BACK), Block.NOTIFY_ALL);

			world.updateNeighbors(pos, Blocks.AIR);
			state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
		}
	}

	@Override
	public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public long getRenderingSeed(@NotNull BlockState state, BlockPos pos) {
		if (state.get(HALF) == LongBlockHalf.FRONT) {
			return super.getRenderingSeed(state, pos);
		} else {
			return super.getRenderingSeed(
				state,
				pos.offset(LongBlockHalf.BACK.getDirectionTowardsOtherHalf(state.get(FACING)))
			);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(FACING);
		builder.add(HALF);
	}
}
