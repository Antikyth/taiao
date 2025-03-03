// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain.sensor;

import antikyth.taiao.entity.TaiaoEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import org.jetbrains.annotations.NotNull;

public class HaastsEagleAttackablesSensor extends NearestVisibleLivingEntitySensor {
	public static final float RANGE = 20f;

	@Override
	protected boolean matches(@NotNull LivingEntity eagle, LivingEntity target) {
		return !eagle.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN)
			&& isInRange(eagle, target)
			&& isPrey(target)
			&& Sensor.testAttackableTargetPredicate(eagle, target);
	}

	@Override
	protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}

	protected boolean isInRange(LivingEntity eagle, @NotNull LivingEntity target) {
		return target.squaredDistanceTo(eagle) <= RANGE * RANGE;
	}

	protected boolean isPrey(@NotNull LivingEntity target) {
		EntityType<?> type = target.getType();

		return type.isIn(TaiaoEntityTypeTags.HAASTS_EAGLE_GENERAL_PREY)
			|| (target.isBaby() && type.isIn(TaiaoEntityTypeTags.HAASTS_EAGLE_BABY_PREY));
	}
}
