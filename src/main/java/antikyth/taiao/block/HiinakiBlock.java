// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.block.entity.HiinakiDummyBlockEntity;
import antikyth.taiao.block.entity.TaiaoBlockEntities;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.stat.TaiaoStats;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: add stat for using
@SuppressWarnings("deprecation")
public class HiinakiBlock extends BlockWithEntity {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
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
				.with(WATERLOGGED, false)
		);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, @NotNull BlockState state) {
		return switch (state.get(HALF)) {
			case FRONT -> new HiinakiBlockEntity(pos, state);
			case BACK -> new HiinakiDummyBlockEntity(pos, state);
		};
	}

	protected static @Nullable BlockEntity getBlockEntity(BlockView world, BlockPos pos, @NotNull BlockState state) {
		if (state.get(HALF) == LongBlockHalf.BACK) {
			pos = pos.offset(LongBlockHalf.BACK.getDirectionTowardsOtherHalf(state.get(FACING)));
		}

		return world.getBlockEntity(pos);
	}

	/**
	 * {@return the offset towards the back of the hīnaki for rendering contents like bait, trapped entities, or particles}
	 */
	public static float getContentsOffset() {
		return 0.675f;
	}

	/**
	 * Returns the yaw in degrees relative to {@link Direction#NORTH}.
	 * <p>
	 * This is used for rendering contents and setting the yaw of
	 * {@linkplain HiinakiBlockEntity#releaseEntity(boolean, boolean) released entities}.
	 */
	@Contract(pure = true)
	public static float getYaw(@NotNull Direction facing) {
		return (-facing.asRotation() + 180f) % 360f;
	}

	// Spawn bait particles when the trap is active
	@Override
	public void randomDisplayTick(
		@NotNull BlockState state,
		@NotNull World world,
		@NotNull BlockPos pos,
		@NotNull Random random
	) {
		// A 1/chanceReciprocal chance to spawn
		int chanceReciprocal = 5;

		if (random.nextInt(chanceReciprocal) == 0 && world.getBlockEntity(pos) instanceof HiinakiBlockEntity blockEntity) {
			if (state.get(WATERLOGGED) && blockEntity.hasBait()) {
				Direction facing = state.get(FACING);
				Vec3d modelOffset = state.getModelOffset(world, pos);
				Vec3d offset = new Vec3d(
					0d,
					0d,
					getContentsOffset()
				).rotateY(Taiao.degreesToRadians(getYaw(facing)));

				double x = pos.getX() + 0.5d + modelOffset.x + offset.x;
				double y = pos.getY() + 0.5d + modelOffset.y + offset.y;
				double z = pos.getZ() + 0.5d + modelOffset.z + offset.z;

				int count = random.nextInt(3) + 1;
				double strength = 0.4d;

				for (int i = 0; i < count; i++) {
					double xVelocity = ((double) random.nextFloat() - 0.5d) * strength;
					double yVelocity = ((double) random.nextFloat() - 0.5d) * strength;
					double zVelocity = ((double) random.nextFloat() - 0.5d) * strength;

					world.addParticle(
						new ItemStackParticleEffect(ParticleTypes.ITEM, blockEntity.getBait()),
						x,
						y,
						z,
						xVelocity,
						yVelocity,
						zVelocity
					);
				}
			}
		}
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
				if (!world.isClient && blockEntity.tryKillTrappedEntity(false, 5f, player)) {
					player.incrementStat(TaiaoStats.HIINAKI_TRAPPED_ENTITY_HARMED);

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
						player.incrementStat(TaiaoStats.HIINAKI_BAIT_ADDED);

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
		if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

		if (direction == state.get(HALF).getDirectionTowardsOtherHalf(state.get(FACING))) {
			if (
				neighborState.isOf(this)
					&& neighborState.get(HALF) != state.get(HALF)
					&& neighborState.get(FACING) == state.get(FACING)
			) {
				// Correct placement
				return state;
			} else {
				// Disconnected parts, so break
				return Blocks.AIR.getDefaultState();
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
				blockEntity.releaseEntity(true, true);
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
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

		// Ensure we can place the back part
		if (world.getBlockState(backPos).canReplace(ctx) && world.getWorldBorder().contains(backPos)) {
			Direction facing = playerFacing.getOpposite();

			return this.getDefaultState().with(FACING, facing).with(WATERLOGGED, world.isWater(frontPos));
		} else {
			return null;
		}
	}

	@Override
	public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient) {
			if (getBlockEntity(world, pos, state) instanceof HiinakiBlockEntity blockEntity) {
				if (blockEntity.hasTrappedEntity()) {
					player.incrementStat(TaiaoStats.HIINAKI_TRAPPED_ENTITY_FREED);
				}
			}

			if (player.isCreative()) {
				LongBlockHalf half = state.get(HALF);
				Direction facing = state.get(FACING);

				// Break the front half
				if (half == LongBlockHalf.BACK) {
					BlockPos otherPos = pos.offset(half.getDirectionTowardsOtherHalf(state.get(FACING)));
					BlockState otherState = world.getBlockState(otherPos);
					Direction otherFacing = otherState.get(FACING);

					if (otherState.isOf(this) && otherState.get(HALF) == half.getOtherHalf() && facing == otherFacing) {
						world.setBlockState(
							otherPos,
							otherState.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState(),
							Block.NOTIFY_ALL | Block.SKIP_DROPS
						);
						world.syncWorldEvent(
							player,
							WorldEvents.BLOCK_BROKEN,
							otherPos,
							Block.getRawIdFromState(otherState)
						);
					}
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

			world.setBlockState(
				backPos,
				state.with(HALF, LongBlockHalf.BACK).with(WATERLOGGED, world.isWater(backPos)),
				Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD
			);

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
		builder.add(WATERLOGGED);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		@NotNull World world,
		BlockState state,
		BlockEntityType<T> type
	) {
//		return checkType(
//			type,
//			TaiaoBlockEntities.HIINAKI,
//			world.isClient ? HiinakiBlockEntity::clientTick : HiinakiBlockEntity::serverTick
//		);

		return world.isClient ? null : checkType(type, TaiaoBlockEntities.HIINAKI, HiinakiBlockEntity::serverTick);
	}
}
