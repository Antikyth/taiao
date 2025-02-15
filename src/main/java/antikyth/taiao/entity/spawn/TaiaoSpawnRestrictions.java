// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.spawn;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.SpawnRestriction.SpawnPredicate;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

public class TaiaoSpawnRestrictions {
	public static void initialize() {
		Taiao.LOGGER.debug("Registering entity spawn restrictions");

		register(TaiaoEntities.KIWI, Location.ON_GROUND, false, AnimalEntity::isValidNaturalSpawn);
		register(TaiaoEntities.PUUKEKO, Location.ON_GROUND, false, AnimalEntity::isValidNaturalSpawn);
		register(TaiaoEntities.MOA, Location.ON_GROUND, false, AnimalEntity::isValidNaturalSpawn);
		register(TaiaoEntities.KAAKAAPOO, Location.ON_GROUND, true, AnimalEntity::isValidNaturalSpawn);
		register(TaiaoEntities.AUSTRALASIAN_BITTERN, Location.ON_GROUND, false, AnimalEntity::isValidNaturalSpawn);

		register(TaiaoEntities.EEL, Location.IN_WATER, false, WaterCreatureEntity::canSpawn);
	}

	public static <E extends MobEntity> void register(
		EntityType<E> entityType,
		Location location,
		boolean canSpawnOnLeaves,
		SpawnPredicate<E> predicate
	) {
		register(
			entityType,
			location,
			canSpawnOnLeaves ? Heightmap.Type.MOTION_BLOCKING : Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
			predicate
		);
	}

	public static <E extends MobEntity> void register(
		EntityType<E> entityType,
		Location location,
		Heightmap.Type heightmapType,
		SpawnPredicate<E> predicate
	) {
		SpawnRestriction.register(entityType, location, heightmapType, predicate);
	}
}
