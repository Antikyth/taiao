// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import antikyth.taiao.block.TaiaoStateProperties;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HarvestableTripleTallPlantBlock extends TripleTallPlantBlock implements Fertilizable {
	public static final BooleanProperty HARVESTABLE = TaiaoStateProperties.HARVESTABLE;

	public HarvestableTripleTallPlantBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.getDefaultState().with(HARVESTABLE, true));
	}

	@Override
	protected BlockState getDefaultPlacementState() {
		return this.getDefaultState().with(HARVESTABLE, false);
	}

	@Nullable
	protected SoundEvent getShearSound() {
		return null;
	}

	public void dropHarvest(World world, BlockPos pos) {
		dropStack(world, pos, new ItemStack(this, 3));
	}

	/**
	 * Whether the plant is receiving enough light to grow.
	 * <p>
	 * The light level is determined at the block above the topmost part of the plant, i.e., 3 blocks above the base.
	 */
	protected static boolean receivingEnoughLight(@NotNull World world, @NotNull BlockPos pos) {
		return world.getBaseLightLevel(pos.up(3), 0) >= 9;
	}

	@Override
	public boolean hasRandomTicks(@NotNull BlockState state) {
		return state.get(TRIPLE_BLOCK_PART) == TripleBlockPart.LOWER && !state.get(HARVESTABLE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, ServerWorld world, BlockPos pos, Random random) {
		// A 1/chanceReciprocal chance to grow
		// This is 3x less likely than berry bushes' chance to grow, as they have 3x more growth
		// stages to cover
		int chanceReciprocal = 15;

		if (hasRandomTicks(state) && random.nextInt(chanceReciprocal) == 0 && receivingEnoughLight(world, pos)) {
			BlockState lowerState = state.with(HARVESTABLE, true);

			world.setBlockState(pos, lowerState, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(lowerState));
		}
	}

	@Override
	protected BlockState getStateForConnectionUpdate(
		@NotNull BlockState state,
		Direction direction,
		@NotNull BlockState neighborState,
		WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		return state.with(HARVESTABLE, neighborState.get(HARVESTABLE));
	}

	@Override
	public ActionResult onUse(
		@NotNull BlockState state,
		World world,
		BlockPos pos,
		@NotNull PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		ItemStack stack = player.getStackInHand(hand);

		if (state.get(HARVESTABLE) && stack.isIn(ConventionalItemTags.SHEARS)) {
			if (player instanceof ServerPlayerEntity serverPlayer) {
				Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			}

			harvest(world, pos, state, player);

			// Damage shears
			stack.damage(1, player, player_ -> player_.sendToolBreakStatus(hand));
			if (!world.isClient()) player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}

	public boolean harvest(@NotNull World world, BlockPos pos, @NotNull BlockState state, @Nullable Entity harvester) {
		if (!state.get(HARVESTABLE)) return false;

		world.setBlockState(
			pos,
			state.with(HARVESTABLE, false),
			Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD
		);

		// Play shear sound
		SoundEvent sound = this.getShearSound();
		if (sound != null) {
			world.playSound(
				harvester instanceof PlayerEntity player ? player : null,
				harvester == null ? pos.getX() : harvester.getX(),
				harvester == null ? pos.getY() : harvester.getY(),
				harvester == null ? pos.getZ() : harvester.getZ(),
				sound,
				SoundCategory.BLOCKS,
				1f,
				1f
			);
		}
		// Drop harvested items
		dropHarvest(world, pos);

		world.emitGameEvent(harvester, GameEvent.BLOCK_CHANGE, pos);

		return true;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return !state.get(HARVESTABLE);
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(@NotNull ServerWorld world, Random random, BlockPos pos, @NotNull BlockState state) {
		world.setBlockState(pos, state.with(HARVESTABLE, true), Block.NOTIFY_LISTENERS);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(HARVESTABLE);
	}
}
