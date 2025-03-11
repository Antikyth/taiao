// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain.sensor;

import antikyth.taiao.TaiaoConfig;
import antikyth.taiao.entity.HaastsEagleEntity;
import antikyth.taiao.entity.TaiaoEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public class HaastsEagleNearestAttackableSensor extends EntityFilteringSensor<LivingEntity, HaastsEagleEntity> {
	public static final float RANGE = 20f;

	@Override
	protected BiPredicate<LivingEntity, HaastsEagleEntity> predicate() {
		return (target, eagle) -> canHunt(eagle)
			&& isInRange(eagle, target)
			&& isPrey(target)
			&& Sensor.testAttackableTargetPredicate(eagle, target);
	}

	// The output memory of this sensor
	@Override
	protected MemoryModuleType<LivingEntity> getMemory() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}

	@Override
	public SensorType<? extends ExtendedSensor<?>> type() {
		return TaiaoSensorTypes.HAASTS_EAGLE_PREY;
	}

	/**
	 * Whether the Haast's eagle's hunting cooldown is over.
	 */
	protected static boolean canHunt(@NotNull HaastsEagleEntity eagle) {
		return !eagle.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN);
	}

	/**
	 * Whether the Haast's eagle's {@code target} is within range.
	 */
	protected static boolean isInRange(HaastsEagleEntity eagle, @NotNull LivingEntity target) {
		return target.squaredDistanceTo(eagle) <= RANGE * RANGE;
	}

	/**
	 * Whether the {@code target} is valid prey for the Haast's eagle.
	 */
	protected static boolean isPrey(@NotNull LivingEntity target) {
		EntityType<?> type = target.getType();

		boolean notExempted = !target.isBaby() || TaiaoConfig.AnimalBehavior.haastsEaglesHuntBabyAnimals;

		return notExempted
			&& (
			type.isIn(TaiaoEntityTypeTags.HAASTS_EAGLE_GENERAL_PREY)
				|| (target.isBaby() && type.isIn(TaiaoEntityTypeTags.HAASTS_EAGLE_BABY_PREY))
		);
	}

	@Override
	protected @Nullable LivingEntity findMatches(HaastsEagleEntity eagle, @NotNull LivingTargetCache targets) {
		return targets.findFirst(target -> this.predicate().test(target, eagle)).orElse(null);
	}
}
