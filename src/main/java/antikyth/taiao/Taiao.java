// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.entity.TaiaoBlockEntities;
import antikyth.taiao.boat.TaiaoBoats;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.spawn.TaiaoSpawnRestrictions;
import antikyth.taiao.entity.villager.TaiaoTradeOffers;
import antikyth.taiao.entity.villager.TaiaoVillagerTypes;
import antikyth.taiao.item.TaiaoBannerPatterns;
import antikyth.taiao.item.TaiaoItemGroups;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import antikyth.taiao.stat.TaiaoStats;
import antikyth.taiao.world.gen.TaiaoBiomeModifications;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import antikyth.taiao.world.gen.blockpredicate.TaiaoBlockPredicates;
import antikyth.taiao.world.gen.entityprovider.TaiaoEntityTypeProviderTypes;
import antikyth.taiao.world.gen.feature.TaiaoFeatures;
import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import antikyth.taiao.world.gen.structure.processor.TaiaoStructureProcessorTypes;
import antikyth.taiao.world.poi.TaiaoPoiTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Taiao implements ModInitializer {
	public static final String MOD_ID = "taiao";

	public static final String MOD_NAME = "Te Taiao o Aotearoa";
	public static final String MOD_NAME_SHORT = "Taiao";

	boolean initialized = false;

	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	/**
	 * Converts from degrees to radians; useful for entity models.
	 */
	public static float degreesToRadians(float degrees) {
		return degrees * (float) (Math.PI / 180d);
	}

	@Override
	public void onInitialize() {
		if (initialized) return;
		else initialized = true;

		LOGGER.info("Initializing {}", MOD_NAME);

		TaiaoStats.initialize();
		TaiaoSoundEvents.initialize();

		TaiaoBlocks.initialize();
		TaiaoPoiTypes.initialize();
		TaiaoBlockEntities.initialize();

		TaiaoItems.initialize();
		TaiaoItemGroups.initialize();
		TaiaoBannerPatterns.initialize();

		TaiaoEntities.initialize();
		TaiaoSpawnRestrictions.initialize();
		TaiaoVillagerTypes.initialize();
		TaiaoTradeOffers.initialize();
		TaiaoBoats.initialize();

		TaiaoEntityTypeProviderTypes.initialize();
		TaiaoStructureProcessorTypes.initialize();
		TaiaoBlockPredicates.initialize();
		TaiaoTreePlacers.initialize();

		TaiaoFeatures.initialize();
		TaiaoBiomes.initializeBiolith();
		TaiaoBiomeModifications.initialize();
	}

	/**
	 * Creates an {@link Identifier} using the {@linkplain Taiao#MOD_ID Te Taiao o Aotearoa namespace}.
	 */
	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}

	/**
	 * Creates an {@link Identifier} using the common ({@code c}) namespace.
	 */
	public static Identifier commonId(String name) {
		return Identifier.of("c", name);
	}

	/**
	 * Converts the given {@code id} to a {@link String} path separated by {@code /}.
	 * <p>
	 * E.g., {@code taiao:predators/mammals} would become {@code taiao/predators/mammals}.
	 */
	public static @NotNull String toPath(@NotNull Identifier id) {
		return id.toString().replace(':', '/');
	}
}