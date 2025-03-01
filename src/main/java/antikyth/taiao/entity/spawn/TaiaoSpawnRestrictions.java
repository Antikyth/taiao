// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.spawn;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.WeetaaEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.SpawnRestriction.SpawnPredicate;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;

public class TaiaoSpawnRestrictions {
	public static void initialize() {
		Taiao.LOGGER.debug("Registering entity spawn restrictions");

		registerNativeLandAnimal(TaiaoEntities.KIWI, true);
		registerNativeLandAnimal(TaiaoEntities.PUUKEKO, false);
		registerNativeLandAnimal(TaiaoEntities.MOA, false);
		registerNativeLandAnimal(TaiaoEntities.KAAKAAPOO, true);
		registerNativeLandAnimal(TaiaoEntities.AUSTRALASIAN_BITTERN, false);
		register(TaiaoEntities.WEETAA, Location.ON_GROUND, true, WeetaaEntity::isValidSpawn);

		register(TaiaoEntities.EEL, Location.IN_WATER, false, WaterCreatureEntity::canSpawn);
	}

	public static boolean isValidDaytimeNativeAnimalSpawn(
		EntityType<? extends MobEntity> entityType,
		WorldAccess world,
		SpawnReason reason,
		BlockPos pos,
		Random random
	) {
		return isValidNativeAnimalSpawn(entityType, world, reason, pos, random)
			&& isLightLevelValidForDiurnalSpawn(world, pos);
	}

	public static boolean isValidNativeAnimalSpawn(
		EntityType<? extends MobEntity> entityType,
		WorldAccess world,
		SpawnReason reason,
		BlockPos pos,
		Random random
	) {
		BlockState below = world.getBlockState(pos.down());

		return below.isIn(TaiaoBlockTags.AOTEAROA_ANIMALS_SPAWNABLE_ON);
	}

	public static boolean isLightLevelValidForDiurnalSpawn(BlockRenderView world, BlockPos pos) {
		return world.getBaseLightLevel(pos, 0) > 8;
	}

	public static <E extends MobEntity> void registerNativeLandAnimal(EntityType<E> entityType, boolean nocturnal) {
		register(
			entityType,
			Location.ON_GROUND,
			false,
			nocturnal ? TaiaoSpawnRestrictions::isValidNativeAnimalSpawn
				: TaiaoSpawnRestrictions::isValidDaytimeNativeAnimalSpawn
		);
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
