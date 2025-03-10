// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.block.entity.HaastsEagleNestBlockEntity;
import antikyth.taiao.block.state.HorizontalDoubleSquareBlockPart;
import antikyth.taiao.block.state.TaiaoStateProperties;
import antikyth.taiao.item.TaiaoItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HaastsEagleNestBlock extends BlockWithEntity {
	public static final EnumProperty<HorizontalDoubleSquareBlockPart> PART = TaiaoStateProperties.HORIZONTAL_DOUBLE_SQUARE_BLOCK_PART;

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

	public HaastsEagleNestBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.getDefaultState().with(PART, HorizontalDoubleSquareBlockPart.NORTH_WEST));
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
					ItemStack egg = blockEntity.removeEgg(player);

					if (!player.getInventory().insertStack(egg)) {
						player.dropItem(egg, false);
					}

					return ActionResult.success(true);
				}

				return ActionResult.success(false);
			} else if (stack.isOf(TaiaoItems.HAASTS_EAGLE_EGG)) {
				// Add egg
				if (!world.isClient) {
					ItemStack egg = player.getAbilities().creativeMode ? stack.copy() : stack;

					if (blockEntity.addEgg(player, egg)) {
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
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(PART)) {
			case NORTH_EAST -> NORTH_EAST_SHAPE;
			case NORTH_WEST -> NORTH_WEST_SHAPE;
			case SOUTH_EAST -> SOUTH_EAST_SHAPE;
			case SOUTH_WEST -> SOUTH_WEST_SHAPE;
		};
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(PART);
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
}
