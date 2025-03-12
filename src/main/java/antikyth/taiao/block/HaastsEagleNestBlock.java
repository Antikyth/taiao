// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.block.entity.HaastsEagleNestBlockEntity;
import antikyth.taiao.block.entity.TaiaoBlockEntities;
import antikyth.taiao.block.state.HaastsEagleEggStage;
import antikyth.taiao.block.state.HorizontalDoubleSquareBlockPart;
import antikyth.taiao.block.state.TaiaoStateProperties;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

import java.util.Optional;

@SuppressWarnings("deprecation")
public class HaastsEagleNestBlock extends BlockWithEntity {
	public static final EnumProperty<HorizontalDoubleSquareBlockPart> PART = TaiaoStateProperties.HORIZONTAL_DOUBLE_SQUARE_BLOCK_PART;
	public static final EnumProperty<HaastsEagleEggStage> EGG_STAGE = TaiaoStateProperties.EGG_STAGE;

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
				.with(EGG_STAGE, HaastsEagleEggStage.NONE)
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
		@NotNull PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		boolean hasChick = world.getBlockEntity(pos, TaiaoBlockEntities.HAASTS_EAGLE_NEST)
			.map(HaastsEagleNestBlockEntity::hasChick)
			.orElse(false);
		ItemStack stack = player.getStackInHand(hand);

		// TODO: feed chicks
		if (!hasChick) {
			HaastsEagleEggStage stage = state.get(EGG_STAGE);

			if (stage.hasEgg()) {
				// Remove egg
				if (!world.isClient) {
					updateState(world, pos, state.with(EGG_STAGE, HaastsEagleEggStage.NONE), player);

					ItemStack egg = stage.getEgg();
					if (!player.getInventory().insertStack(egg)) {
						player.dropItem(egg, false);
					}

					return ActionResult.success(true);
				}

				return ActionResult.success(false);
			} else if (!stack.isEmpty()) {
				HaastsEagleEggStage eggStage = HaastsEagleEggStage.EGG_TO_STAGE.get(stack.getItem());

				if (eggStage != null) {
					// Add egg
					if (!world.isClient) {
						if (!player.getAbilities().creativeMode) stack.decrement(1);

						updateState(world, pos, state.with(EGG_STAGE, eggStage), player);

						return ActionResult.success(true);
					}

					return ActionResult.success(false);
				}
			}
		}

		return ActionResult.PASS;
	}

	@Override
	public boolean hasRandomTicks(@NotNull BlockState state) {
		return state.get(EGG_STAGE).hasEgg();
	}

	@Override
	public void randomTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, @NotNull Random random) {
		HaastsEagleEggStage stage = state.get(EGG_STAGE);
		// 1/chanceReciprocal chance to incubate
		int chanceReciprocal = 25;

		if (stage.hasEgg() && random.nextInt(chanceReciprocal) == 0) {
			Optional<HaastsEagleNestBlockEntity> blockEntity = world.getBlockEntity(
				pos,
				TaiaoBlockEntities.HAASTS_EAGLE_NEST
			);
			boolean hasChick = blockEntity.map(HaastsEagleNestBlockEntity::hasChick).orElse(false);

			// Only hatch if there isn't an existing chick (there shouldn't be but just to be
			// sure)
			if (!stage.isReadyToHatch() || !hasChick) {
				SoundEvent sound = stage.isReadyToHatch()
					? TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_HATCH
					: TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_CRACK;
				world.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);

				updateState(world, pos, state.with(EGG_STAGE, stage.nextStage()));

				// Hatch a chick if this was the final stage
				if (stage.isReadyToHatch()) {
					blockEntity.ifPresent(nest -> nest.hatchChick(random));
				}
			}
		}
	}

	protected static void updateState(@NotNull World world, BlockPos pos, BlockState newState) {
		updateState(world, pos, newState, null);
	}

	protected static void updateState(
		@NotNull World world,
		BlockPos pos,
		BlockState newState,
		@Nullable Entity source
	) {
		world.setBlockState(pos, newState, Block.NOTIFY_ALL);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(source, newState));
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getShape(state.get(PART), state.get(EGG_STAGE).hasEgg());
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

		builder.add(PART, EGG_STAGE);
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
		// FIXME: ???? sometimes breaking the northwest corner works, sometimes it doesn't and
		//      : drops an extra egg instead, even with if the eggs are all in the same positions as
		//      : when it did work

		if (!world.isClient) {
			HorizontalDoubleSquareBlockPart part = state.get(PART);

			// Drops for the block broken are done here instead of in `afterBreak`, unsure why, but
			// it is required when the other parts are broken in this method (vanilla
			// `TallPlantBlock`s do the same), otherwise the drops don't work quite right.
			if (!player.isCreative()) {
				dropStacks(state, world, pos, null, player, player.getMainHandStack());
			}

			// Break the other parts without drops
			BlockPos.Mutable mutable = new BlockPos.Mutable();
			part.otherPlacements().forEach(placement -> {
				BlockPos offset = placement.getLeft();
				HorizontalDoubleSquareBlockPart otherPart = placement.getRight();

				mutable.set(pos, offset);
				BlockState otherState = world.getBlockState(mutable);

				if (otherState.isOf(this) && otherState.get(PART) == otherPart) {
					world.setBlockState(
						mutable,
						Blocks.AIR.getDefaultState(),
						Block.NOTIFY_ALL | Block.SKIP_DROPS
					);
					world.syncWorldEvent(
						player,
						WorldEvents.BLOCK_BROKEN,
						mutable,
						Block.getRawIdFromState(otherState)
					);

					// Drops are done here in survival so that the tool used is applied to all parts
					if (!player.isCreative()) {
						dropStacks(otherState, world, pos, null, player, player.getMainHandStack());
					}
				}
			});
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
