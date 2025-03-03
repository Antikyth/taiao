// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain;

import antikyth.taiao.entity.HaastsEagleEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class HaastsEagleBrain {
	@Contract("_ -> param1")
	public static @NotNull Brain<HaastsEagleEntity> create(Brain<HaastsEagleEntity> brain) {
		addCoreActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);

		brain.resetPossibleActivities();
		return brain;
	}

	protected static void addCoreActivities(@NotNull Brain<HaastsEagleEntity> brain) {
		brain.setTaskList(
			Activity.CORE,
			0,
			ImmutableList.of(
				new LookAroundTask(45, 90),
				new WanderAroundTask()
			)
		);
	}

	public static void updateActivities(@NotNull HaastsEagleEntity eagle) {
		Brain<HaastsEagleEntity> brain = eagle.getBrain();

		// TODO: hunting cooldown
	}
}
