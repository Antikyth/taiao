// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain.task;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.plant.HarakekeBlock;
import antikyth.taiao.block.plant.HarvestableTripleTallPlantBlock;
import antikyth.taiao.block.plant.TripleBlockPart;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarvestHarakekeTask extends MultiTickTask<VillagerEntity> {
	protected static final long RESPONSE_INTERVAL = 40L;

	protected final float speed;
	protected final int maxRuntime;

	protected int runtime;
	protected long nextResponseTime;

	protected final List<BlockPos> targetPositions = Lists.newArrayList();
	@Nullable
	protected BlockPos currentTarget;

	public HarvestHarakekeTask(float speed) {
		this(speed, 200);
	}

	public HarvestHarakekeTask(float speed, int maxRuntime) {
		super(
			ImmutableMap.of(
				MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleState.VALUE_PRESENT
			)
		);

		this.speed = speed;
		this.maxRuntime = maxRuntime;
	}

	@Override
	protected boolean shouldRun(@NotNull ServerWorld world, VillagerEntity villager) {
		if (!world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) return false;
		if (villager.getVillagerData().getProfession() != VillagerProfession.SHEPHERD) return false;

		BlockPos startPos = villager.getBlockPos();
		BlockPos range = new BlockPos(1, 1, 1);

		this.targetPositions.clear();

		for (BlockPos pos : BlockPos.iterate(startPos.subtract(range), startPos.add(range))) {
			if (isSuitableTarget(world.getBlockState(pos))) {
				this.targetPositions.add(new BlockPos(pos));
			}
		}

		this.currentTarget = this.chooseRandomTarget(world);
		return this.currentTarget != null;
	}

	protected @Nullable BlockPos chooseRandomTarget(@NotNull World world) {
		Random random = world.getRandom();

		return this.targetPositions.isEmpty() ? null
			: this.targetPositions.get(random.nextInt(this.targetPositions.size()));
	}

	private static boolean isSuitableTarget(@NotNull BlockState state) {
		return state.isOf(TaiaoBlocks.HARAKEKE)
			&& state.get(HarakekeBlock.TRIPLE_BLOCK_PART) == TripleBlockPart.LOWER
			&& state.get(HarakekeBlock.HARVESTABLE);
	}

	@Override
	protected void run(ServerWorld world, VillagerEntity villager, long time) {
		if (time > this.nextResponseTime && this.currentTarget != null) {
			this.rememberTargets(villager.getBrain(), this.currentTarget);
		}
	}

	protected void rememberTargets(@NotNull Brain<VillagerEntity> brain, @NotNull BlockPos currentTarget) {
		// Looks at the middle part of the harakeke
		brain.remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(currentTarget.up()));
		// But walks to the lower part of the harakeke
		brain.remember(
			MemoryModuleType.WALK_TARGET,
			new WalkTarget(new BlockPosLookTarget(currentTarget), this.speed, 1)
		);
	}

	@Override
	protected void finishRunning(ServerWorld world, @NotNull VillagerEntity villager, long time) {
		Brain<VillagerEntity> brain = villager.getBrain();

		brain.forget(MemoryModuleType.LOOK_TARGET);
		brain.forget(MemoryModuleType.WALK_TARGET);

		this.runtime = 0;
		this.nextResponseTime = time + RESPONSE_INTERVAL;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, VillagerEntity entity, long time) {
		return this.runtime < this.maxRuntime;
	}

	@Override
	protected void keepRunning(ServerWorld world, VillagerEntity villager, long time) {
		if (this.currentTarget == null || this.currentTarget.isWithinDistance(villager.getPos(), 1d)) {
			this.runtime++;

			if (this.currentTarget != null && time > this.nextResponseTime) {
				BlockState state = world.getBlockState(this.currentTarget);

				if (state.getBlock() instanceof HarvestableTripleTallPlantBlock harvestable && isSuitableTarget(state)) {
					harvestable.harvest(world, this.currentTarget, state, villager);
				} else {
					this.targetPositions.remove(this.currentTarget);
					this.currentTarget = this.chooseRandomTarget(world);

					if (this.currentTarget != null) {
						this.nextResponseTime = time + RESPONSE_INTERVAL;
						this.rememberTargets(villager.getBrain(), this.currentTarget);
					}
				}
			}
		}
	}
}
