// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.brain.sensor;

import antikyth.taiao.Taiao;
import antikyth.taiao.item.TaiaoItemTags;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class TaiaoSensorTypes {
	public static final SensorType<TemptationsSensor> HAASTS_EAGLE_TEMPTATIONS = register(
		Taiao.id("haasts_eagle_temptations"),
		() -> new TemptationsSensor(Ingredient.fromTag(TaiaoItemTags.HAASTS_EAGLE_FOOD))
	);
	public static final SensorType<HaastsEagleAttackablesSensor> HAASTS_EAGLE_PREY = register(
		Taiao.id("haasts_eagle_attackables"),
		HaastsEagleAttackablesSensor::new
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered sensor types");
	}

	public static <S extends Sensor<?>> SensorType<S> register(Identifier id, Supplier<S> factory) {
		return Registry.register(Registries.SENSOR_TYPE, id, new SensorType<>(factory));
	}
}
