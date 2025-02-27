// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.entity.villager;

import antikyth.taiao.entity.ai.brain.task.TaiaoVillagerTaskLists;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Map;

@Mixin(VillagerTaskListProvider.class)
public class VillagerTaskListProviderMixin {
	/**
	 * Adds work tasks to villagers' existing {@link RandomTask} work task.
	 */
	@WrapOperation(
		method = "createWorkTasks",
		at = @At(value = "NEW", target = "net/minecraft/entity/ai/brain/task/RandomTask")
	)
	private static RandomTask<VillagerEntity> addTasksToRandomWorkTasks(
		@Unmodifiable List<Pair<? extends Task<? super VillagerEntity>, Integer>> tasks,
		@NotNull Operation<RandomTask<VillagerEntity>> constructor,
		VillagerProfession profession,
		float speed
	) {
		return constructor.call(TaiaoVillagerTaskLists.createRandomWorkTasks(profession, speed).addAll(tasks).build());
	}

	/**
	 * Adds play tasks to villagers' existing {@link RandomTask} play task.
	 */
	@WrapOperation(
		method = "createPlayTasks",
		at = @At(value = "NEW", target = "net/minecraft/entity/ai/brain/task/RandomTask")
	)
	private static RandomTask<VillagerEntity> addTasksToRandomPlayTasks(
		@Unmodifiable Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState,
		@Unmodifiable List<Pair<? extends Task<? super VillagerEntity>, Integer>> tasks,
		@NotNull Operation<RandomTask<VillagerEntity>> constructor,
		float speed
	) {
		return constructor.call(
			requiredMemoryState,
			TaiaoVillagerTaskLists.createRandomPlayTasks(speed).addAll(tasks).build()
		);
	}

	/**
	 * Adds idle tasks to villagers' existing {@link RandomTask} idle task.
	 */
	@WrapOperation(
		method = "createIdleTasks",
		at = @At(value = "NEW", target = "net/minecraft/entity/ai/brain/task/RandomTask")
	)
	private static RandomTask<VillagerEntity> addTasksToRandomIdleTasks(
		@Unmodifiable List<Pair<? extends Task<? super VillagerEntity>, Integer>> tasks,
		@NotNull Operation<RandomTask<VillagerEntity>> constructor,
		VillagerProfession profession,
		float speed
	) {
		return constructor.call(TaiaoVillagerTaskLists.createRandomIdleTasks(profession, speed).addAll(tasks).build());
	}

	/**
	 * Adds free-follow tasks to villagers' existing {@link RandomTask} free-follow task.
	 */
	@WrapOperation(
		method = "createFreeFollowTask",
		at = @At(value = "NEW", target = "net/minecraft/entity/ai/brain/task/RandomTask")
	)
	private static RandomTask<VillagerEntity> addTasksToRandomFreeFollowTasks(
		@Unmodifiable List<Pair<? extends Task<? super VillagerEntity>, Integer>> tasks,
		@NotNull Operation<RandomTask<VillagerEntity>> constructor
	) {
		return constructor.call(TaiaoVillagerTaskLists.createRandomFreeFollowTasks().addAll(tasks).build());
	}
}
