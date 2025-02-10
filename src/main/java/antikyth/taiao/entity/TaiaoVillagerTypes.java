// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.event.FishermanBoatTradeOfferCallback;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class TaiaoVillagerTypes {
	/**
	 * Māori villagers inhabiting {@linkplain antikyth.taiao.world.gen.structure.TaiaoStructures#VILLAGE_MARAE marae}.
	 */
	public static final VillagerType MAAORI = register(
		Taiao.id("maaori"),
		false,
		TaiaoBiomes.NATIVE_FOREST,
		TaiaoBiomes.KAHIKATEA_SWAMP
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering villager types");

		FishermanBoatTradeOfferCallback.EVENT.register(builder -> builder.put(MAAORI, TaiaoItems.KAURI_BOAT));
	}

	/**
	 * Registers a {@link VillagerType} for the given {@code biomes}.
	 *
	 * @param replace whether to replace the {@link VillagerType} for a biome that already has one
	 */
	@SafeVarargs
	public static VillagerType register(Identifier id, boolean replace, RegistryKey<Biome> @NotNull ... biomes) {
		VillagerType villagerType = Registry.register(Registries.VILLAGER_TYPE, id, new VillagerType(id.toString()));

		for (RegistryKey<Biome> biome : biomes) {
			VillagerType oldVillagerType = VillagerType.BIOME_TO_TYPE.get(biome);

			if (oldVillagerType != null) {
				if (replace) {
					Taiao.LOGGER.debug(
						"Switching villager type for biome '{}' from '{}' to '{}'",
						biome.getValue(),
						oldVillagerType,
						villagerType
					);

					VillagerType.BIOME_TO_TYPE.put(biome, villagerType);
				} else {
					Taiao.LOGGER.warn(
						"Attempted to switch villager type for biome '{}' from '{}' to '{}' without replace flag",
						biome.getValue(),
						oldVillagerType,
						villagerType
					);
				}
			} else {
				VillagerType.BIOME_TO_TYPE.put(biome, villagerType);
			}
		}

		return villagerType;
	}
}
