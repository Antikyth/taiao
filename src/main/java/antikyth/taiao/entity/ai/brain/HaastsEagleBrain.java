// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain;

import antikyth.taiao.entity.HaastsEagleEntity;
import antikyth.taiao.entity.ai.brain.sensor.TaiaoSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Brain} of a {@link HaastsEagleEntity}.
 */
public class HaastsEagleBrain {
	public static final ImmutableList<SensorType<? extends Sensor<? super HaastsEagleEntity>>> SENSORS = ImmutableList.of(
		SensorType.NEAREST_LIVING_ENTITIES,
		SensorType.NEAREST_ADULT,
		SensorType.HURT_BY,
		TaiaoSensorTypes.HAASTS_EAGLE_PREY,
		TaiaoSensorTypes.HAASTS_EAGLE_TEMPTATIONS
	);
	public static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
		MemoryModuleType.HOME,
		MemoryModuleType.LOOK_TARGET,
		MemoryModuleType.WALK_TARGET,
		MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
		MemoryModuleType.PATH,
		MemoryModuleType.BREED_TARGET,
		MemoryModuleType.NEAREST_VISIBLE_ADULT,
		MemoryModuleType.MOBS,
		MemoryModuleType.VISIBLE_MOBS,
		MemoryModuleType.NEAREST_VISIBLE_PLAYER,
		MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
		MemoryModuleType.HURT_BY_ENTITY,
		MemoryModuleType.ATTACK_TARGET,
		MemoryModuleType.ATTACK_COOLING_DOWN,
		MemoryModuleType.NEAREST_ATTACKABLE,
		MemoryModuleType.TEMPTING_PLAYER,
		MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
		MemoryModuleType.IS_TEMPTED,
		MemoryModuleType.HAS_HUNTING_COOLDOWN,
		MemoryModuleType.IS_PANICKING
	);

	@Contract("_ -> param1")
	public static @NotNull Brain<HaastsEagleEntity> create(Brain<HaastsEagleEntity> brain) {
		addCoreActivities(brain);
		addIdleTasks(brain);

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

	protected static void addIdleTasks(@NotNull Brain<HaastsEagleEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			10,
			ImmutableList.of(
				LookAtMobTask.create(8f),
				new RandomTask<>(
					ImmutableList.of(
						Pair.of(StrollTask.create(0.4f), 2),
						Pair.of(GoTowardsLookTargetTask.create(0.4f, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)
					)
				)
			)
		);
	}

	public static void updateActivities(@NotNull HaastsEagleEntity eagle) {
		Brain<HaastsEagleEntity> brain = eagle.getBrain();

		// TODO: hunting cooldown
	}
}
