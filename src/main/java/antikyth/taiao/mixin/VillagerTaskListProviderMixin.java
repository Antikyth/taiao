// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.entity.ai.brain.task.TaiaoVillagerTaskLists;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
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
}
