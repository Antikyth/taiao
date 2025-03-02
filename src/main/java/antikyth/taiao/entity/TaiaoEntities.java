// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TaiaoEntities {
	public static final EntityType<KiwiEntity> KIWI = register(
		Taiao.id("kiwi"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(KiwiEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(0.4f, 0.5f))
			.defaultAttributes(KiwiEntity::createAttributes)
			.trackRangeChunks(10)
			.build()
	);
	/**
	 * A pūkeko.
	 */
	public static final EntityType<PuukekoEntity> PUUKEKO = register(
		Taiao.id("puukeko"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(PuukekoEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(0.4f, 0.7f))
			.defaultAttributes(PuukekoEntity::createAttributes)
			.trackRangeChunks(10)
			.build()
	);
	public static final EntityType<HaastsEagleEntity> HAASTS_EAGLE = register(
		Taiao.id("haasts_eagle"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(HaastsEagleEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(1.25f, 2.25f))
			.defaultAttributes(HaastsEagleEntity::createAttributes)
			.build()
	);
	public static final EntityType<MoaEntity> MOA = register(
		Taiao.id("moa"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(MoaEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(1.25f, 2.25f))
			.defaultAttributes(MoaEntity::createAttributes)
			.trackRangeChunks(10)
			.build()
	);
	/**
	 * A kākāpō.
	 */
	public static final EntityType<KaakaapooEntity> KAAKAAPOO = register(
		Taiao.id("kaakaapoo"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(KaakaapooEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(0.55f, 0.7f))
			.defaultAttributes(KaakaapooEntity::createAttributes)
			.trackRangeChunks(10)
			.build()
	);
	/**
	 * A matuku-hūrepo, also known as an Australasian bittern in English.
	 */
	public static final EntityType<AustralasianBitternEntity> AUSTRALASIAN_BITTERN = register(
		Taiao.id("australasian_bittern"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(AustralasianBitternEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(0.45f, 0.5f))
			.defaultAttributes(AustralasianBitternEntity::createAttributes)
			.trackRangeChunks(10)
			.build()
	);

	public static final EntityType<EelEntity> EEL = register(
		Taiao.id("eel"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(EelEntity::new)
			.spawnGroup(SpawnGroup.WATER_AMBIENT)
			.dimensions(EntityDimensions.changing(0.7f, 0.4f))
			.defaultAttributes(EelEntity::createFishAttributes)
			.trackRangeChunks(4)
			.build()
	);
	/**
	 * A wētā.
	 */
	public static final EntityType<WeetaaEntity> WEETAA = register(
		Taiao.id("weetaa"),
		FabricEntityTypeBuilder.createLiving()
			.entityFactory(WeetaaEntity::new)
			.spawnGroup(SpawnGroup.CREATURE)
			.dimensions(EntityDimensions.changing(1.2f, 0.65f))
			.defaultAttributes(WeetaaEntity::createAttributes)
			.build()
	);

	public static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> entityType) {
		return Registry.register(Registries.ENTITY_TYPE, id, entityType);
	}

	public static void initialize() {
		Taiao.LOGGER.debug("Registered entities");
	}
}
