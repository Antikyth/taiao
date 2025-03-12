// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.block.entity.HaastsEagleNestBlockEntity;
import antikyth.taiao.block.entity.TaiaoBlockEntities;
import antikyth.taiao.block.state.HorizontalDoubleSquareBlockPart;
import antikyth.taiao.block.state.NestBlockContents;
import antikyth.taiao.block.state.TaiaoStateProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HaastsEagleNestBlock extends BlockWithEntity {
	public static final EnumProperty<HorizontalDoubleSquareBlockPart> PART = TaiaoStateProperties.HORIZONTAL_DOUBLE_SQUARE_BLOCK_PART;
	public static final EnumProperty<NestBlockContents> CONTENTS = TaiaoStateProperties.NEST_BLOCK_CONTENTS;

	protected static final VoxelShape NORTH_WEST_SHAPE = VoxelShapes.combineAndSimplify(
		createCuboidShape(2f, 0f, 2f, 16f, 8f, 16f),
		createCuboidShape(4f, 2f, 4f, 16f, 8f, 16f),
		BooleanBiFunction.ONLY_FIRST // subtraction
	);
	protected static final VoxelShape NORTH_EAST_SHAPE = VoxelShapes.combineAndSimplify(
		createCuboidShape(0f, 0f, 2f, 14f, 8f, 16f),
		createCuboidShape(0f, 2f, 4f, 12f, 8f, 16f),
		BooleanBiFunction.ONLY_FIRST // subtraction
	);
	protected static final VoxelShape SOUTH_WEST_SHAPE = VoxelShapes.combineAndSimplify(
		createCuboidShape(2f, 0f, 0f, 16f, 8f, 14f),
		createCuboidShape(4f, 2f, 0f, 16f, 8f, 12f),
		BooleanBiFunction.ONLY_FIRST // subtraction
	);
	protected static final VoxelShape SOUTH_EAST_SHAPE = VoxelShapes.combineAndSimplify(
		createCuboidShape(0f, 0f, 0f, 14f, 8f, 14f),
		createCuboidShape(0f, 2f, 0f, 12f, 8f, 12f),
		BooleanBiFunction.ONLY_FIRST // subtraction
	);

	// the egg happens to be centered, so it can be used for each part
	protected static final VoxelShape EGG_SHAPE = createCuboidShape(6d, 2d, 6d, 10d, 7d, 10d);
	protected static final VoxelShape NORTH_WEST_EGG_SHAPE = VoxelShapes.union(NORTH_WEST_SHAPE, EGG_SHAPE);
	protected static final VoxelShape NORTH_EAST_EGG_SHAPE = VoxelShapes.union(NORTH_EAST_SHAPE, EGG_SHAPE);
	protected static final VoxelShape SOUTH_WEST_EGG_SHAPE = VoxelShapes.union(SOUTH_WEST_SHAPE, EGG_SHAPE);
	protected static final VoxelShape SOUTH_EAST_EGG_SHAPE = VoxelShapes.union(SOUTH_EAST_SHAPE, EGG_SHAPE);

	public HaastsEagleNestBlock(Settings settings) {
		super(settings);

		this.setDefaultState(
			this.getDefaultState()
				.with(PART, HorizontalDoubleSquareBlockPart.NORTH_WEST)
				.with(CONTENTS, NestBlockContents.NONE)
		);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new HaastsEagleNestBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(
		BlockState state,
		@NotNull World world,
		BlockPos pos,
		PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		if (world.getBlockEntity(pos) instanceof HaastsEagleNestBlockEntity blockEntity) {
			ItemStack stack = player.getStackInHand(hand);

			if (blockEntity.hasEgg()) {
				// Remove egg
				if (!world.isClient) {
					ItemStack egg = blockEntity.removeEgg();

					if (!player.getInventory().insertStack(egg)) {
						player.dropItem(egg, false);
					}

					// Update contents state
					BlockState newState = state.with(CONTENTS, getContents(blockEntity));

					world.setBlockState(pos, newState, Block.NOTIFY_ALL);
					world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));

					return ActionResult.success(true);
				}

				return ActionResult.success(false);
			} else if (HaastsEagleNestBlockEntity.EGGS.contains(stack.getItem())) {
				// Add egg
				if (!world.isClient) {
					ItemStack egg = player.getAbilities().creativeMode ? stack.copy() : stack;

					if (blockEntity.addEgg(egg)) {
						// Update contents state
						BlockState newState = state.with(CONTENTS, getContents(blockEntity));

						world.setBlockState(pos, newState, Block.NOTIFY_ALL);
						world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));

						return ActionResult.success(true);
					}
				}

				return ActionResult.success(false);
			}

			// TODO: add feeding for chicks
		}

		return ActionResult.PASS;
	}

	@Override
	public boolean hasRandomTicks(@NotNull BlockState state) {
		return state.get(CONTENTS) == NestBlockContents.EGG;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
		// 1/chanceReciprocal chance to incubate
		int chanceReciprocal = 25;

		if (random.nextInt(chanceReciprocal) == 0) {
			world.getBlockEntity(pos, TaiaoBlockEntities.HAASTS_EAGLE_NEST).ifPresent(blockEntity -> {
				blockEntity.incubate(world, pos, state, random);
			});
		}
	}

	/**
	 * Returns the appropriate {@link NestBlockContents} based on the block entity's state.
	 */
	public static NestBlockContents getContents(@NotNull HaastsEagleNestBlockEntity blockEntity) {
		if (blockEntity.hasChick()) {
			return NestBlockContents.CHICK;
		} else if (blockEntity.hasEgg()) {
			return NestBlockContents.EGG;
		} else {
			return NestBlockContents.NONE;
		}
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getShape(state.get(PART), state.get(CONTENTS) == NestBlockContents.EGG);
	}

	// Give the egg no collision, so the adult eagle can still sit in the nest snugly
	@Override
	public VoxelShape getCollisionShape(
		@NotNull BlockState state,
		BlockView world,
		BlockPos pos,
		ShapeContext context
	) {
		return getShape(state.get(PART), false);
	}

	@Contract(pure = true)
	protected static VoxelShape getShape(@NotNull HorizontalDoubleSquareBlockPart part, boolean egg) {
		return switch (part) {
			case NORTH_WEST -> egg ? NORTH_WEST_EGG_SHAPE : NORTH_WEST_SHAPE;
			case NORTH_EAST -> egg ? NORTH_EAST_EGG_SHAPE : NORTH_EAST_SHAPE;
			case SOUTH_EAST -> egg ? SOUTH_EAST_EGG_SHAPE : SOUTH_EAST_SHAPE;
			case SOUTH_WEST -> egg ? SOUTH_WEST_EGG_SHAPE : SOUTH_WEST_SHAPE;
		};
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(PART, CONTENTS);
	}

	@Override
	public @Nullable BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		BlockPos pos = ctx.getBlockPos();
		World world = ctx.getWorld();
		Direction facing = ctx.getHorizontalPlayerFacing();
		HorizontalDoubleSquareBlockPart part = HorizontalDoubleSquareBlockPart.placement(facing);

		BlockPos.Mutable mutable = new BlockPos.Mutable();
		boolean canPlace = part.otherPlacements()
			.map(Pair::getLeft)
			.map(offset -> mutable.set(pos, offset))
			.allMatch(
				otherPos -> world.getWorldBorder().contains(otherPos) // within world border
					&& world.getBlockState(otherPos).canReplace(ctx) // replaceable
			);

		return canPlace ? this.getDefaultState().with(PART, part) : null;
	}

	@Override
	public void onPlaced(
		World world,
		BlockPos pos,
		@NotNull BlockState state,
		@Nullable LivingEntity placer,
		ItemStack itemStack
	) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		// Place each other part of the nest
		state.get(PART).otherPlacements().forEach(placement -> {
			BlockPos offset = placement.getLeft();
			HorizontalDoubleSquareBlockPart part = placement.getRight();

			mutable.set(pos, offset);
			world.setBlockState(mutable, state.with(PART, part), Block.NOTIFY_ALL);
		});
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
		HorizontalDoubleSquareBlockPart part = state.get(PART);
		HorizontalDoubleSquareBlockPart expected = part.getPartAtOffset(direction.getVector());

		// If there is a nest part expected at the neighbor position, ensure it matches.
		if (expected == null || (neighborState.isOf(this) && neighborState.get(PART) == expected)) {
			return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		} else {
			return Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient) {
			if (player.isCreative()) {
				HorizontalDoubleSquareBlockPart part = state.get(PART);

				// Break the north-west part without drops.
				if (part != HorizontalDoubleSquareBlockPart.NORTH_WEST) {
					BlockPos northWestPos = pos.add(part.offsetTo(HorizontalDoubleSquareBlockPart.NORTH_WEST));
					BlockState northWestState = world.getBlockState(northWestPos);

					if (northWestState.isOf(this) && northWestState.get(PART) == HorizontalDoubleSquareBlockPart.NORTH_WEST) {
						world.setBlockState(
							northWestPos,
							Blocks.AIR.getDefaultState(),
							Block.NOTIFY_ALL | Block.SKIP_DROPS
						);
						world.syncWorldEvent(
							player,
							WorldEvents.BLOCK_BROKEN,
							northWestPos,
							Block.getRawIdFromState(northWestState)
						);
					}
				}
			} else {
				dropStacks(state, world, pos, null, player, player.getMainHandStack());
			}
		}

		super.onBreak(world, pos, state, player);
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

	@Override
	public BlockState rotate(@NotNull BlockState state, BlockRotation rotation) {
		return state.with(PART, state.get(PART).rotate(rotation));
	}

	@Override
	public BlockState mirror(@NotNull BlockState state, BlockMirror mirror) {
		return state.with(PART, state.get(PART).mirror(mirror));
	}

	@Override
	public long getRenderingSeed(@NotNull BlockState state, BlockPos pos) {
		HorizontalDoubleSquareBlockPart part = state.get(PART);
		pos = part == HorizontalDoubleSquareBlockPart.NORTH_WEST
			? pos
			: pos.add(part.offsetTo(HorizontalDoubleSquareBlockPart.NORTH_WEST));

		return MathHelper.hashCode(pos);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		@NotNull World world,
		BlockState state,
		BlockEntityType<T> type
	) {
		return world.isClient
			? null
			: checkType(type, TaiaoBlockEntities.HAASTS_EAGLE_NEST, HaastsEagleNestBlockEntity::serverTick);
	}
}
